package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.auth.repository.AuthRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import challenge.server.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.*;
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
    @Autowired
    private EntityManager em;

//    @BeforeEach
//    public void saveChallenge() throws Exception {
//        Challenge challenge1 = Challenge.builder().status(CHALLENGE).build();
//        Challenge saveChallenge1 = challengeRepository.save(challenge1);
//        Challenge challenge2 = Challenge.builder().status(CHALLENGE).build();
//        Challenge saveChallenge2 = challengeRepository.save(challenge2);
//        Challenge challenge3 = Challenge.builder().status(FAIL).build();
//        Challenge saveChallenge3 = challengeRepository.save(challenge3);
//        Challenge challenge4 = Challenge.builder().status(CHALLENGE).build();
//        Challenge saveChallenge4 = challengeRepository.save(challenge4);
//        Challenge challenge5 = Challenge.builder().status(CHALLENGE).build();
//        Challenge saveChallenge5 = challengeRepository.save(challenge5);
//        Challenge challenge6 = Challenge.builder().status(CHALLENGE).build();
//        Challenge saveChallenge6 = challengeRepository.save(challenge6);
//        Challenge challenge7 = Challenge.builder().status(CHALLENGE).build();
//        Challenge saveChallenge7 = challengeRepository.save(challenge7);
//
//        Auth auth1 = Auth.builder().challenge(saveChallenge1).body("인증 내용1").build();
//        Auth auth2 = Auth.builder().challenge(saveChallenge3).body("인증 내용3").build();
//
//        authRepository.save(auth1);
//        authRepository.save(auth2);
//
//        em.flush();
//        em.clear();
//    }

    @DisplayName(value = "특정 챌린지의 모든 인증 게시글 조회")
    @Test
    void findAuthsByChallengeId() throws Exception {
        // given
        Challenge challenge1 = Challenge.builder().status(CHALLENGE).build();
        Challenge saveChallenge1 = challengeRepository.save(challenge1);
        Challenge challenge2 = Challenge.builder().status(CHALLENGE).build();
        Challenge saveChallenge2 = challengeRepository.save(challenge2);

        Auth auth1 = Auth.builder().challenge(saveChallenge1).body("인증 내용1").build();
        Auth auth2 = Auth.builder().challenge(saveChallenge1).body("인증 내용3").build();

        authRepository.save(auth1);
        authRepository.save(auth2);

        // when
        List<Auth> auths = challengeRepository.findAuthsByChallengeId(saveChallenge1.getChallengeId());
        List<Auth> auths2 = challengeRepository.findAuthsByChallengeId(saveChallenge2.getChallengeId());

        // then
        assertEquals(2, auths.size());
        assertEquals(null, auths2.get(0));
    }

//    @DisplayName(value = "특정 상태의 모든 챌린지 조회")
//    @Test
//    void findAllByStatus() throws Exception {
//        int page = 1;
//        int size = 30;
//
//        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("challengeId").descending());
//
//        // when
//        List<Challenge> challenges = challengeRepository.findAllByStatus(CHALLENGE, pageable).getContent();
//        List<Challenge> success = challengeRepository.findAllByStatus(SUCCESS, pageable).getContent();
//        List<Challenge> fail = challengeRepository.findAllByStatus(FAIL, pageable).getContent();
//
//        // then
//        assertEquals(2, challenges.size());
//        assertEquals(0, success.size());
//        assertEquals(1, fail.size());
//    }

    @Test
    @DisplayName(value = "당일 인증하지 않은 게시물 체크")
    void notAuthTodayCheck() throws Exception {
        // given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 15, 1, 1, 1);
        Challenge challenge1 = Challenge.builder().status(CHALLENGE).lastPostedAt(localDateTime).build();
        Challenge saveChallenge1 = challengeRepository.save(challenge1);
        Challenge challenge2 = Challenge.builder().status(CHALLENGE).build();
        Challenge saveChallenge2 = challengeRepository.save(challenge2);

        wildcardRepository.save(Wildcard.builder().challenge(saveChallenge1).build());
        wildcardRepository.save(Wildcard.builder().challenge(saveChallenge1).build());

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.of(2023, 1, 16), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.of(2023, 1, 16), LocalTime.of(23, 59, 59));

        // when
        List<Challenge> findAll = challengeRepository.findAllByNotAuthToday(CHALLENGE, startDatetime, endDatetime);

        findAll.forEach(challenge ->
                System.out.print("챌린지 상태: " + challenge.getStatus() + "\n"
                        + "챌린지 Id: " + challenge.getChallengeId() + "\n"
                        + "챌린지 마지막 인증 시간: " + challenge.getLastPostedAt() + "\n"
                        + "-----------------------------------------------------\n"));

        // then
        assertTrue(findAll.contains(saveChallenge1));
        assertTrue(findAll.contains(saveChallenge2));
        assertEquals(30, findAll.size());
    }
}
