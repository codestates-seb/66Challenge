package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.auth.repository.AuthRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import challenge.server.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.challenge.entity.Challenge.Status.FAIL;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
class WildcardRepositoryTest {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private WildcardRepository wildcardRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void saveChallenge() throws Exception {
        Challenge challenge1 = Challenge.builder().status(CHALLENGE).build();
        Challenge saveChallenge1 = challengeRepository.save(challenge1);
        Challenge challenge2 = Challenge.builder().status(CHALLENGE).build();
        Challenge saveChallenge2 = challengeRepository.save(challenge2);
        Challenge challenge3 = Challenge.builder().status(FAIL).build();
        Challenge saveChallenge3 = challengeRepository.save(challenge3);

        Wildcard wildcard1 = Wildcard.builder().challenge(challenge1).build();
        wildcardRepository.save(wildcard1);
        Wildcard wildcard2 = Wildcard.builder().challenge(challenge1).build();
        wildcardRepository.save(wildcard2);
        Wildcard wildcard3 = Wildcard.builder().challenge(challenge1).build();
        wildcardRepository.save(wildcard3);

        Wildcard wildcard4 = Wildcard.builder().challenge(challenge2).build();
        wildcardRepository.save(wildcard4);
        Wildcard wildcard5 = Wildcard.builder().challenge(challenge2).build();
        wildcardRepository.save(wildcard5);

        em.flush();
        em.clear();
    }

    @Test
    void findAllByChallengeChallengeId() throws Exception {
        // when
        List<Wildcard> wildcards1 = wildcardRepository.findAllByChallengeChallengeId(1L);
        List<Wildcard> wildcards2 = wildcardRepository.findAllByChallengeChallengeId(2L);
        List<Wildcard> wildcards3 = wildcardRepository.findAllByChallengeChallengeId(3L);

        // then
        assertEquals(3, wildcards1.size());
        assertEquals(2, wildcards2.size());
        assertEquals(0, wildcards3.size());
    }
}