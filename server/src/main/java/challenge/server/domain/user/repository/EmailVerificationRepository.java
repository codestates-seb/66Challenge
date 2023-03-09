package challenge.server.domain.user.repository;

import challenge.server.domain.user.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long>, EmailVerificationCustomRepository  {
    Optional<EmailVerification> findByEmail(String email);
}
