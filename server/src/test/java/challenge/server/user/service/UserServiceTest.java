//package challenge.server.user.service;
//
//import challenge.server.category.entity.Category;
//import challenge.server.category.repository.CategoryRepository;
//import challenge.server.challenge.entity.Challenge;
//import challenge.server.challenge.repository.ChallengeRepository;
//import challenge.server.config.TestConfig;
//import challenge.server.habit.entity.Habit;
//import challenge.server.habit.repository.HabitRepository;
//import challenge.server.user.dto.UserDto;
//import challenge.server.user.entity.User;
//import challenge.server.user.repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static challenge.server.challenge.entity.Challenge.Status.*;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
////@ExtendWith(MockitoExtension.class)
//@DataJpaTest
//@Import(TestConfig.class)
//class UserServiceTest {
//    @Autowired
////    @InjectMocks
//    private UserService userService;
//
//    @Autowired
////    @Mock
//    private UserRepository userRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private HabitRepository habitRepository;
//
//    @Autowired
//    private ChallengeRepository challengeRepository;
//
//    @Test
//    @DisplayName(value = "회원 개인 정보 통합 조회(마이페이지)")
//    void findUserDetailsTest() {
//        // given
//        User user1 = User.builder().email("coffee@gmail.com").username("아메리카노").password("Abcdefg12!").build();
//        User saveUser1 = userRepository.save(user1);
//        System.out.println(user1.getUserId());
//
//        Category category1 = Category.builder().type("eco").build();
//        Category saveCategory1 = categoryRepository.save(category1);
//        Category category2 = Category.builder().type("health").build();
//        Category saveCategory2 = categoryRepository.save(category2);
//
//        Habit habit1 = Habit.builder().subTitle("no 플라스틱").category(saveCategory1).build();
//        Habit saveHabit1 = habitRepository.save(habit1);
//        Habit habit2 = Habit.builder().subTitle("미라클모닝").category(saveCategory2).build();
//        Habit saveHabit2 = habitRepository.save(habit2);
//        Habit habit3 = Habit.builder().subTitle("분리수거").category(saveCategory1).build();
//        Habit saveHabit3 = habitRepository.save(habit3);
//        Habit habit4 = Habit.builder().subTitle("만보 걷기").category(saveCategory2).build();
//        Habit saveHabit4 = habitRepository.save(habit4);
//
//        Challenge challenge1 = Challenge.builder().status(CHALLENGE).user(saveUser1).habit(saveHabit1).build();
//        Challenge challenge2 = Challenge.builder().status(CHALLENGE).user(saveUser1).habit(saveHabit2).build();
//        Challenge challenge3 = Challenge.builder().status(FAIL).user(saveUser1).habit(saveHabit3).build();
//        Challenge challenge4 = Challenge.builder().status(SUCCESS).user(saveUser1).habit(saveHabit4).build();
//        Challenge saveChallenge1 = challengeRepository.save(challenge1);
//        Challenge saveChallenge2 = challengeRepository.save(challenge2);
//        Challenge saveChallenge3 = challengeRepository.save(challenge3);
//        Challenge saveChallenge4 = challengeRepository.save(challenge4);
//
//        // when
//        List<Object> results = userService.findUserDetails(user1.getUserId());
////        List<UserDto.ChallengeDetailsDb> challengeDetailsDbs = userRepository.findChallengeDetails(user1.getUserId());
//
//        // then
//        Assertions.assertThat(((UserDto.UserDetailsDb) results.get(0)).getEmail().equals(user1.getEmail()));
//        Assertions.assertThat(((List<UserDto.ChallengeDetailsDb>) results.get(1)).size()).isEqualTo(3);
//    }
//
////    @Test
////    @DisplayName(value = "이메일 중복 여부 확인")
////    void verifyExistEmail() {
////        // given
////        User user1 = User.builder()
////                .email("user1@gmail.com")
////                .password("Abc12&defg")
////                .username("user1번")
////                .build();
////
////        User savedUser = userService.createUser(user1);
////        System.out.println(savedUser.getUserId());
////
////        String email1 = "user1@gmail.com";
////        String email2 = "mimi@gmail.com";
////
////        // when + then
////        assertTrue(userService.verifyExistEmail(email1));
////        assertFalse(userService.verifyExistEmail(email2));
////    }
////
////    @Test
////    @DisplayName(value = "회원 닉네임 중복 여부 확인")
////    void verifyExistUsername() {
////    }
//
//    // 2023.1.18(수) 오전 Postman으로 기능 테스트함
////    @Test
////    void createUser() {
////    }
//
//    // 2023.1.18(수) 낮 Postman으로 기능 테스트함
////    @Test
////    @DisplayName(value = "회원 정보 수정")
////    void updateUserPassword() {
////        // given
////        User user = User.builder()
////                .userId(1L)
////                .email("user1@gmail.com")
////                .password("Abc12&defg") // 수정 가능
////                .username("user1번") // 수정 가능
////                .build();
////
//////        given(userRepository.save(Mockito.any(User.class))).willReturn(user);
////
////        User userForPasswordUpdate = User.builder()
////                .userId(1L)
////                .password("aBc34!defG")
////                .username(null)
////                .build();
////
//////        given(userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(userForPasswordUpdate));
////        System.out.println(userForPasswordUpdate.getPassword()); // aBc34!defG
////
////        // when
//////        assertThrows()
////        given(userService.findVerifiedUser(1L)).willReturn(user);
////        given(userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(user));
////        User updatedUser = userService.updateUser(userForPasswordUpdate);
////
////        // then
////        assertEquals(userForPasswordUpdate.getPassword(), updatedUser.getPassword());
////    }
//
////    @Test
////    void verifyExistUser() {
////    }
////
////    @Test
////    void findUser() {
////    }
////
////    @Test
////    void findVerifiedUser() {
////    }
//}