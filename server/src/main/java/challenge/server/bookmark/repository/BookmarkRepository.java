package challenge.server.bookmark.repository;

import challenge.server.bookmark.entity.Bookmark;
import challenge.server.habit.entity.Habit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkCustomRepository {
//    Page<Bookmark> findAllByUserUserId(Long userId, Pageable pageable);
    /*
    특정 회원이 찜한 습관 조회
    select b.habit_id
    from bookmark b
    where b.user_id = #;

    특정 회원이 host인 습관 중 해당 회원이 찜한 습관
    select b.habit_id
    from bookmark b
    left join habit h
    on b.habit_id = h.habit_id
    where h.host_id = # && b.user_id = #;
     */
    // 특정 회원이 찜한 습관 조회기능이 필요하여 추가했습니다. 쿼리 사용하게 되신다면 삭제해주세요.
    Optional<Bookmark> findByUserUserIdAndHabitHabitId(Long userId, Long habitId);

    List<Bookmark> findAllByUserUserId(Long userId);
}
