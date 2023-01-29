package challenge.server.bookmark.repository;

import challenge.server.habit.entity.Habit;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge.server.bookmark.entity.QBookmark.bookmark;
import static challenge.server.habit.entity.QHabit.habit;

@Repository
@RequiredArgsConstructor
public class BookmarkCustomRepositoryImpl implements BookmarkCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Habit> findAllByUserUserId(Long lastHabitId, Long userId, int size) {
        return jpaQueryFactory
                .select(habit)
                .from(bookmark)
                .where(
                        bookmark.user.userId.eq(userId)
                )
                .leftJoin(bookmark.habit, habit)
                .on(bookmark.habit.habitId.eq(habit.habitId).and(ltHabitId(lastHabitId)))
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
