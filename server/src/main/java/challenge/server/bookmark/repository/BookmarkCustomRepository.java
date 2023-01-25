package challenge.server.bookmark.repository;

import challenge.server.bookmark.entity.Bookmark;
import challenge.server.habit.entity.Habit;

import java.util.List;

public interface BookmarkCustomRepository {
    List<Habit> findAllByUserUserId(Long lastHabitId, Long userId, int page, int size);
}
