package challenge.server.domain.review.repository;

import challenge.server.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
    Optional<Review> findByHabitHabitIdAndUserUserId(Long habitId, Long userId);
}
