package challenge.server.user.repository;

import challenge.server.config.TestConfig;
import challenge.server.user.entity.QUser;
import challenge.server.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    //    @BeforeEach
//    @Test
    void saveUser() {
        // given
        User user1 = User.builder()
                .userId(1L)
                .email("user1@gmail.com")
                .password("Iamuser001!")
                .username("user1번")
                .status(User.Status.ACTIVE)
                .build();

        // when
        /*User saveUser = */
        userRepository.save(user1);

        // then
//        Assertions.assertThat(saveUser.getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    void findUserById() {
        // given
        User user1 = User.builder()
                .userId(1L)
                .email("user1@gmail.com")
                .password("Iamuser001!")
                .username("user1번")
                .status(User.Status.ACTIVE)
                .build();
//        em.persist(user1);
        userRepository.save(user1);

        // when
        JPAQueryFactory query = new JPAQueryFactory(em);
        QUser qUser = QUser.user;

        User findUser = query
                .selectFrom(qUser)
                .fetchOne();

        // then
//        assertThat(findUser).isEqualTo(user1);
        assertThat(findUser.getUserId()).isEqualTo(user1.getUserId());
    }
}
