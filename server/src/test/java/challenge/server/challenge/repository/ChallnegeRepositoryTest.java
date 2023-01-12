package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.auth.repository.AuthRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.ChallengeStatus;
import challenge.server.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static challenge.server.challenge.entity.ChallengeStatus.Type.CHALLENGE;
import static challenge.server.challenge.entity.ChallengeStatus.Type.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
class ChallnegeRepositoryTest {

    @Autowired
    private ChallengeStatusRepository challengeStatusRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private AuthRepository authRepository;

    @Test
    @DisplayName(value = "특정 상태의 모든 챌린지 조회")
    void findChallengesByStatus() throws Exception {
        // given
        ChallengeStatus challengeStatus1 = ChallengeStatus.builder().status(CHALLENGE).challenges(new ArrayList<>()).build();
        challengeStatusRepository.save(challengeStatus1);

        ChallengeStatus challengeStatus2 = ChallengeStatus.builder().status(SUCCESS).challenges(new ArrayList<>()).build();
        challengeStatusRepository.save(challengeStatus2);

        Challenge challenge1 = Challenge.builder().status(challengeStatus1).build();
        challengeRepository.save(challenge1);
        Challenge challenge2 = Challenge.builder().status(challengeStatus1).build();
        challengeRepository.save(challenge2);
        Challenge challenge3 = Challenge.builder().status(challengeStatus1).build();
        challengeRepository.save(challenge3);
        Challenge challenge4 = Challenge.builder().status(challengeStatus2).build();
        challengeRepository.save(challenge4);
        Challenge challenge5 = Challenge.builder().status(challengeStatus2).build();
        challengeRepository.save(challenge5);
        List<Challenge> challenges = List.of(challenge1, challenge2, challenge3);
        challengeStatus1.addChallenge(challenges);

        // when
        List<Challenge> findChallenges = challengeRepository.findAllStatus(1L);
        findChallenges.forEach(challenge -> System.out.println(challenge.getChallengeId()));

        // then
        assertEquals(3, findChallenges.size());
    }

    @Test
    void findAuthsByChallengeId() throws Exception {
        // given
        Challenge challenge1 = Challenge.builder().build();
        Challenge saveChallenge = challengeRepository.save(challenge1);
        Challenge challenge2 = Challenge.builder().build();
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

        // when
        List<Auth> auths = challengeRepository.findAuthsByChallengeId(saveChallenge.getChallengeId());
        auths.forEach(System.out::println);
        // then
        assertEquals(3, auths.size());
    }
}