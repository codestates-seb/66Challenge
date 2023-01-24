package challenge.server.challenge.repository;

import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Challenge.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeCustomRepository {
    Page<Challenge> findAllByStatus(Status status, Pageable pageable);

    Page<Challenge> findAllByUserUserId(Long userId, Pageable pageable);

    Page<Challenge> findAllByUserUserIdAndStatus(Long userId, Challenge.Status status, Pageable pageable);

    // user '마이페이지' 관련 추가
    List<Challenge> findAllByUserUserIdAndStatusOrderByChallengeIdAsc(Long userId, Challenge.Status status);

    List<Challenge> findAllByUserUserIdAndStatusEqualsOrUserUserIdAndStatusEquals(Long userId1, Challenge.Status status1, Long userId2, Challenge.Status status2);

    // user 인증서 발급 관련 추가
    Optional<Challenge> findByUserUserIdAndHabitHabitId(Long userId, Long habidId);
//    List<Challenge> findAllByUserUserIdAndHabitHabitId(Long userId, Long habitId); // 2023.1.24(화) 8h45 인증서 발급 테스트 시 만들어봄
}
