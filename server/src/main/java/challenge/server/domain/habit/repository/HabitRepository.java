package challenge.server.domain.habit.repository;

import challenge.server.domain.habit.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long>, HabitCustomRepository {
    List<Habit> findByHostUserId(Long userId);
}