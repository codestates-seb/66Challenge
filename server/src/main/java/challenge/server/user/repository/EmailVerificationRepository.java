package challenge.server.user.repository;

import challenge.server.user.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long>, EmailVerificationCustomRepository  {
}
