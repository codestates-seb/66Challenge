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

import static challenge.server.challenge.entity.Challenge.Status.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("mm:ss:SSS");
    private final WildcardService wildcardService;

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

    /**
     * 1. 매일 자정 해당 로직 실행
     * 2. 전날 인증글을 게시하지 않은 진행중인 모든 Challenge 조회
     * 3. wildcard를 이미 모두 사용했다면 Challenge Fail
     * 4. 사용할 수 있는 wildcard가 이미 남아있다면 wildcard 사용
     * 5. challenge 마지막 인증 게시글 업로드 시간 업데이트
     * 6. wildcard로 마지막 66일을 채워도 Challenge Success
     */
    @Transactional
    @Scheduled(cron = "${cron.cron1}")
    public List<Challenge> notAuthTodayCheck() {
        log.info("로직 실행시간 - {}", formatter.format(LocalDateTime.now()));
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));     // 익일 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(23, 59, 59));    // 익일 23:59:59

        List<Challenge> challenges = challengeRepository.
                findAllByNotAuthToday(CHALLENGE, startDatetime, endDatetime);

        challenges.forEach(challenge ->
        {
            int wildcardCount = challenge.getWildcards() == null ? 0 : challenge.getWildcards().size();

            if (wildcardCount >= 2) {
                challenge.changeStatus(FAIL);
            } else {
                wildcardService.useWildcard(challenge);
                challenge.updatePostedAt(LocalDateTime.now().minusDays(1));
                if (challenge.getCreatedAt() != null) { // createdAt 추가해줘야함
                    if (challenge.successCheck()) challenge.changeStatus(SUCCESS);
                }
            }
        });
        return challenges;
    }

    public Challenge findVerifiedChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHALLENGE_NOT_FOUND));
    }
}
