package challenge.server.challenge.service;

import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import challenge.server.challenge.repository.ChallengeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.*;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    @InjectMocks
    private ChallengeService challengeService;

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private WildcardService wildcardService;

    @Test
    void notAuthTodayCheck() {
        // given
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime successTime = LocalDateTime.now().minusDays(67);
        Wildcard wildcard = Wildcard.builder().build();

        Challenge fChallenge = Challenge.builder().challengeId(1L).status(CHALLENGE).wildcards(List.of(wildcard, wildcard)).build();
        fChallenge.setCreatedAt(time);
        Challenge challenge = Challenge.builder().challengeId(2L).status(CHALLENGE).build();
        challenge.setCreatedAt(time);
        Challenge sChallenge = Challenge.builder().challengeId(3L).status(CHALLENGE).build();
        sChallenge.setCreatedAt(successTime);

        given(challengeRepository.findAllByNotAuthToday(Mockito.any(Challenge.Status.class), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .willReturn(List.of(fChallenge, challenge, sChallenge));
        given(wildcardService.useWildcard(Mockito.any(Challenge.class))).willReturn(wildcard);

        // when
        List<Challenge> challenges = challengeService.notAuthTodayCheck();
        Challenge failChallenge = challenges.get(0);
        Challenge normarChallenge = challenges.get(1);
        Challenge successChallenge = challenges.get(2);
        assertEquals(FAIL, failChallenge.getStatus());
        assertEquals(CHALLENGE, normarChallenge.getStatus());
        assertEquals(SUCCESS, successChallenge.getStatus());
//        await().atMost(Duration.of(15, ChronoUnit.SECONDS))
//                .untilAsserted(() -> {
//                    List<Challenge> challenges = challengeService.notAuthTodayCheck();
//                    Challenge failChallenge = challenges.get(0);
//                    Challenge normarChallenge = challenges.get(1);
//                    Challenge successChallenge = challenges.get(2);
//                    assertEquals(FAIL, failChallenge.getStatus());
//                    assertEquals(CHALLENGE, normarChallenge.getStatus());
//                    assertEquals(SUCCESS, successChallenge.getStatus());
//                });
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}