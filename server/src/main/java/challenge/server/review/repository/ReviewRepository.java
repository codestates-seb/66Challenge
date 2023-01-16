package challenge.server.review.repository;

import challenge.server.challenge.entity.Challenge;
import challenge.server.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
//    Optional<Review> findByChallengeChallengeId(Long challengeReId);
//    TODO Review - Habit 테이블 매핑으로 인한 수정 필요
}
