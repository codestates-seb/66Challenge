package challenge.server.challenge.service;

import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import challenge.server.challenge.repository.WildcardRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WildcardService {
    private final WildcardRepository wildcardRepository;
    private final ChallengeService challengeService;

    @Transactional
    public Challenge useWildcard(Long challengeId) {
        Challenge challenge = challengeService.findVerifiedChallenge(challengeId);
        Wildcard wildcard = Wildcard.builder().challenge(challenge).build();
        wildcardRepository.save(wildcard);

        return challenge;
    }

    public Wildcard findWildcard(Long wildcardId) {
        return findVerifiedWildcard(wildcardId);
    }

    public Wildcard findVerifiedWildcard(Long wildcardId) {
        return wildcardRepository.findById(wildcardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.WILDCARD_NOT_FOUND));
    }
}
