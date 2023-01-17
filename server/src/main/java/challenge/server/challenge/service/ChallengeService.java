package challenge.server.challenge.service;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.challenge.entity.Challenge.Status.FAIL;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("mm:ss:SSS");
    private final WildcardService wildcardService;

    private final EntityManager em;

    @Transactional
    public Challenge createChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    @Transactional
    public Challenge changeStatus(Long challengeId, Challenge.Status status) {
        Challenge challenge = findVerifiedChallenge(challengeId);
        challenge.changeStatus(status);

        return challenge;
    }

    public Challenge findChallenge(Long challengeId) {
        return findVerifiedChallenge(challengeId);
    }

    // 특정 챌린지의 모든 인증 게시글 조회
    public List<Auth> findAuthsByChallengeId(Long challengeId) {
        // TODO: QueryDSL 페이지네이션 구현 방식 결정 후 수정
        return challengeRepository.findAuthsByChallengeId(challengeId);
    }

    // 특정 회원의 모든 챌린지 조회
    public List<Challenge> findAllByUser(Long userId, int page, int size) {
        // TODO: QueryDSL 페이지네이션 구현 방식 결정 후 수정
        return challengeRepository.findAllByUserUserId(userId,
                PageRequest.of(page - 1, size, Sort.by("challengeId").descending())).getContent();
    }

    // 특정 상태의 모든 챌린지 조회
    public List<Challenge> findAllStatus(Challenge.Status status, int page, int size) {
        // TODO: QueryDSL 페이지네이션 구현 방식 결정 후 수정
        return challengeRepository.findAllByStatus(status,
                PageRequest.of(page - 1, size, Sort.by("challengeId").descending())).getContent();
    }

    // 특정 회원의 특정 상태의 모든 챌린지 조회
    public List<Challenge> findAllByUserAndStatus(Long userId, Challenge.Status status, int page, int size) {
        // TODO: QueryDSL 페이지네이션 구현 방식 결정 후 수정
        return challengeRepository.findAllByUserUserIdAndStatus(userId, status,
                PageRequest.of(page - 1, size, Sort.by("challengeId").descending())).getContent();
    }

    public List<Challenge> findAll(int page, int size) {
        return challengeRepository.findAll(PageRequest.of(page - 1, size,
                Sort.by("challengeId").descending())).getContent();
    }

    @Transactional
    @Scheduled(cron = "${cron.cron1}")  // 매일 자정 해당 메서드가 실행됨
    public List<Challenge> notAuthTodayCheck() {
        log.info("현재시간 - {}", formatter.format(LocalDateTime.now()));
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));     // 익일 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(23, 59, 59));    // 익일 23:59:59

        // 전날 인증글을 게시하지 않은 모든 Challenge 조회
        List<Challenge> challenges = challengeRepository.
                findAllByNotAuthToday(CHALLENGE, startDatetime, endDatetime);

        challenges.forEach(challenge ->
        {
            int wildcardCount = challenge.getWildcards() == null ? 0 : challenge.getWildcards().size();

            if (wildcardCount >= 2) {   // wildcard를 이미 모두 사용했다면
                challenge.changeStatus(FAIL);   // Challenge Fail
            } else {    // 사용할 수 있는 wildcard가 아직 남아있다면
                wildcardService.useWildcard(challenge);    // wildcard 사용
                challenge.updatePostedAt(LocalDateTime.now().minusDays(1));     // challenge 마지막 인증 게시글 업로드 시간 업데이트
            }
        });
        return challenges;
    }

    public Challenge findVerifiedChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHALLENGE_NOT_FOUND));
    }
}
