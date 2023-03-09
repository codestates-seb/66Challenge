package challenge.server.domain.challenge.repository;

import challenge.server.domain.challenge.entity.Wildcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WildcardRepository extends JpaRepository<Wildcard, Long> {
    List<Wildcard> findAllByChallengeChallengeId(Long challengeId);
}
