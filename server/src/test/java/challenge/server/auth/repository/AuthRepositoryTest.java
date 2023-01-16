package challenge.server.auth.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.challenge.repository.WildcardRepository;
import challenge.server.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
class AuthRepositoryTest {

    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private AuthRepository authRepository;

    @Test
    @DisplayName(value = "특정 챌린지의 모든 인증게시글 조회")
    void findAllByChallengeChallengeId() throws Exception {
        // given
        Challenge challenge1 = Challenge.builder().status(CHALLENGE).build();
        Challenge saveChallenge1 = challengeRepository.save(challenge1);
        Challenge challenge2 = Challenge.builder().status(CHALLENGE).build();
        Challenge saveChallenge2 = challengeRepository.save(challenge2);

        Auth auth1 = Auth.builder().challenge(saveChallenge1).body("인증 내용1").build();
        Auth auth2 = Auth.builder().challenge(saveChallenge1).body("인증 내용3").build();

        authRepository.save(auth1);
        authRepository.save(auth2);

        Pageable pageable = PageRequest.of(0, 30, Sort.by("authId").descending());

        // when
        List<Auth> auths = authRepository.findAllByChallengeChallengeId(saveChallenge1.getChallengeId(), pageable).getContent();
        List<Auth> auths2 = authRepository.findAllByChallengeChallengeId(saveChallenge2.getChallengeId(), pageable).getContent();

        // then
        assertEquals(2, auths.size());
        assertEquals(0, auths2.size());
    }

    @Test
    @DisplayName(value = "특정 습관의 모든 인증게시글 조회")
    void findAllByChallengeHabitHabitId() throws Exception {    // Habit Repository 구현 된 이후 테스트

    }
}