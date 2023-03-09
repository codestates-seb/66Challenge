package challenge.server.domain.auth.repository;

import challenge.server.domain.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long>, AuthCustomRepository {
}
