package challenge.server.challenge.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChallengeStatusCustomRepository {
    List<Long> findChallengesByStatus(Long statusId, Pageable pageable);
}
