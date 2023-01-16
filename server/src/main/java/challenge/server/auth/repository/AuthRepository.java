package challenge.server.auth.repository;

import challenge.server.auth.entity.Auth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Page<Auth> findAllByChallengeChallengeId(Long challengeId, PageRequest authId);

    Page<Auth> findAllByChallengeHabitHabitId(Long habitId, Pageable pageable);
}
