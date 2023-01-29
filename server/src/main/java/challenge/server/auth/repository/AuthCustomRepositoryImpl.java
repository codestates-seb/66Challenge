package challenge.server.auth.repository;

import challenge.server.auth.entity.Auth;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge.server.auth.entity.QAuth.auth;
import static challenge.server.habit.entity.QHabit.habit;

@Repository
@RequiredArgsConstructor
public class AuthCustomRepositoryImpl implements AuthCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Auth> findAllByChallengeChallengeId(Long lastAuthId, Long challengeId, int size) {
        return jpaQueryFactory
                .selectFrom(auth)
                .where(
                        ltAuthId(lastAuthId),
                        auth.challenge.challengeId.eq(challengeId)
                ).orderBy(auth.authId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Auth> findAllByChallengeHabitHabitId(Long lastId, Long habitId, int size) {
        return jpaQueryFactory
                .selectFrom(auth)
                .where(
                        auth.challenge.habit.habitId.eq(habitId),
                        ltAuthId(lastId)
                ).orderBy(auth.authId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Auth> findAllByChallengeHabitHabitId(Long lastId, Long habitId, int size) {
        return jpaQueryFactory
                .selectFrom(auth)
                .where(
                        auth.challenge.habit.habitId.eq(habitId),
                        ltAuthId(lastId)
                ).orderBy(auth.authId.desc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression ltAuthId(Long lastAuthId) {
        if (lastAuthId == null) {
            return null;
        }
        return auth.authId.lt(lastAuthId);
    }
}
