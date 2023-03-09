package challenge.server.domain.challenge.repository;

import challenge.server.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeCustomRepository {
    // user '마이페이지' 관련 추가
    List<Challenge> findAllByUserUserIdAndStatusOrderByChallengeIdAsc(Long userId, Challenge.Status status);

    List<Challenge> findAllByUserUserIdAndStatusEqualsOrUserUserIdAndStatusEquals(Long userId1, Challenge.Status status1, Long userId2, Challenge.Status status2);

    // user 인증서 발급 관련 추가
    Optional<Challenge> findByUserUserIdAndHabitHabitId(Long userId, Long habidId);
//    List<Challenge> findAllByUserUserIdAndHabitHabitId(Long userId, Long habitId); // 2023.1.24(화) 8h45 인증서 발급 테스트 시 만들어봄
}
