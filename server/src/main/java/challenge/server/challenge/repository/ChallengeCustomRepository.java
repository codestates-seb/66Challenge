package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import challenge.server.user.dto.UserDto;
import com.querydsl.core.Tuple;

import java.time.LocalDateTime;
import java.util.List;

public interface ChallengeCustomRepository {
    List<Challenge> findAllByStatus(Long lastChaallengeId, Challenge.Status status, int size);

    List<Challenge> findAllByUserUserId(Long lastChaallengeId, Long userId, int size);

    List<Challenge> findAllByUserUserIdAndStatus(Long lastChaallengeId, Long userId, Challenge.Status status, int size);
    List<Auth> findAuthsByChallengeId(Long challengeId);
    List<Challenge> findAllByNotAuthToday(Challenge.Status status, LocalDateTime startDatetime, LocalDateTime endDatetime);

    // 특정 습관의 챌린저들,
    Integer findChallengers(Long habitId);
    Integer findAllChallengers(Long habitId);

    List<Challenge> findAllNoOffset(Long lastChallengeId, int size);

    List<UserDto.CategoriesResponse> findFavoriteCategories(Long userId);
}
