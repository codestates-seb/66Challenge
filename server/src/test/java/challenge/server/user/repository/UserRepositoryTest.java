//package challenge.server.user.repository;
//
//import challenge.server.category.entity.Category;
//import challenge.server.category.repository.CategoryRepository;
//import challenge.server.challenge.entity.Challenge;
//import challenge.server.challenge.repository.ChallengeRepository;
//import challenge.server.config.TestConfig;
//import challenge.server.habit.entity.Habit;
//import challenge.server.habit.repository.HabitRepository;
//import challenge.server.user.dto.UserDto;
//import challenge.server.user.entity.QUser;
//import challenge.server.user.entity.User;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static challenge.server.challenge.entity.Challenge.Status.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@Import(TestConfig.class)
//public class UserRepositoryTest {
//
//    @Autowired
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
//    @Autowired
//    private EntityManager em;
//
//    @Test
//    @DisplayName(value = "회원 개인 정보 통합 조회(마이페이지)")
//    void findUserDetails() {
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
//        Habit habit1 = Habit.builder().subTitle("no 플라스틱").category(category1).build();
//        Habit saveHabit1 = habitRepository.save(habit1);
//        Habit habit2 = Habit.builder().subTitle("미라클모닝").category(category2).build();
//        Habit saveHabit2 = habitRepository.save(habit2);
//        Habit habit3 = Habit.builder().subTitle("분리수거").category(category1).build();
//        Habit saveHabit3 = habitRepository.save(habit3);
//        Habit habit4 = Habit.builder().subTitle("만보 걷기").category(category2).build();
//        Habit saveHabit4 = habitRepository.save(habit4);
//
//        Challenge challenge1 = Challenge.builder().status(CHALLENGE).user(user1).habit(habit1).build();
//        Challenge challenge2 = Challenge.builder().status(CHALLENGE).user(user1).habit(habit2).build();
//        Challenge challenge3 = Challenge.builder().status(FAIL).user(user1).habit(habit3).build();
//        Challenge challenge4 = Challenge.builder().status(SUCCESS).user(user1).habit(habit4).build();
//        Challenge saveChallenge1 = challengeRepository.save(challenge1);
//        Challenge saveChallenge2 = challengeRepository.save(challenge2);
//        Challenge saveChallenge3 = challengeRepository.save(challenge3);
//        Challenge saveChallenge4 = challengeRepository.save(challenge4);
//
//        // when
//        UserDto.UserDetailsDb userDetailsDb = userRepository.findUserDetails(user1.getUserId());
//        List<UserDto.ChallengeDetailsDb> challengeDetailsDbs = userRepository.findChallengeDetails(user1.getUserId());
//
//        // then
//        Assertions.assertThat(userDetailsDb.getEmail().equals(user1.getEmail()));
//        Assertions.assertThat(challengeDetailsDbs.size()).isEqualTo(3);
//    }
//
//    //    @BeforeEach
////    @Test
////    void saveUser() {
////        // given
////        User user1 = User.builder()
////                .userId(1L)
////                .email("user1@gmail.com")
////                .password("Iamuser001!")
////                .username("user1번")
////                .status(User.Status.ACTIVE)
////                .build();
////
////        // when
////        /*User saveUser = */
////        userRepository.save(user1);
////
////        // then
//////        Assertions.assertThat(saveUser.getUserId()).isEqualTo(user.getUserId());
////    }
//
////    @Test
////    void findUserById() {
////        // given
////        User user1 = User.builder()
////                .userId(1L)
////                .email("user1@gmail.com")
////                .password("Iamuser001!")
////                .username("user1번")
////                .status(User.Status.ACTIVE)
////                .build();
//////        em.persist(user1);
////        userRepository.save(user1);
////
////        // when
////        JPAQueryFactory query = new JPAQueryFactory(em);
////        QUser qUser = QUser.user;
////
////        User findUser = query
////                .selectFrom(qUser)
////                .fetchOne();
////
////        // then
//////        assertThat(findUser).isEqualTo(user1);
////        assertThat(findUser.getUserId()).isEqualTo(user1.getUserId());
////    }
//}
