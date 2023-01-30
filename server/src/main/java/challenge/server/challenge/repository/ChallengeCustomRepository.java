package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import com.querydsl.core.Tuple;

import java.time.LocalDateTime;
import java.util.List;

public interface ChallengeCustomRepository {
    List<Challenge> findAllByStatus(Long lastChaallengeId, Challenge.Status status, int size);

    List<Challenge> findAllByUserUserId(Long lastChaallengeId, Long userId, int size);

    List<Challenge> findAllByUserUserIdAndStatus(Long lastChaallengeId, Long userId, Challenge.Status status, int size);
    List<Auth> findAuthsByChallengeId(Long challengeId);
    List<Challenge> findAllByNotAuthToday(Challenge.Status status, LocalDateTime startDatetime, LocalDateTime endDatetime);
    Integer findChallengers(Long habitId);

    List<Challenge> findAllNoOffset(Long lastChallengeId, int size);
}
