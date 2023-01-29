package challenge.server.security.user.repository;

import challenge.server.security.user.entity.LogoutList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogoutListRepository extends JpaRepository<LogoutList, Long> {
    Optional<LogoutList> findByAccessToken(String accessToken);
}
