package challenge.server.domain.auth.repository;

import challenge.server.domain.auth.entity.Auth;

import java.util.List;

public interface AuthCustomRepository {
    List<Auth> findAllByChallengeChallengeId(Long lastId, Long challengeId, int size);

    List<Auth> findAllByChallengeHabitHabitId(Long lastId, Long habitId, int size);
}
