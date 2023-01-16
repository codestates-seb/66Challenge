package challenge.server.challenge.service;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.challenge.repository.WildcardRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final WildcardRepository wildcardRepository;

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

    // 특정 상태의 모든 챌린지 조회
    public List<Challenge> findAllStatus(Challenge.Status status) {
        // TODO: QueryDSL 페이지네이션 구현 방식 결정 후 수정
        return challengeRepository.findAllByStatus(status);
    }

    public List<Challenge> findAll(int page, int size) {
        return challengeRepository.findAll(PageRequest.of(page - 1, size,
                Sort.by("challengeId").descending())).getContent();
    }

    @Scheduled(cron = "${cron.cron1}")  // 매일 자정 해당 메서드가 실행됨
    public void postAuthCheck() {
        // 오늘 날짜가 포함된 Auth를 포함하지 않은 Challenge를 모두 조회
        List<Challenge> allByChallenge = challengeRepository.findAllByStatus(CHALLENGE);

        //
    }

    public Challenge findVerifiedChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHALLENGE_NOT_FOUND));
    }
}
