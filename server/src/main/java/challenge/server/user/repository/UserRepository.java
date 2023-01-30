package challenge.server.user.repository;

import challenge.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>/*, UserCustomRepository*/ {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByRefreshToken(String refreshToken);

    // 5회 이상 신고 당한 회원 정지
    /* 신고 테이블에 특정 회원에 대한 신고 회수가 5회가 되는 시점에
    그 회원의 상태를 banned로 바꾸기
     */

    /* challenge 테이블에서 특정 회원의 습관 형성 현황 조회
    select c.challenge_id, c.user_id, c.habit_id, h.subtitle, count(auth_id) + count(wildcard_id)
    from challenge c
    left outer join habit h on c.habit_id = h.habit_id
    left outer join auth a on c.challenge_id = a.challenge_id
    left outer join wildcard w on c.challenge_id = w.challenge_id
    where user_id == # && (challenge_status_id == 1 || challenge_status_id == 2)
    group by challenge_id;
     */

    /*
    category 테이블에서 특정 회원이 참여/진행 중인 습관의 카테고리 조회
    select ca.type
    from category ca
    left outer join habit h on ca.habit_id = h.habit_id
    left outer join challenge c on h.habit_id = c.habit_id
    where user_id == # && challenge_status_id == 1;
     */

    /* 특정 회원이 만든/host인 습관 조회
    select h.habit_id, h.title, h.body, h.category_id, u.username,
    from habit h
    left outer join challenge c
    left outer join users u
    where h.host_id = #;
     */

    /*
    select c.challenge_id, u.username, h.title, c.created_at, c.created_at + 66days
    from challenge c
    left outer join users u
    on c.user_id = u.user_id
    left outer join habit h
    on c.habit_id = h.habit_id
    where c.user_id = # && c.habit_id = #
     */
}
