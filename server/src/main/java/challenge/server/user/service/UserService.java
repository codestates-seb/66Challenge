package challenge.server.user.service;

import challenge.server.auth.repository.AuthRepository;
import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.file.service.FileUploadService;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.repository.HabitRepository;
import challenge.server.habit.service.HabitService;
import challenge.server.security.filter.JwtAuthenticationFilter;
import challenge.server.security.filter.JwtVerificationFilter;
import challenge.server.security.jwt.JwtTokenizer;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.security.utils.LoggedInUserInfoUtils;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.*;
import challenge.server.user.mapper.UserMapperImpl;
import challenge.server.user.repository.EmailVerificationRepository;
import challenge.server.user.repository.LogoutListRepository;
import challenge.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static challenge.server.challenge.entity.Challenge.Status.*;
import static challenge.server.user.entity.User.Status.ACTIVE;
import static challenge.server.user.entity.User.Status.QUIT;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final AuthRepository authRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final BookmarkRepository bookmarkRepository;
    private final HabitRepository habitRepository;
    private final HabitService habitService;
    private final ChallengeRepository challengeRepository;
    private final UserMapperImpl userMapper;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher; // todo 회원 가입 시 이메일 전송 관련
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final LoggedInUserInfoUtils loggedInUserInfoUtils;
    private final FileUploadService fileUploadService;
    private final JwtTokenizer jwtTokenizer;
    private final JwtVerificationFilter jwtVerificationFilter;
    private final LogoutListRepository logoutListRepository;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String verificationCode = createVerificationCode(); // 회원 가입 시 이메일 인증 코드 생성

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
    public void sendEmailVerificationMail(String email) throws MessagingException {
        // 인증 대상 이메일이 이미 가입된 회원인지 한 번 확인
        if (verifyExistEmail(email)) {
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }

        // 인증 대상 이메일이 이미 인증 요청을 보냈다면 + 미인증 상태라면
        // 인증 대상 이메일이 이미 인증 요청을 보냈다면 + 인증 상태인데 아직 회원 가입을 하지 않은 상태라면

        // 인증용 이메일 정보(이메일, 인증 코드, 코드 만료 여부, 코드 만료 기한)를 DB에 저장
        EmailVerification emailVerification = EmailVerification.builder().build();
        emailVerificationRepository.save(emailVerification.createEmailVerification(email, verificationCode, false));

        // 인증용 이메일 전송
        emailService.send(email, verificationCode);
    }

    @Transactional
    public void verifyEmail(String email, String verificationCode) {
        Optional<EmailVerification> optionalEmailVerification = emailVerificationRepository.findValidVerificationByEmail(email, verificationCode, LocalDateTime.now());
        EmailVerification emailVerification = optionalEmailVerification.orElseThrow(() -> new BusinessLogicException(ExceptionCode.EMAIL_VERIFICATION_FAILED));

        emailVerification.useVerificationCode(); // 해당 인증 정보는 사용한 것으로 처리 -> 재 인증 요청 시 사용할 수 없도록
        emailVerificationRepository.save(emailVerification);
        log.info("이메일 인증 처리 완료!");
    }

    // 인증 코드 생성
    public String createVerificationCode() {
        StringBuffer verificationCode = new StringBuffer();
        Random rnd = new Random();
        int lengthOfCode = 8;

        for (int i = 0; i < lengthOfCode; i++) {
            verificationCode.append(rnd.nextInt(10));
        }

        return verificationCode.toString();
    }

    @Transactional
    public User createUser(User user) {
        //log.info("-------- createUser 중복 회원 검사 --------");
        //System.out.println(user.getEmail());
        verifyExistUser(user.getEmail());

        // 이메일 인증을 받았는지 확인
        verifyEmailVerified(user.getEmail());

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(user.getEmail());
        //log.info("-------- createUser roles --------");
        //System.out.println(roles); // 2023.1.11(수) 3h40 포스트맨 일반 회원 가입 테스트 시 [USER]
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

//        publisher.publishEvent(new UserRegistrationApplicationEvent(savedUser)); // 회원 가입 시 이메일 전송 관련
        return savedUser;
    }

    // 일반 로그인 시
    @Transactional
    public void verifyLoginUser(String email, String password, String refreshToken) {
        // 로그인 시도하는 이메일 회원이 있는지 확인
        User findUser = findLoginUserByEmail(email);

        // quit이나 banned 상태인 회원은 로그인 불가능 = active 상태인 회원만 로그인 가능
        if (!findUser.getStatus().equals(ACTIVE)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        // 비밀번호가 맞아야만 로그인 가능
//        if (!passwordEncoder.matches(password, findUser.getPassword())) {
//            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
//        }
        if (!password.equals(findUser.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        // 위 조건들을 모두 만족해서 로그인 대상 회원인 경우
        findUser.saveRefreshToken(refreshToken);
    }

    // OAuth2 로그인 시
    @Transactional
    public void verifyLoginUser(String email, String refreshToken) {
        User findUser = findLoginUserByEmail(email);

        if (!findUser.getUserId().equals(ACTIVE)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        // 위 조건들을 모두 만족해서 로그인 대상 회원인 경우
        findUser.saveRefreshToken(refreshToken);
    }

    public User findLoginUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
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
        // todo 위 로직을 람다식으로 써보기

        Optional.ofNullable(user.getPassword())
                .ifPresent(password -> findUser.setPassword(passwordEncoder.encode(password)));
        Optional.ofNullable(user.getUsername())
                .ifPresent(username -> findUser.setUsername(username));
        Optional.ofNullable(user.getAge())
                .ifPresent(age -> findUser.setAge(age));
        Optional.ofNullable(user.getGender())
                .ifPresent(gender -> findUser.setGender(gender));

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

    public void verifyEmailVerified(String email) {
        // 해당 이메일이 인증을 받았는지 확인
        Optional<EmailVerification> optionalEmailVerification = emailVerificationRepository.findByEmail(email);

        // 이메일 인증 테이블에 정보 없으면, 예외 처리
        EmailVerification emailVerification = optionalEmailVerification.orElseThrow(() -> new BusinessLogicException(ExceptionCode.EMAIL_VERIFICATION_FAILED));

        // 이메일 인증 테이블에 정보 있는데, 인증 여부가 여전히 false이면(vs 유효 시간 안에 인증되었을 때만 true로 바뀌어 저장되어 있음), 예외 처리
        if (!emailVerification.getIsValidated()) {
            throw new BusinessLogicException(ExceptionCode.EMAIL_VERIFICATION_FAILED);
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
        Set<UserDto.CategoryDb> activeCategories = new HashSet<>();

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

        // [마이페이지 상단 기본 정보] 리턴할 것 준비
        userDetailsDb.setUserId(findUser.getUserId());
        userDetailsDb.setEmail(findUser.getEmail());
        userDetailsDb.setUsername(findUser.getUsername());
        userDetailsDb.setProfileImageUrl(findUser.getProfileImageUrl());
        userDetailsDb.setBiggestProgressDays((int) DAYS.between(earliestCreatedAt, today));

        // [마이페이지 통계 정보] 리턴할 것 준비
        UserDto.StatisticsResponse statisticsResponse = new UserDto.StatisticsResponse();
        List<NumOfAuthByChallenge> numOfAuthByChallengeList = new ArrayList<>();
        List<DaysOfFail> daysOfFailList = new ArrayList<>();

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

        for (int i = 0; i < challengesInProgress.size(); i++) {
            // 마이페이지 중간2 = '진행중인 습관의 분석데이터를 선택하기위한 카테고리 선택자'
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

            // 마이페이지 통계3 = 많이 참여한 습관의 카테고리
            String categoryType = ch.getHabit().getCategory().getType();
            Map<String, Integer> categoriesByPopularity = new HashMap<>();

            if (!categoriesByPopularity.containsKey(categoryType)) {
                categoriesByPopularity.put(categoryType, 0);
            }

            Integer value = categoriesByPopularity.get(categoryType);
            categoriesByPopularity.put(categoryType, value++);

            // 마이페이지 통계1
            // 현재 진행 중인 습관의 습관 번호를 가지고 Habit 테이블에 가서 습관의 제목을 가져옴
            Long habitId = ch.getHabit().getHabitId();
            String habitTitle = habitService.findVerifiedHabit(habitId).getTitle();

            // 해당 습관/챌린지의 인증글 수
            int numOfAuth = ch.getAuths().size();
            int numOfUsedWildCard = ch.getWildcards().size();
            LocalDateTime createdAt = ch.getCreatedAt();

            NumOfAuthByChallenge numOfAuthByChallenge = NumOfAuthByChallenge.builder()
                    .habitId(habitId)
                    .habitTitle(habitTitle)
                    .createdAt(createdAt)
                    .numOfAuth(numOfAuth)
                    .numOfUsedWildCard(numOfUsedWildCard)
                    .build();

            numOfAuthByChallengeList.add(numOfAuthByChallenge);
        }

        // 마이페이지 통계2
        int sum = 0;
        int average = 0;
        List<Challenge> challengeFails = challengeRepository.findAllByUserUserIdAndStatusOrderByChallengeIdAsc(findUser.getUserId(), FAIL);

        for (int i = 0; i < challengeFails.size(); i++) {
            Challenge ch = challengeFails.get(i);
            int days = (int) DAYS.between(ch.getCreatedAt(), ch.getLastAuthAt()) + 2; // (챌린지 시작한 날 ~ 마지막 인증일) + wild card 2일 감안
            sum += days;

            Long habitId = ch.getHabit().getHabitId();
            String habitTitle = habitService.findVerifiedHabit(habitId).getTitle();

            // 해당 습관/챌린지의 인증글 수
            int numOfAuth = ch.getAuths().size();
            int numOfUsedWildCard = ch.getWildcards().size();
            LocalDateTime createdAt = ch.getCreatedAt();

            DaysOfFail daysOfFail = DaysOfFail.builder()
                    .habitId(habitId)
                    .habitTitle(habitTitle)
                    .createdAt(createdAt)
                    .daysOfFail(days)
                    .build();

            daysOfFailList.add(daysOfFail);
        }

        if (!challengeFails.isEmpty()) {
            average = sum / challengeFails.size();
        }

        // 리턴할 [마이페이지 상단 기본 정보] 최종 정리
        userDetailsDb.setActiveChallenges(activeChallenges);
        userDetailsDb.setActiveCategories(activeCategories);

        UserDto.StatisticsResponse.builder()
                .numOfAuthByChallengeList(numOfAuthByChallengeList)
//                .averageDaysofFail()
//                .myCategories()
                .build();

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
    public List<UserDto.HabitResponse> findHostHabits(Long lastHabitId, Long userId, int size) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인 = 필요 없는 로직
        /*
        Long loggedInUserId = verifyLoggedInUser(userId);
        // 해당 회원의 기본 정보를 DB에서 받아옴 = select 쿼리1
        User findUser = findUser(loggedInUserId);
         */
        User findUser = findVerifiedUser(userId);

        List<Habit> habits = habitRepository.findByHostUserId(lastHabitId, findUser.getUserId(), size);

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
    // 테스트 필요
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
    // 2023.1.21(토) 9h30 문제 = 현재 로그인 시 회원의 상태(ACTIVE)를 확인하지는 않아서, 회원 탈퇴했어도(상태 QUIT) 로그인 가능..
    // 회원 탈퇴 시 db상 비밀번호, 닉네임 정보 남겨놓나? 이메일 주소(로그인 ID 역할)는?
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

    @Transactional
    public void reissueToken(UserDto.TokenRequest requestBody, HttpServletResponse response) throws ServletException, IOException {
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = requestBody.getRefreshToken();

        // refreshToken 검증
        if (!jwtTokenizer.validateToken(refreshToken, base64EncodedSecretKey)) {
            throw new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_NOT_VALID);
        }

        // accessToken에서 user email 가져옴
//        Authentication authentication = jwtVerificationFilter.getAuthentication(accessToken, base64EncodedSecretKey);
        // 2023.1.30(월) 16h accessToken을 안 받기로 함 -> 로그인 시 users 테이블에 저장해둔 refreshToken으로 회원 검색
        Optional<User> optionalUser = userRepository.findByRefreshToken(refreshToken);
        User findUser = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_NOT_VALID)); // 로그아웃되어 DB/Redis에 refreshToken이 존재하지 않는 경우 포함

        // 새로운 토큰 생성
        String newRefreshToken = jwtVerificationFilter.reissueRefreshToken(findUser, response);

        // DB/Redis에 새로운 refreshToken 업데이트
        findUser.setRefreshToken(newRefreshToken);
        userRepository.save(findUser); // dirty checking으로 별도 저장 안 해도 된다?
    }

    @Transactional
    public void logout(UserDto.LogoutRequest requestBody) {
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = requestBody.getAccessToken().substring(7);
        String refreshToken = requestBody.getRefreshToken();

        // accessToken 검증
        if (!jwtTokenizer.validateToken(accessToken, base64EncodedSecretKey)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_TOKEN_NOT_VALID);
        }

        // accessToken에서 user email 가져옴
        Authentication authentication = jwtVerificationFilter.getAuthentication(accessToken, base64EncodedSecretKey);

        // DB/Redis에서 user email을 기반으로 저장된 refreshToken 값을 가져옴
        User findUser = findByEmail(authentication.getName());
        String refreshTokenInDb = findUser.getRefreshToken();

        // DB/Redis에 refreshToken이 존재하는 경우 삭제
        if (refreshTokenInDb != null) {
            findUser.setRefreshToken(null);
        }

        // 해당 accessToken 유효시간 가지고 와서 blackList로 저장하기
        Long expiration = jwtTokenizer.getExpirationFromToken(accessToken, base64EncodedSecretKey);

        LogoutList logoutList = LogoutList.builder()
                .accessToken(accessToken)
                .expiration(expiration).
                build();

        logoutListRepository.save(logoutList);
    }
}