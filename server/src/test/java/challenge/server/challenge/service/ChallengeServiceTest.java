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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.challenge.entity.Challenge.Status.FAIL;
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
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 15, 1, 1, 1);
        Wildcard wildcard = Wildcard.builder().build();
        Challenge challenge1 = Challenge.builder().challengeId(1L).status(CHALLENGE).wildcards(List.of(wildcard, wildcard)).build();
        Challenge challenge2 = Challenge.builder().challengeId(2L).status(CHALLENGE).build();

        given(challengeRepository.findAllByNotAuthToday(Mockito.any(Challenge.Status.class), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .willReturn(List.of(challenge1, challenge2));
        given(wildcardService.useWildcard(Mockito.any(Challenge.class))).willReturn(wildcard);

        // when
        await().atMost(Duration.of(15, ChronoUnit.SECONDS))
                .untilAsserted(() -> {
                    List<Challenge> challenges = challengeService.notAuthTodayCheck();
                    Challenge findChallenge1 = challenges.get(0);
                    Challenge findChallenge2 = challenges.get(1);
                    assertEquals(FAIL, findChallenge1.getStatus());
                    assertEquals(CHALLENGE, findChallenge2.getStatus());
                });
    }
}