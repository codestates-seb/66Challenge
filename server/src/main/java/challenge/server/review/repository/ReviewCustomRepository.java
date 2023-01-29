package challenge.server.review.repository;

import challenge.server.review.entity.Review;

import java.util.List;

public interface ReviewCustomRepository {
    Double findAverage(Long habitId);
    List<Review> findAllByHabitHabitId(Long lastReviewId, Long habitId, int size);

    List<Review> findAllNoOffset(Long lasdtReviewId, int size);

}
