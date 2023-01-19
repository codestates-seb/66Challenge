package challenge.server.challenge.service;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.habit.entity.Habit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.*;
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
    }

    @Test
    @DisplayName(value = "당일 인증글 중복 체크")
    void todayAuthCheck1() throws Exception {

        // given
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        Habit habit = Habit.builder().authStartTime(startTime).authEndTime(endTime).build();

        Challenge challenge = Challenge.builder().lastAuthAt(LocalDateTime.now()).habit(habit).build();   // 마지막 인증 게시일이 오늘인 challenge

        // when // then
        assertThrows(BusinessLogicException.class, () -> challenge.todayAuthCheck(LocalDateTime.now()));
    }

    @Test
    @DisplayName(value = "정해진 시간 내에만 인증 가능")
    void todayAuthCheck2() throws Exception {

        // given
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        LocalDateTime fTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LocalDateTime sTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0));

        Habit habit = Habit.builder().authStartTime(startTime).authEndTime(endTime).build();

        Challenge sChallenge = Challenge.builder().lastAuthAt(LocalDateTime.now().minusDays(1)).habit(habit).build();
        Challenge fChallenge = Challenge.builder().lastAuthAt(LocalDateTime.now().minusDays(1)).habit(habit).build();

        // when // then
        assertDoesNotThrow(() -> sChallenge.todayAuthCheck(sTime));
        assertThrows(BusinessLogicException.class, () -> fChallenge.todayAuthCheck(fTime));
    }
}