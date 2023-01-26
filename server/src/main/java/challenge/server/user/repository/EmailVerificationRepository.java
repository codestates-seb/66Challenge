package challenge.server.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository, EmailVerificationCustomRepository {
}
