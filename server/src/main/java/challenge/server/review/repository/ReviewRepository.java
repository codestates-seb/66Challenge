package challenge.server.review.repository;

import challenge.server.challenge.entity.Challenge;
import challenge.server.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByHabitHabitIdAndUserUserId(Long habitId, Long userId);

    Page<Review> findAllByHabitHabitId(Long habitId, Pageable pageable);
}
