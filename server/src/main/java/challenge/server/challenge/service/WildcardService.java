package challenge.server.challenge.service;

import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import challenge.server.challenge.repository.WildcardRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WildcardService {
    private final WildcardRepository wildcardRepository;

    @Transactional
    public Wildcard useWildcard(Challenge challenge) {
        Wildcard wildcard = Wildcard.builder().challenge(challenge).build();

        return wildcardRepository.save(wildcard);
    }

    public Wildcard findWildcard(Long wildcardId) {
        return findVerifiedWildcard(wildcardId);
    }

    public List<Wildcard> findAllByChallenge(Long challengeId) {
        return wildcardRepository.findAllByChallengeChallengeId(challengeId);
    }

    public Wildcard findVerifiedWildcard(Long wildcardId) {
        return wildcardRepository.findById(wildcardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.WILDCARD_NOT_FOUND));
    }
}
