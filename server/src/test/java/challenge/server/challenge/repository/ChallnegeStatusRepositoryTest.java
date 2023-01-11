package challenge.server.challenge.repository;

import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.ChallengeStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static challenge.server.challenge.entity.ChallengeStatus.Type.CHALLENGE;
import static challenge.server.challenge.entity.ChallengeStatus.Type.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ChallnegeStatusRepositoryTest {

    @Autowired
    private ChallengeStatusRepository challengeStatusRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

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
        List<Challenge> contents = challengeStatusRepository.findChallengesByStatus(2L, PageRequest.of(1, 30)).getContent();
        // then
        assertEquals(3, contents.size());
    }


}