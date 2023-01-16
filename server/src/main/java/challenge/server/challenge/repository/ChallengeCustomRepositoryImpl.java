package challenge.server.challenge.repository;

import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
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

        return jpaQueryFactory
                .selectFrom(challenge)
                .where(
                        challenge.status.eq(CHALLENGE),
                        challenge.lastPostedAt.notBetween(startDatetime, endDatetime)
                        .or(challenge.lastPostedAt.isNull()))
                .fetch();


        // TODO: 추후 쿼리만으로 구현 가능하도록 도전
        /**
         * 1. 첼린지를 반환하고 첼린지 안에는 auth 테이블이 존재 (찬빈님 멋쟁이 이병헌 송중기) 원빈, 현빈, 김우빈, 찬빈 Let's go F4
         * 2. 조건 1) 도전중인 첼린지로 추려져야 한다.
         * 3. 조건 2) 각 첼린지에서 가장 최신인 인증 게시물(날짜와 비교가 필요함)
         * 4. 결과 : 도전중인 첼린지만 반환
         */
//        return jpaQueryFactory
//                .selectFrom(challenge)  // Challenge에서
//                .leftJoin(challenge.auths, auth)    // auth와 leftjoin
//                .on(challenge.challengeId.eq(auth.challenge.challengeId))   // challengeId 기준으로
//                .where(challenge.status.eq(CHALLENGE)) // 도전중인 Challenge만
//                .groupBy(challenge.challengeId, auth.createdAt) //  challengeId를 기준으로 group 만듦
//                .having((auth.createdAt.max().lt(LocalDate.now().atStartOfDay())).or(auth.isNull()))    // 가장 최근의 auth 생성일이 startDatetime과 endDatetime 사이에 포함되지 않은 경우만
//                .fetch();

//        // distinct() - 중복 제거
//        // 1. Auth 날짜별로 오름차순으로 데이터를 정렬
//        // 2. challenge의 id or name 같은걸로 distinct()
//        // challenge 1에 10시 40분 auth 1 10시 39분 auth 1 distinct()
//        List<Challenge> result = queryFactory
//                .select(challenge)
//                .from(challenge)
//                .leftJoin(challenge.auths, auth)
//                .where(
//                        conditionEq(status),
//                        conditionEq2(startDatetime, endDatetime)
//                ).fetch();
    }
}
