package challenge.server.auth.service;

import challenge.server.auth.entity.Auth;
import challenge.server.auth.repository.AuthRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthRepository authRepository;

    @Transactional
    public Auth createAuth(Auth auth) {
        return authRepository.save(auth);
    }

    @Transactional
    public Auth updateAuth(Auth auth) {
        findVerifiedAuth(auth.getAuthId());
        return authRepository.save(auth);
    }

    public Auth findAuth(Long authId) {
        return findVerifiedAuth(authId);
    }

    public List<Auth> findAll(int page, int size) {
        return authRepository.findAll(PageRequest.of(page, size,
                Sort.by("authId").descending())).getContent();
    }

    @Transactional
    public void deleteAuth(Long authId) {
        authRepository.deleteById(authId);
    }

    private Auth findVerifiedAuth(Long authId) {
        return authRepository.findById(authId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.AUTH_NOT_FOUND));
    }
}
