package challenge.server.domain.bookmark.repository;

import challenge.server.domain.habit.entity.Habit;

import java.util.List;

public interface BookmarkCustomRepository {
    List<Habit> findAllByUserUserId(Long lastHabitId, Long userId, int size);
}
