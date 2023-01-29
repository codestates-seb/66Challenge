package challenge.server.auth.repository;

import challenge.server.auth.entity.Auth;

import java.util.List;

public interface AuthCustomRepository {
    List<Auth> findAllByChallengeChallengeId(Long lastId, Long challengeId, int page, int size);

    List<Auth> findAllByChallengeHabitHabitId(Long lastId, Long habitId, int page, int size);
    List<Auth> findAllByChallengeHabitHabitId(Long lastId, Long habitId, int size);

}
