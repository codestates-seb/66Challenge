package challenge.server.auth.service;

import challenge.server.auth.entity.Auth;
import challenge.server.auth.repository.AuthRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.service.ChallengeService;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthRepository authRepository;
    private final ChallengeService challengeService;

    @Transactional
    public Auth createAuth(Auth auth, Long challengeId) {
        Challenge challenge = challengeService.findChallenge(challengeId);
        auth.setChallenge(challenge);
        challenge.updatePostedAt(LocalDateTime.now());

        return authRepository.save(auth);
    }

    @Transactional
    public Auth updateAuth(Auth auth) {
        Auth findAuth = findVerifiedAuth(auth.getAuthId());
        findAuth.changeAuth(auth);  // CustomBeanUtils 구현 후 추가 반영

        return findAuth;
    }

    public Auth findAuth(Long authId) {
        return findVerifiedAuth(authId);
    }

    public List<Auth> findAll(int page, int size) {
        return authRepository.findAll(PageRequest.of(page, size,
                Sort.by("authId").descending())).getContent();
    }

    public List<Auth> findAllByChallenge(Long challengeId, int page, int size) {
        return authRepository.findAllByChallengeChallengeId(challengeId,
                PageRequest.of(page - 1, size, Sort.by("authId").descending())).getContent();
    }

    public List<Auth> findAllByHabit(Long habitId, int page, int size) {
        return authRepository.findAllByChallengeHabitHabitId(habitId,
                PageRequest.of(page - 1, size, Sort.by("authId").descending())).getContent();
    }

    @Transactional
    public void deleteAuth(Long authId) {
        findVerifiedAuth(authId);
        authRepository.deleteById(authId);
    }

    public void postAuthCheck(Long challengeId) {     // 시간 기준으로 Auth를 조회해야할까?
//        if (optionalReview.isPresent()) {
//            throw new BusinessLogicException(ExceptionCode.REVIEW_EXISTS);
//        }
    }

    private Auth findVerifiedAuth(Long authId) {
        Auth auth = authRepository.findById(authId).get();
        return authRepository.findById(authId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.AUTH_NOT_FOUND));
    }
}
