package challenge.server.user.repository;

import challenge.server.user.entity.EmailVerification;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailVerificationCustomRepository {
    Optional<EmailVerification> findValidVerificationByEmail(String email, String verificationToken, LocalDateTime currentTime);
}
