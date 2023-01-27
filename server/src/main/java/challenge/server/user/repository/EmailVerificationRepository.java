package challenge.server.user.repository;

import challenge.server.user.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long>, EmailVerificationCustomRepository  {
    Optional<EmailVerification> findByEmail(String email);
}
