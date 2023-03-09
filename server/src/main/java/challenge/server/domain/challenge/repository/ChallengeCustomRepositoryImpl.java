package challenge.server.domain.challenge.repository;

import challenge.server.domain.auth.entity.Auth;
import challenge.server.domain.challenge.entity.Challenge;

import challenge.server.domain.habit.entity.Habit;
import challenge.server.domain.user.dto.UserDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static challenge.server.domain.auth.entity.QAuth.auth;
import static challenge.server.domain.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.domain.challenge.entity.QChallenge.challenge;
import static challenge.server.domain.habit.entity.QHabit.habit;

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

//    @Override
//    public List<ChallengeDto.Response> findAllByUserUserIdAndStatus(Long lastChaallengeId, Long userId, Challenge.Status status, int size) {
//        return jpaQueryFactory
//                .select(
//                        challenge.challengeId,
//                        challenge.habit.title,
//                        challenge.habit.subTitle,
//                        challenge.habit.challengers,
//                        challenge.status,
//                        challenge.wildcards.size())
//                .from(challenge)
//                .where(
//                        challenge.user.userId.eq(userId),
//                        challenge.status.eq(status),
//                        ltChallengeId(lastChaallengeId)
//                ).orderBy(challenge.challengeId.desc())
//                .limit(size)
//                .fetch();
//    }

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

    // 내가 참여 중인 습관 목록
    public List<Habit> findHabitsByUserId(Long userId) {
        return jpaQueryFactory
                .select(challenge.habit)
                .from(challenge)
                .where(challenge.user.userId.eq(userId))
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
