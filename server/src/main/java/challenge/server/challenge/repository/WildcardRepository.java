package challenge.server.challenge.repository;

import challenge.server.challenge.entity.Wildcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WildcardRepository extends JpaRepository<Wildcard, Long> {
    List<Wildcard> findAllByChallengeChallengeId(Long challengeId);
}
