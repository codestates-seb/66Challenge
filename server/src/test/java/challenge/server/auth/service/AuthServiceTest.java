package challenge.server.auth.service;

import challenge.server.auth.entity.Auth;
import challenge.server.auth.repository.AuthRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.challenge.service.ChallengeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.challenge.entity.Challenge.Status.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private ChallengeService challengeService;

    @Test
    @DisplayName(value = "챌린지 성공 여부 자동 체크 테스트")
    void successCheck() throws Exception {
        // given
        LocalDateTime createdAt = LocalDateTime.now().minusDays(66);
        Challenge challenge = Challenge.builder().challengeId(1L).status(CHALLENGE).build();
        challenge.setCreatedAt(createdAt);
        Auth auth = Auth.builder().build();

        given(challengeService.findChallenge(Mockito.anyLong())).willReturn(challenge);
        given(authRepository.save(Mockito.any(Auth.class))).willReturn(auth);

        // when
        authService.createAuth(auth, challenge);

        // then
        assertEquals(SUCCESS, challenge.getStatus());
    }
}