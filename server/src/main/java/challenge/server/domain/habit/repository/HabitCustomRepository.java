package challenge.server.domain.habit.repository;

import challenge.server.domain.habit.entity.Habit;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HabitCustomRepository {
    List<Habit> findByTitleIsContaining(Long lastHabitId, String keyword, int size);
    List<Habit> findByCategory(Long lastHabitId, Long categoryId, int szie);
    List<Habit> findByHostUserId(Long lastHabitId, Long userId, int size);

    List<Habit> findAllNoOffset(Long lastHabitId, int size);

    List<Habit> findAllByScore(Pageable pageable);   // 추천순 정렬

    List<Habit> findAllByPopularity(Pageable pageable);  // 인기순 정렬

    List<Habit> findAllByNewest(Long lastHabitId, int size);  // 신규순 정렬
}
