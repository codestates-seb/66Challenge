package challenge.server.user.service;

import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.file.service.FileUploadService;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.repository.HabitRepository;
import challenge.server.habit.service.HabitService;
import challenge.server.helper.event.UserRegistrationApplicationEvent;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.security.utils.LoggedInUserInfoUtils;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.mapper.UserMapperImpl;
import challenge.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.challenge.entity.Challenge.Status.SUCCESS;
import static challenge.server.user.entity.User.Status.QUIT;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final BookmarkRepository bookmarkRepository;
    private final HabitRepository habitRepository;
    private final HabitService habitService;
    private final ChallengeRepository challengeRepository;
    private final UserMapperImpl userMapper;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher; // todo 회원 가입 시 이메일 전송 관련
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final LoggedInUserInfoUtils loggedInUserInfoUtils;
    private final FileUploadService fileUploadService;

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
    // 10h25 Postman 테스트 시 항상 true -> 수정 필요
    public Boolean verifyExistPassword(User user) {
//        return passwordEncoder.encode(user.getPassword()) == findUser.getPassword();
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
//        User loggedInUser = loggedInUserInfoUtils.extractUser();
//        Long loggedInUserId = verifyLoggedInUser(user.getUserId());
//        return loggedInUserPassword == findUser.getPassword();

        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findVerifiedUser(user.getUserId());
        return passwordEncoder.matches(user.getPassword(), findUser.getPassword());
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

    @Transactional
    public User addProfileImage(Long userId, MultipartFile multipartFile) {
        User findUser = findVerifiedUser(userId);

        String profileImageUrl = fileUploadService.save(multipartFile);
        findUser.setProfileImageUrl(profileImageUrl);

        return userRepository.save(findUser);
    }

    // 회원 정보를 수정하려는 사람이 해당 회원이 맞는지 검증하는 로직이 필요한가? 아니면 요청 받을 때 Access Token을 받으면 그걸로 충분한가? -> 2023.1.19(목) 멘토링3 = 후자
    @Transactional/*(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)*/
    public User updateUser(User user) {
//        Long loggedInUserId = verifyLoggedInUser(user.getUserId());
//        User findUser = findVerifiedUser(loggedInUserId);
        User findUser = findVerifiedUser(user.getUserId());

        String oldImage = findUser.getProfileImageUrl();
        String newImage = user.getProfileImageUrl();

        // 기존 이미지가 있고 이번에 새로운 이미지를 업로드하려는 경우 = 이미지 수정
        if (oldImage != null && newImage != null) {
            // 기존 이미지 삭제
            fileUploadService.delete(oldImage);

            // 새로운 이미지 저장
            findUser.setProfileImageUrl(newImage);
        } else if (oldImage == null && newImage != null) { // else if 기존 이미지가 없고 새로운 이미지를 업로드하는 경우 = 이미지 추가
            findUser.setProfileImageUrl(newImage);
        } // else if 기존 이미지가 있고 새로운 이미지가 없는 경우 = 이미지 유지(변동 없음)
        // else = 기존 이미지가 없고 새로운 이미지도 없는 경우 = 프로필 이미지 없음

        /* todo 위 로직을 람다식으로 써보기
        Optional.ofNullable(user.getProfileImageUrl())
                        .ifPresent( if (oldImage != null) {
                            fileUploadService.delete(oldImage);
        }
        profileImageUrl -> findUser.setProfileImageUrl(profileImageUrl);
                        );
         */

        Optional.ofNullable(user.getPassword())
                .ifPresent(password -> findUser.setPassword(passwordEncoder.encode(password)));
        Optional.ofNullable(user.getUsername())
                .ifPresent(username -> findUser.setUsername(username));

        return userRepository.save(findUser);
    }

    @Transactional
    public void deleteProfileImage(Long userId) {
        User findUser = findVerifiedUser(userId);
        fileUploadService.delete(findUser.getProfileImageUrl());

        findUser.setProfileImageUrl(null);
        userRepository.save(findUser);
    }

    public void verifyExistUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }
    }

    // 회원 개인 정보 통합 조회(마이페이지)
    public UserDto.UserDetailsDb findUserDetails(Long userId) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인 = 필요 없는 로직
        /*
        Long loggedInUserId = verifyLoggedInUser(userId);
        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findUser(loggedInUserId);
         */
        User findUser = findVerifiedUser(userId);

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
        LocalDateTime today = LocalDateTime.now(); // 2023.1.19(목) 6h30 -> 2023.1.24(화) 7h 현재 주석 이해 안 됨

        // 현재 진행 중 또는 성공한 챌린지 목록을 DB에서 받아옴 = select 쿼리2
        List<Challenge> challenges = challengeRepository.findAllByUserUserIdAndStatusEqualsOrUserUserIdAndStatusEquals(findUser.getUserId(), CHALLENGE, findUser.getUserId(), SUCCESS);

        // 현재 진행 중인 챌린지 목록을 DB에서 받아옴 = select 쿼리3
        List<Challenge> challengesInProgress = challengeRepository.findAllByUserUserIdAndStatusOrderByChallengeIdAsc(findUser.getUserId(), CHALLENGE);

        // 현재 진행 중인 챌린지 중 가장 먼저 시작한 것 구하기
        LocalDate earliestCreatedAt = LocalDate.now();
        if (!challengesInProgress.isEmpty()) {
            earliestCreatedAt = challengesInProgress.get(0).getCreatedAt().toLocalDate();
        }
        /*
        LocalDateTime earliestCreatedAT = LocalDateTime.of(firstChCreatedAt.getYear(), firstChCreatedAt.getMonth(), firstChCreatedAt.getDayOfMonth(), firstChCreatedAt.getHour(), firstChCreatedAt.getMinute(), firstChCreatedAt.getSecond());
        for (int i = 1; i < challengesInProgress.size(); i++) {
            LocalDateTime thisDate = challengesInProgress.get(i).getCreatedAt();
            if (thisDate.isBefore(earliestCreatedAT)) {
                earliestCreatedAT = LocalDateTime.of(thisDate.getYear(), thisDate.getMonth(), thisDate.getDayOfMonth(), thisDate.getHour(), thisDate.getMinute(), thisDate.getSecond());
            }
        }

        System.out.println(earliestCreatedAT); // 7h5 null -> setBiggestProgressDays 라인에서 null pointer exception 발생
         */

        // 날짜 비교 실험
        LocalDateTime earliestCreatedAtExample = LocalDateTime.of(2023, 1, 15, 0, 1, 1);
//        System.out.println(today.compareTo(earliestCreatedAt1.truncatedTo(ChronoUnit.DAYS)) + 1); // 오늘(1/19)-과거 비교 기대 값 = 5일 -> 7h 과거-오늘 비교로 실행 시 -1
        System.out.println("날짜 비교 실험 결과 = 2023.1.15 ~ 오늘 날짜 수 = " + DAYS.between(earliestCreatedAtExample, today)); // 2023.1.23(월) 23h45 실행 시 8 찍힘 -> todo 우리 biz logic 상 +1 해줘야 하나?

        // 마이페이지 상단 기본 정보 리턴할 것 준비
        userDetailsDb.setUserId(findUser.getUserId());
        userDetailsDb.setEmail(findUser.getEmail());
        userDetailsDb.setUsername(findUser.getUsername());
        userDetailsDb.setProfileImageUrl(findUser.getProfileImageUrl());
        userDetailsDb.setBiggestProgressDays((int) DAYS.between(earliestCreatedAt, today));

        // 마이페이지 중간1 = '회원이 참여중, 참여완료한 습관목록을 서브타이틀로 표시'
        for (int i = 0; i < challenges.size(); i++) {
            Challenge ch = challenges.get(i);

            // 리턴할 자료
            UserDto.ChallengeDetailsDb challengeDetailsDb = new UserDto.ChallengeDetailsDb();

            challengeDetailsDb.setChallengeId(ch.getChallengeId());

            // 챌린지 진행일
            if (!ch.getStatus().equals(SUCCESS)) {
                //            LocalDateTime chCreatedAt = LocalDateTime.of(ch.getCreatedAt().getYear(), ch.getCreatedAt().getMonth(), ch.getCreatedAt().getDayOfMonth(), ch.getCreatedAt().getHour(), ch.getCreatedAt().getMinute(), ch.getCreatedAt().getSecond());
                LocalDateTime chCreatedAt = ch.getCreatedAt();
//            System.out.println(chCreatedAt); // 2023.1.20(금) 14h10 '2022-07-04T21:08:55'와 같이 출력됨
//            challengeDetailsDb.setCreatedAt(ch.getCreatedAt());
                challengeDetailsDb.setProgressDays((int) DAYS.between(chCreatedAt, today)); // 0 ~ 66
            } else {
                challengeDetailsDb.setProgressDays(66);
            }

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

    //    @Transactional(readOnly = true)
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

    // 내가 만든 습관 조회
    // mapper 만들어서 테스트 필요(mapper 없이 응답 통신 가는 것은 Postman 확인 완료)
    public List<UserDto.HabitResponse> findHostHabits(Long lastHabitId, Long userId, int page, int size) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인 = 필요 없는 로직
        /*
        Long loggedInUserId = verifyLoggedInUser(userId);
        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findUser(loggedInUserId);
         */
        User findUser = findVerifiedUser(userId);

        List<Habit> habits = habitRepository.findByHostUserId(lastHabitId, findUser.getUserId(), page, size);

        List<UserDto.HabitResponse> habitResponses = new ArrayList<>();
        for (int i = 0; i < habits.size(); i++) {
            Habit h = habits.get(i);
            UserDto.HabitResponse habitResponse = UserDto.HabitResponse.builder()
                    .habitId(h.getHabitId())
                    .title(h.getTitle())
                    .subTitle(h.getSubTitle())
                    .body(h.getBody())
                    .isBooked(!bookmarkRepository.findByUserUserIdAndHabitHabitId(userId, h.getHabitId()).isEmpty())
                    .thumbImgUrl(h.getThumbImgUrl())
                    .build();

            habitResponses.add(habitResponse);
        }

        return habitResponses;
    }

    // 인증서 발급
    // todo 테스트 필요
    public UserDto.SuccessHabitCertificate issueHabitCertificate(Long userId, Long habitId) {
        System.out.println("userId = " + userId + ", habitId = " + habitId);
        User findUser = findVerifiedUser(userId);

        Optional<Challenge> optionalChallenge = challengeRepository.findByUserUserIdAndHabitHabitId(userId, habitId);
        Challenge findChallenge = optionalChallenge.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHALLENGE_NOT_FOUND));
        /* 2023.1.24(화) 8h45 인증서 발급 테스트 시 만들어봄
        Challenge findChallenge = Challenge.builder().build();
        List<Challenge> challengeList = challengeRepository.findAllByUserUserIdAndHabitHabitId(findUser.getUserId(), habitId);
        if (challengeList.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.CHALLENGE_NOT_FOUND);
        } else {
            findChallenge = challengeList.get(0);
        }
         */
        System.out.println("인증서 발급 대상 challenge의 식별자 = " + findChallenge.getChallengeId());

        Habit findHabit = habitService.findVerifiedHabit(findChallenge.getHabit().getHabitId());
        System.out.println("인증서 발급 대상 challenge의 habit title = " + findHabit.getTitle());

        UserDto.SuccessHabitCertificate successHabitCertificate = UserDto.SuccessHabitCertificate.builder()
                .challengeId(findChallenge.getChallengeId())
                .username(findUser.getUsername())
                .title(findHabit.getTitle())
                .createdAt(findChallenge.getCreatedAt())
                .completedAt(findChallenge.getCreatedAt().plusDays(66L))
                .build();

        return successHabitCertificate;
    }

    // 회원 탈퇴
    // todo 회원 탈퇴했다가(quit 상태) 다시 가입하고자 하는 사람은 어떻게 하나? 회원 가입 시 quit 상태인 사람들은 받아주나, 아니면 새로운 이메일 주소로 가입해야 하나?
    // todo 2023.1.21(토) 9h30 문제 = 현재 로그인 시 회원의 상태(ACTIVE)를 확인하지는 않아서, 회원 탈퇴했어도(상태 QUIT) 로그인 가능..
    // todo 회원 탈퇴 시 db상 비밀번호, 닉네임 정보 남겨놓나? 이메일 주소(로그인 ID 역할)는?
    @Transactional
    public void quitUser(Long userId) {
        User findUser = findVerifiedUser(userId);

        // 회원 프로필 이미지 저장소에서 삭제 + db 데이터 업데이트
        if (findUser.getProfileImageUrl() != null) {
            fileUploadService.delete(findUser.getProfileImageUrl());
            findUser.setProfileImageUrl(null);
        }

        // todo 아래 회원 엔티티 상태 변경 관련해서 엔티티에 '회원 탈퇴' 메서드 만들기?
        // 회원 상태 변경
        findUser.setStatus(QUIT);
//        System.out.println(findUser.getStatus().toString());

        // Spring Security 회원 탈퇴(reference = https://carpet-part1.tistory.com/156)
//        SecurityContextHolder.clearContext(); // 이렇게 해도 user_roles 테이블 데이터 삭제 안 됨 + 로그인됨

        // 회원 역할(roles) 삭제
        findUser.deleteRoles();

        // 비밀번호 삭제 -> null로 만들려고 했으나 Column 'password' cannot be null
//        findUser.setPassword(null);

        userRepository.save(findUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }
}