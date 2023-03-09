package challenge.server.domain.auth.service;

import challenge.server.domain.auth.dto.AuthDto;
import challenge.server.domain.auth.entity.Auth;
import challenge.server.domain.auth.mapper.AuthMapper;
import challenge.server.domain.auth.repository.AuthRepository;
import challenge.server.domain.challenge.entity.Challenge;
import challenge.server.domain.challenge.service.ChallengeService;
import challenge.server.common.exception.BusinessLogicException;
import challenge.server.common.exception.ExceptionCode;
import challenge.server.file.service.FileUploadService;
import challenge.server.domain.user.service.UserService;
import challenge.server.common.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static challenge.server.domain.challenge.entity.Challenge.Status.SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthRepository authRepository;
    private final ChallengeService challengeService;
    private final CustomBeanUtils<Auth> beanUtils;
    private final FileUploadService fileUploadService;
    private final AuthMapper authMapper;
    private final UserService userService;

    @Transactional
    public AuthDto.Response createAuth(Auth auth, Long challengeId) {
        Challenge challenge = challengeService.findVerifiedChallenge(challengeId);
        auth.setChallenge(challenge);
        challenge.updatePostedAt(LocalDateTime.now());

        // 챌린지 성공 여부 확인
        // TODO: 인증성 발급 방법 논의 필요
        if (challenge.successCheck()) {
            challenge.changeStatus(SUCCESS);
        }
        Auth saveAuth = authRepository.save(auth);
        Auth findAuth = authRepository.findById(saveAuth.getAuthId()).get();

        AuthDto.Response response = authMapper.toDto(findAuth);

        return response;
    }

    @Transactional
    public AuthDto.Response updateAuth(Auth auth) {
        Auth findAuth = findVerifiedAuth(auth.getAuthId());
        fileUploadService.delete(findAuth.getAuthImageUrl());
        Auth changeAuth = beanUtils.copyNonNullProperties(auth, findAuth);
        Auth updateAuth = authRepository.save(changeAuth);

        return authMapper.toDto(updateAuth);
    }

    public Auth findAuth(Long authId) {
        return findVerifiedAuth(authId);
    }

    public List<Auth> findAllByChallenge(Long lastAuthId, Long challengeId, int size) {
        return authRepository.findAllByChallengeChallengeId(lastAuthId, challengeId, size);
    }

    public List<AuthDto.Response> findAllByHabit(Long lastAuthId, Long habitId, int size) {
        List<Auth> auths = authRepository.findAllByChallengeHabitHabitId(lastAuthId, habitId, size);
        return authMapper.toDtos(auths);
    }

    @Transactional
    public void deleteAuth(Long authId) {
        Auth auth = findVerifiedAuth(authId);
        fileUploadService.delete(auth.getAuthImageUrl());
        authRepository.deleteById(authId);
    }

    private Auth findVerifiedAuth(Long authId) {
        return authRepository.findById(authId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.AUTH_NOT_FOUND));
    }
}