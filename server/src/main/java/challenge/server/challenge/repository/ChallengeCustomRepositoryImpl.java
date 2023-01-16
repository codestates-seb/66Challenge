package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static challenge.server.auth.entity.QAuth.auth;
import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
import static challenge.server.challenge.entity.QChallenge.challenge;

@Repository
@RequiredArgsConstructor
public class ChallengeCustomRepositoryImpl implements ChallengeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

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

    @Override
    public List<Challenge> findAllByNotAuthToday(Challenge.Status status, LocalDateTime startDatetime, LocalDateTime endDatetime) {

//        return jpaQueryFactory
//                .selectFrom(challenge)  // Challenge에서
//                .where(challenge.status.eq(CHALLENGE)) // 도전중인 Challenge만
//                .leftJoin(challenge.auths, auth)    // auth와 leftjoin
//                .on(challenge.challengeId.eq(auth.challenge.challengeId))   // challengeId 기준으로
//                .groupBy(challenge.challengeId, auth.authId) //  challengeId를 기준으로 group 만듦
//                .having((auth.createdAt.max().notBetween(startDatetime, endDatetime)).or(auth.isNull()))    // 가장 최근의 auth 생성일이 startDatetime과 endDatetime 사이에 포함되지 않은 경우만
//                .fetch();

        return jpaQueryFactory
                .selectFrom(challenge)  // Challenge에서
                .leftJoin(challenge.auths, auth)    // auth와 leftjoin
                .on(challenge.challengeId.eq(auth.challenge.challengeId))   // challengeId 기준으로
                .where(challenge.status.eq(CHALLENGE)) // 도전중인 Challenge만
                .groupBy(challenge.challengeId, auth.createdAt) //  challengeId를 기준으로 group 만듦
                .having((auth.createdAt.max().notBetween(startDatetime, endDatetime)).or(auth.isNull()))    // 가장 최근의 auth 생성일이 startDatetime과 endDatetime 사이에 포함되지 않은 경우만
                .fetch();
    }
}
