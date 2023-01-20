package challenge.server.habit.repository;

import challenge.server.habit.entity.Habit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Page<Habit> findByTitleIsContaining(String keyword, Pageable pageable);
    Page<Habit> findByCategoryCategoryId(Long categoryId, Pageable pageable);
    Page<Habit> findByHostUserId(Long userId, Pageable pageable);

    // User 마이페이지로부터 '내가 만든 습관 조회' 관련 추가
    Page<Habit> findAllByHostUserId(Long userId, Pageable pageable);
}