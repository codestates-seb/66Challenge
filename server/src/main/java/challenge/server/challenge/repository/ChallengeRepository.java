package challenge.server.challenge.repository;

import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Challenge.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeCustomRepository {
    Page<Challenge> findAllByStatus(Status status, Pageable pageable);
    Page<Challenge> findAllByUserUserId(Long userId, Pageable pageable);
    Page<Challenge> findAllByUserUserIdAndStatus(Long userId, Challenge.Status status, Pageable pageable);

}
