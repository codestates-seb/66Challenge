package challenge.server.review.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.review.entity.Review;
import challenge.server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
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
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        findVerifiedReview(review.getReviewId());
        return reviewRepository.save(review);
    }

    public Review findReview(Long reviewId) {
        return findVerifiedReview(reviewId);
    }

    public List<Review> findAll(int page, int size) {
        return reviewRepository.findAll(PageRequest.of(page - 1, size,
                Sort.by("reviewId").descending())).getContent();
    }

    public void deleteReview(Long reviewId) {
        findVerifiedReview(reviewId);
        reviewRepository.deleteById(reviewId);
    }

//    TODO Review - Habit 테이블 매핑으로 인한 수정 필요
//    public void verfiyExistReview(Long challengeReId) {
//        Optional<Review> optionalReview = reviewRepository.findByChallengeChallengeId(challengeReId);
//        if (optionalReview.isPresent()) {
//            throw new BusinessLogicException(ExceptionCode.REVIEW_EXISTS);
//        }
//    }

    public Review findVerifiedReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
    }
}
