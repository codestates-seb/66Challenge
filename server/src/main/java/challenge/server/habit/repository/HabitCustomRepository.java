package challenge.server.habit.repository;

import challenge.server.habit.entity.Habit;

import java.util.List;

public interface HabitCustomRepository {
    List<Habit> findByTitleIsContaining(Long lastHabitId, String keyword, int page, int size);
    List<Habit> findByCategory(Long lastHabitId, Long categoryId, int page, int szie);
    List<Habit> findByHostUserId(Long lastHabitId, Long userId, int page, int size);

    List<Habit> findAllNoOffset(Long lastHabitId, int page, int size);
}
