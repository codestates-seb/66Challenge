package challenge.server.challenge.repository;

import challenge.server.challenge.entity.ChallengeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeStatusRepository extends JpaRepository<ChallengeStatus, Long>, ChallengeStatusCustomRepository {
}
