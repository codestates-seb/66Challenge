package challenge.server.review.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.review.entity.Review;
import challenge.server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        verfiyExistReview(review.getHabit().getHabitId(), review.getUser().getUserId());

        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        Review findReview = findVerifiedReview(review.getReviewId());
        findReview.changeReview(review);    // CustomBeanUtils 구현 후 수정

        return findReview;
    }

    public Review findReview(Long reviewId) {
        return findVerifiedReview(reviewId);
    }

    public List<Review> findAll(int page, int size) {
        return reviewRepository.findAll(PageRequest.of(page - 1, size,
                Sort.by("reviewId").descending())).getContent();
    }

    public List<Review> findAllByHabit(Long habitId, int page, int size) {
        return reviewRepository.findAllByHabitHabitId(habitId,
                PageRequest.of(page - 1, size, Sort.by("reviewId").descending())).getContent();
    }

    public void deleteReview(Long reviewId) {
        findVerifiedReview(reviewId);
        reviewRepository.deleteById(reviewId);
    }

    public void verfiyExistReview(Long habitId, Long userId) {
        Optional<Review> optionalReview = reviewRepository.findByHabitHabitIdAndUserUserId(habitId, userId);
        if (optionalReview.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.REVIEW_EXISTS);
        }
    }

    public Review findVerifiedReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
    }
}
