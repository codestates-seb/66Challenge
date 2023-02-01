package challenge.server.habit.repository;

import challenge.server.habit.entity.Habit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long>, HabitCustomRepository {
    List<Habit> findByHostUserId(Long userId);
}