package challenge.server.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

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
}
