package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;

import java.time.LocalDateTime;
import java.util.List;

public interface ChallengeCustomRepository {
    List<Auth> findAuthsByChallengeId(Long challengeId);
    List<Challenge> findAllByNotAuthToday(Challenge.Status status, LocalDateTime startDatetime, LocalDateTime endDatetime);
    Integer findChallengers(Long habitId);
}
