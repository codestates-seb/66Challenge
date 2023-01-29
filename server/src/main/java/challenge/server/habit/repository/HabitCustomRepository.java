package challenge.server.habit.repository;

import challenge.server.habit.entity.Habit;

import java.util.List;

public interface HabitCustomRepository {
    List<Habit> findByTitleIsContaining(Long lastHabitId, String keyword, int size);
    List<Habit> findByCategory(Long lastHabitId, Long categoryId, int szie);
    List<Habit> findByHostUserId(Long lastHabitId, Long userId, int size);

    List<Habit> findAllNoOffset(Long lastHabitId, int size);

    List<Habit> findAllByScore(int page, int size);   // 추천순 정렬

    List<Habit> findAllByPopularity(int page, int size);  // 인기순 정렬

    List<Habit> findAllByNewest(Long lastHabitId, int size);  // 신규순 정렬
}
