package challenge.server.challenge.repository;

import challenge.server.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeCustomRepository {
    List<Challenge> findAllByStatus(Challenge.Status status);
}
