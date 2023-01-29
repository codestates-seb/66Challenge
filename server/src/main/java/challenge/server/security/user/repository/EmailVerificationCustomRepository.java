package challenge.server.security.user.repository;

import challenge.server.security.user.entity.EmailVerification;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailVerificationCustomRepository {
    Optional<EmailVerification> findValidVerificationByEmail(String email, String verificationToken, LocalDateTime currentTime);
}
