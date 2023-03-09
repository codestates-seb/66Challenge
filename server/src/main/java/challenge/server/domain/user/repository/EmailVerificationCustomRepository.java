package challenge.server.domain.user.repository;

import challenge.server.domain.user.entity.EmailVerification;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailVerificationCustomRepository {
    Optional<EmailVerification> findValidVerificationByEmail(String email, String verificationToken, LocalDateTime currentTime);
}
