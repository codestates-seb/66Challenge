package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge.server.auth.entity.QAuth.auth;
import static challenge.server.challenge.entity.QChallenge.challenge;
import static challenge.server.challenge.entity.QChallengeStatus.challengeStatus;

@Repository
@RequiredArgsConstructor
public class ChallengeCustomRepositoryImpl implements ChallengeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Challenge> findAllStatus(Long statusId) {
        return jpaQueryFactory
                .select(challenge)
                .from(challengeStatus)
                .where(challengeStatus.challengeStatusId.eq(statusId))
                .leftJoin(challengeStatus.challenges, challenge)
                .fetch();
    }

    @Override
    public List<Auth> findAuthsByChallengeId(Long challengeId) {
        return jpaQueryFactory
                .select(auth)
                .from(challenge)
                .where(challenge.challengeId.eq(challengeId))
                .leftJoin(challenge.auths, auth)
                .on(challenge.challengeId.eq(auth.challenge.challengeId))
                .fetch();
    }
}
