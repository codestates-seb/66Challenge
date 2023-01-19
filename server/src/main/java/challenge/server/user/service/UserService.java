package challenge.server.user.service;

import challenge.server.bookmark.entity.Bookmark;
import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.repository.HabitRepository;
import challenge.server.helper.event.UserRegistrationApplicationEvent;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.security.utils.LoggedInUserInfoUtils;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.mapper.UserMapper;
import challenge.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.challenge.entity.Challenge.Status.SUCCESS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final BookmarkRepository bookmarkRepository;
    private final HabitRepository habitRepository;
    private final ChallengeRepository challengeRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher; // todo 회원 가입 시 이메일 전송 관련
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final LoggedInUserInfoUtils loggedInUserInfoUtils;

    public Boolean verifyExistEmail(String email) {
        Boolean existEmail = false;
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            existEmail = true;
        }

        return existEmail;
    }

    public Boolean verifyExistUsername(String username) {
        Boolean existUsername = false;
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            existUsername = true;
        }

        return existUsername;
    }

    // 비밀번호 일치 여부 확인
    // todo 10h25 Postman 테스트 시 항상 true -> 수정 필요
    public Boolean verifyExistPassword(User user) {
//        return passwordEncoder.encode(user.getPassword()) == findUser.getPassword();
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
        User loggedInUser = loggedInUserInfoUtils.extractUser();
        Long loggedInUserId = verifyLoggedInUser(user.getUserId());

        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findUser(loggedInUserId);

        String loggedInUserPassword = loggedInUser.getPassword();

        return loggedInUserPassword == findUser.getPassword();
    }

    @Transactional
    public User createUser(User user) {
        //log.info("-------- createUser 중복 회원 검사 --------");
        //System.out.println(user.getEmail());
        verifyExistUser(user.getEmail());

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(user.getEmail());
        //log.info("-------- createUser roles --------");
        //System.out.println(roles); // 2023.1.11(수) 3h40 포스트맨 일반 회원 가입 테스트 시 [USER]
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        publisher.publishEvent(new UserRegistrationApplicationEvent(savedUser)); // todo 회원 가입 시 이메일 전송 관련
        return savedUser;
    }

    // todo 회원 정보를 수정하려는 사람이 해당 회원이 맞는지 검증하는 로직이 필요한가? 아니면 요청 받을 때 Access Token을 받으면 그걸로 충분한가?
    @Transactional/*(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)*/
    public User updateUser(User user) {
        Long loggedInUserId = verifyLoggedInUser(user.getUserId());
        User findUser = findVerifiedUser(loggedInUserId);
        Optional.ofNullable(user.getPassword())
                .ifPresent(password -> findUser.setPassword(passwordEncoder.encode(password)));
        Optional.ofNullable(user.getUsername())
                .ifPresent(username -> findUser.setUsername(username));

        return userRepository.save(findUser);
    }

    public void verifyExistUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }
    }

    // 회원 개인 정보 통합 조회(마이페이지)
    public UserDto.UserDetailsDb findUserDetails(Long userId) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
        Long loggedInUserId = verifyLoggedInUser(userId);

        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findUser(loggedInUserId);

        // Querydsl 시도
//        UserDto.UserDetailsDb userDetailsDb = userRepository.findUserDetails(userId);
//        List<UserDto.ChallengeDetailsDb> challengeDetailsDb0 = userRepository.findChallengeDetails(userId);

        // 쿼리메서드 사용
        // 반환할 자료형 준비
        UserDto.UserDetailsDb userDetailsDb = new UserDto.UserDetailsDb();

        // 반환 자료형의 속성 중 컬렉션 준비
        List<UserDto.ChallengeDetailsDb> activeChallenges = new ArrayList<>();
        List<UserDto.CategoryDb> activeCategories = new ArrayList<>();

        // 오늘 날짜
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS); // 2023.1.19(목) 6h30

        // 현재 진행 중 + 성공한 챌린지 목록을 DB에서 받아옴 = select 쿼리2
        List<Challenge> challenges = challengeRepository.findAllByUserUserIdAndStatusEqualsOrStatusEquals(findUser.getUserId(), CHALLENGE, SUCCESS);

        // 현재 진행 중인 챌린지 목록을 DB에서 받아옴 = select 쿼리3
        List<Challenge> challengesInProgress = challengeRepository.findAllByUserUserIdAndStatus(findUser.getUserId(), CHALLENGE);

        // 현재 진행 중인 챌린지 중 가장 먼저 시작한 것 구하기
        LocalDateTime firstChCreatedAt = challenges.get(0).getCreatedAt();
        LocalDateTime earliestCreatedAT = LocalDateTime.of(firstChCreatedAt.getYear(), firstChCreatedAt.getMonth(), firstChCreatedAt.getDayOfMonth(), firstChCreatedAt.getHour(), firstChCreatedAt.getMinute(), firstChCreatedAt.getSecond());
        for (int i = 1; i < challengesInProgress.size(); i++) {
            LocalDateTime thisDate = challengesInProgress.get(i).getCreatedAt();
            if (thisDate.isBefore(earliestCreatedAT)) {
                earliestCreatedAT = LocalDateTime.of(thisDate.getYear(), thisDate.getMonth(), thisDate.getDayOfMonth(), thisDate.getHour(), thisDate.getMinute(), thisDate.getSecond());
            }
        }

        System.out.println(earliestCreatedAT); // 7h5 null -> setBiggestProgressDays 라인에서 null pointer exception 발생

        // 날짜 비교 실험
        LocalDateTime earliestCreatedAt1 = LocalDateTime.of(2023, 1, 15, 1, 1, 1);
        System.out.println(today.compareTo(earliestCreatedAt1.truncatedTo(ChronoUnit.DAYS)) + 1); // 오늘-과거 비교 기대 값 = 5일 -> 7h 과거-오늘 비교로 실행 시 -1

        // 마이페이지 상단 기본 정보 리턴할 것 준비
        userDetailsDb.setUserId(findUser.getUserId());
        userDetailsDb.setEmail(findUser.getEmail());
        userDetailsDb.setUsername(findUser.getUsername());
        userDetailsDb.setBiggestProgressDays(today.compareTo(earliestCreatedAT.truncatedTo(ChronoUnit.DAYS)) + 1);

        // 마이페이지 중간1 = '회원이 참여중, 참여완료한 습관목록을 서브타이틀로 표시'
        for (int i = 0; i < challenges.size(); i++) {
            Challenge ch = challenges.get(i);

            // 리턴할 자료
            UserDto.ChallengeDetailsDb challengeDetailsDb = new UserDto.ChallengeDetailsDb();

            challengeDetailsDb.setChallengeId(ch.getChallengeId());

            // 챌린지 진행일
            LocalDateTime chCreatedAt = LocalDateTime.of(ch.getCreatedAt().getYear(), ch.getCreatedAt().getMonth(), ch.getCreatedAt().getDayOfMonth(), ch.getCreatedAt().getHour(), ch.getCreatedAt().getMinute(), ch.getCreatedAt().getSecond());
//            challengeDetailsDb.setCreatedAt(ch.getCreatedAt());
            challengeDetailsDb.setProgressDays(today.compareTo(chCreatedAt.truncatedTo(ChronoUnit.DAYS)) + 1);

            challengeDetailsDb.setHabitId(ch.getHabit().getHabitId());
            challengeDetailsDb.setSubTitle(ch.getHabit().getSubTitle());
//            challengeDetailsDb.setCategoryId(ch.getHabit().getCategory().getCategoryId());
//            challengeDetailsDb.setType(ch.getHabit().getCategory().getType());

            activeChallenges.add(challengeDetailsDb);
        }

        // 마이페이지 중간2 = '진행중인 습관의 분석데이터를 선택하기위한 카테고리 선택자'
        for (int i = 0; i < challengesInProgress.size(); i++) {
            Challenge ch = challengesInProgress.get(i);
            Long categoryId = ch.getHabit().getCategory().getCategoryId();
            Set<Long> categoryIds = new HashSet<>();

            // 리턴할 자료
            UserDto.CategoryDb categoryDb = new UserDto.CategoryDb();

            if (!categoryIds.contains(categoryId)) {
                categoryDb.setCategoryId(categoryId);
                categoryDb.setType(ch.getHabit().getCategory().getType());

                activeCategories.add(categoryDb);
            }
        }

        // 리턴할 자료 최종 정리
        userDetailsDb.setActiveChallenges(activeChallenges);
        userDetailsDb.setActiveCategories(activeCategories);

        return userDetailsDb;
    }

    public User findUser(Long userId) {
        return findVerifiedUser(userId);
    }

    public User findVerifiedUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User findUser = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }

    // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
    public Long verifyLoggedInUser(Long userId) {
        User loggedInUser = loggedInUserInfoUtils.extractUser();
        Long loggedInUserId = loggedInUser.getUserId();

        if (loggedInUserId != userId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        return loggedInUserId;
    }

    // 회원이 찜한 습관들의 목록 출력
    // todo 테스트 필요
    public List<Habit> findBookmarkHabits(Long userId, int page, int size) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
        Long loggedInUserId = verifyLoggedInUser(userId);

        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findUser(loggedInUserId);

        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserUserId(findUser.getUserId(), PageRequest.of(page - 1, size, Sort.by("habitId").descending())).getContent();

        List<Habit> habits = new ArrayList<>();

        for (int i = 0; i < bookmarks.size(); i++) {
            habits.add(habitRepository.findById(bookmarks.get(i).getHabit().getHabitId()).get());
        }

        return habits;
    }

    // 내가 만든 습관 조회
    // todo mapper 만들어서 테스트 필요(mapper 없이 응답 통신 가는 것은 Postman 확인 완료)
    public List<Habit> findHostHabits(Long userId, int page, int size) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
        Long loggedInUserId = verifyLoggedInUser(userId);

        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findUser(loggedInUserId);

        List<Habit> habits = habitRepository.findAllByHostUserId(findUser.getUserId(), PageRequest.of(page - 1, size, Sort.by("habitId").descending())).getContent();
        return habits;
    }

    // 인증서 발급
    // todo 테스트 필요
    public UserDto.SuccessHabitCertificate issueHabitCertificate(Long userId, Long habitId) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
        Long loggedInUserId = verifyLoggedInUser(userId);

        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findUser(loggedInUserId);

        Optional<Challenge> optionalChallenge = challengeRepository.findByUserUserIdAndHabitHabitId(findUser.getUserId(), habitId);
        Challenge findChallenge = optionalChallenge.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHALLENGE_NOT_FOUND));

        UserDto.SuccessHabitCertificate successHabitCertificate = UserDto.SuccessHabitCertificate.builder()
                .challengeId(findChallenge.getChallengeId())
                .username(findUser.getUsername())
                .title(findChallenge.getHabit().getTitle())
                .createdAt(findChallenge.getCreatedAt())
                .completedAt(findChallenge.getCreatedAt().plusDays(66L))
                .build();

        return successHabitCertificate;
    }
}