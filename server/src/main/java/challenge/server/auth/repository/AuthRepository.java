package challenge.server.auth.repository;

import challenge.server.auth.entity.Auth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Page<Auth> findAllByChallengeChallengeId(Long challengeId, Pageable pageable);

    Page<Auth> findAllByChallengeHabitHabitId(Long habitId, Pageable pageable);
}
