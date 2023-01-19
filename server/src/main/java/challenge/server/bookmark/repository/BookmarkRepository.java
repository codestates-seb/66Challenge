package challenge.server.bookmark.repository;

import challenge.server.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Page<Bookmark> findAllByUserUserId(Long userId, Pageable pageable);
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
}
