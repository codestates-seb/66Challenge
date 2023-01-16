package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;

import java.util.List;

public interface ChallengeCustomRepository {
    List<Auth> findAuthsByChallengeId(Long challengeId);
}
