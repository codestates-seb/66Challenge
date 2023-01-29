package challenge.server.habit.repository;

import challenge.server.habit.entity.Habit;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge.server.habit.entity.QHabit.habit;

@Repository
@RequiredArgsConstructor
public class HabitCustomRepositoryImpl implements HabitCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Habit> findByTitleIsContaining(Long lastHabitId, String keyword, int size) {
        return jpaQueryFactory
                .selectFrom(habit)
                .where(
                        // no-offset 페이징 처리
                        ltHabitId(lastHabitId),
                        habit.title.contains(keyword)
                ).orderBy(habit.habitId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Habit> findByCategory(Long lastHabitId, Long categoryId, int size) {
        return jpaQueryFactory
                .selectFrom(habit)
                .where(
                        ltHabitId(lastHabitId),
                        habit.category.categoryId.eq(categoryId)
                ).orderBy(habit.habitId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Habit> findByHostUserId(Long lastHabitId, Long userId, int size) {
        return jpaQueryFactory
                .selectFrom(habit)
                .where(
                        ltHabitId(lastHabitId),
                        habit.host.userId.eq(userId)
                ).orderBy(habit.habitId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Habit> findAllNoOffset(Long lastHabitId, int size) {
        return jpaQueryFactory
                .selectFrom(habit)
                .where(ltHabitId(lastHabitId))
                .orderBy(habit.habitId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Habit> findAllByScore(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(habit)
                .orderBy(habit.avgScore.desc(), habit.bookmarks.size().desc(), habit.habitId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Habit> findAllByPopularity(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(habit)
                .orderBy(habit.challengers.desc(), habit.avgScore.desc(), habit.bookmarks.size().desc(), habit.habitId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Habit> findAllByNewest(Long lastHabitId, int size) {
        return jpaQueryFactory
                .selectFrom(habit)
                .where(ltHabitId(lastHabitId))
                .orderBy(habit.habitId.desc())
                .limit(size)
                .fetch();
    }

    // no-offset 방식 처리하는 메서드
    private BooleanExpression ltHabitId(Long lastHabitId) {
        if (lastHabitId == null) {
            return null;    // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제외
        }
        return habit.habitId.lt(lastHabitId);
    }
}


