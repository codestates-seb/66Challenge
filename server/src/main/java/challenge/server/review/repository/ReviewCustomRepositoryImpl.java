package challenge.server.review.repository;

import challenge.server.review.entity.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge.server.habit.entity.QHabit.habit;
import static challenge.server.review.entity.QReview.review;

@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Double findAverage(Long habitId) {
        return jpaQueryFactory
                .select(review.score.avg())
                .from(habit)
                .where(habit.habitId.eq(habitId))
                .leftJoin(habit.reviews, review)
                .on(habit.habitId.eq(review.habit.habitId))
                .fetchOne();
    }

    @Override
    public List<Review> findAllByHabitHabitId(Long lastReviewId, Long habitId, int size) {
        return jpaQueryFactory
                .selectFrom(review)
                .where(
                        review.habit.habitId.eq(habitId),
                        ltReviewId(lastReviewId)
                ).orderBy(review.reviewId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Review> findAllNoOffset(Long lasdtReviewId, int size) {
        return jpaQueryFactory
                .selectFrom(review)
                .where(ltReviewId(lasdtReviewId))
                .orderBy(review.reviewId.desc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression ltReviewId(Long lastReviewId) {
        if (lastReviewId == null) {
            return null;
        }
        return review.reviewId.lt(lastReviewId);
    }
}
