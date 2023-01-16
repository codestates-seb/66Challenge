package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.auth.repository.AuthRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import challenge.server.config.TestConfig;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
class ChallnegeRepositoryTest {
    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private WildcardRepository wildcardRepository;

    @BeforeEach
    void saveChallenge() throws Exception {
        Challenge challenge1 = Challenge.builder().status(Challenge.Status.CHALLENGE).build();
        Challenge saveChallenge = challengeRepository.save(challenge1);
        Challenge challenge2 = Challenge.builder().status(Challenge.Status.CHALLENGE).build();
        Challenge saveChallenge2 = challengeRepository.save(challenge2);

        Auth auth1 = Auth.builder().challenge(saveChallenge).body("인증 내용1").build();
        Auth auth2 = Auth.builder().challenge(saveChallenge).body("인증 내용2").build();
        Auth auth3 = Auth.builder().challenge(saveChallenge).body("인증 내용3").build();
        Auth auth4 = Auth.builder().challenge(saveChallenge2).body("인증 내용3").build();
        Auth auth5 = Auth.builder().challenge(saveChallenge2).body("인증 내용3").build();

        authRepository.save(auth1);
        authRepository.save(auth2);
        authRepository.save(auth3);

        challengeRepository.save(saveChallenge);
        challengeRepository.save(saveChallenge2);
    }

    @DisplayName(value = "특정 챌린지의 모든 인증 게시글 조회")
    @Test
    void findAuthsByChallengeId() throws Exception {
        // given
        Challenge challenge = challengeRepository.findAll().get(0);

        // when
        List<Auth> auths = challengeRepository.findAuthsByChallengeId(challenge.getChallengeId());
        auths.forEach(System.out::println);

        // then
        assertEquals(3, auths.size());
    }

    @DisplayName(value = "특정 상태의 모든 챌린지 조회")
    @Test
    void findAllByStatus() throws Exception {

        // when
        List<Challenge> challenges = challengeRepository.findAllByStatus(Challenge.Status.CHALLENGE);
        List<Challenge> success = challengeRepository.findAllByStatus(Challenge.Status.SUCCESS);

        // then
        assertEquals(2, challenges.size());
        assertEquals(0, success.size());
    }
}