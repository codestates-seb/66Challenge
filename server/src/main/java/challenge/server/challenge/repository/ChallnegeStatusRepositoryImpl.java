package challenge.server.challenge.repository;

import challenge.server.challenge.entity.QChallenge;
import challenge.server.challenge.entity.QChallengeStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChallnegeStatusRepositoryImpl implements ChallengeStatusCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findChallengesByStatus(Long statusId, Pageable pageable) {
        QChallengeStatus cs = QChallengeStatus.challengeStatus;
        QChallenge c = QChallenge.challenge;
//        List<Challenge> findChallengesByStatus = queryFactory
//                .select(cs.challenges)
//                .from(cs)
//                .where(cs.challengeStatusId.eq(statusId))
//                .offset(pageable.getOffset())   // 페이지 번호
//                .limit(pageable.getPageSize())  // 페이지 사이즈
//                .fetchOne();
//
//        return new PageImpl<>(findChallengesByStatus, pageable, () -> countQuery.fetchCount());

        List<Long> findChallengesByStatus = queryFactory
                .select(c.challengeId)
                .from(c).fetch();

        return findChallengesByStatus;
    }

}
