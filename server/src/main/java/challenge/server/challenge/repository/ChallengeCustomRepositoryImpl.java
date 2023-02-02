package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.entity.QHabit;
import challenge.server.review.entity.QReview;
import challenge.server.user.dto.UserDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Projection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static challenge.server.auth.entity.QAuth.auth;
import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.challenge.entity.QChallenge.challenge;
import static challenge.server.habit.entity.QHabit.habit;
import static challenge.server.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ChallengeCustomRepositoryImpl implements ChallengeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Challenge> findAllByStatus(Long lastChaallengeId, Challenge.Status status, int size) {
        return jpaQueryFactory
                .selectFrom(challenge)
                .where(
                        challenge.status.eq(status),
                        ltChallengeId(lastChaallengeId)
                ).orderBy(challenge.challengeId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Challenge> findAllByUserUserId(Long lastChaallengeId, Long userId, int size) {
        return jpaQueryFactory
                .selectFrom(challenge)
                .where(
                        challenge.user.userId.eq(userId),
                        ltChallengeId(lastChaallengeId)
                ).orderBy(challenge.challengeId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Challenge> findAllByUserUserIdAndStatus(Long lastChaallengeId, Long userId, Challenge.Status status, int size) {
        return jpaQueryFactory
                .selectFrom(challenge)
                .where(
                        challenge.user.userId.eq(userId),
                        challenge.status.eq(status),
                        ltChallengeId(lastChaallengeId)
                ).orderBy(challenge.challengeId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Auth> findAuthsByChallengeId(Long challengeId) {
        return jpaQueryFactory
                .select(auth)
                .from(challenge)
                .where(challenge.challengeId.eq(challengeId))
                .leftJoin(challenge.auths, auth)
                .on(challenge.challengeId.eq(auth.challenge.challengeId))
                .fetch();
    }

    @Override
    public List<Challenge> findAllByNotAuthToday(Challenge.Status status, LocalDateTime startDatetime, LocalDateTime endDatetime) {

        return jpaQueryFactory
                .selectFrom(challenge)
                .where(
                        challenge.status.eq(CHALLENGE),
                        challenge.lastAuthAt.notBetween(startDatetime, endDatetime)
                                .or(challenge.lastAuthAt.isNull()))
                .fetch();
    }

    @Override
    public Integer findChallengers(Long habitId) {
        return Math.toIntExact(jpaQueryFactory
                .select(challenge.challengeId.count())
                .from(challenge)
                .where(challenge.habit.habitId.eq(habitId).and(challenge.status.eq(CHALLENGE)))
                .fetchOne());
    }

    @Override
    public Integer findAllChallengers(Long habitId) {
        return Math.toIntExact(jpaQueryFactory
                .select(challenge.challengeId.count())
                .from(challenge)
                .where(challenge.habit.habitId.eq(habitId))
                .fetchOne());
    }

    @Override
    public List<Challenge> findAllNoOffset(Long lastChallengeId, int size) {
        return jpaQueryFactory
                .selectFrom(challenge)
                .where(ltChallengeId(lastChallengeId))
                .orderBy(challenge.challengeId.desc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression ltChallengeId(Long lastChallengeId) {
        if (lastChallengeId == null) {
            return null;
        }
        return challenge.challengeId.lt(lastChallengeId);
    }

    @Override
    public List<UserDto.CategoriesResponse> findFavoriteCategories(Long userId) {
        return jpaQueryFactory
                .select(Projections.fields(UserDto.CategoriesResponse.class, challenge.challengeId.count().as("count"),
                        challenge.habit.category.categoryId))
                .from(challenge)
                .where(challenge.user.userId.eq(userId))
                .leftJoin(challenge.habit, habit)
                .on(challenge.habit.habitId.eq(habit.habitId))
                .groupBy(challenge.habit.category.categoryId)
                .orderBy(challenge.challengeId.count().desc())
                .fetch();
    }
}
