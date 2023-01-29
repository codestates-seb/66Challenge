//package challenge.server.user.repository;
//
//import challenge.server.challenge.repository.ChallengeRepository;
////import challenge.server.user.dto.QUserDto_ChallengeDetailsDb;
////import challenge.server.user.dto.QUserDto_UserDetailsDb;
//import challenge.server.user.dto.UserDto;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static challenge.server.category.entity.QCategory.category;
//import static challenge.server.challenge.entity.Challenge.Status.CHALLENGE;
//import static challenge.server.challenge.entity.Challenge.Status.SUCCESS;
//import static challenge.server.habit.entity.QHabit.habit;
//import static challenge.server.user.entity.QUser.user;
//import static challenge.server.challenge.entity.QChallenge.challenge;
//
//@Repository
//@RequiredArgsConstructor
//public class UserCustomRepositoryImpl implements UserCustomRepository {
//    private final JPAQueryFactory jpaQueryFactory;
//    private final ChallengeRepository challengeRepository;
//
//    @Override
//    public UserDto.UserDetailsDb findUserDetails(Long userId) {
//        return jpaQueryFactory
//                .select(new QUserDto_UserDetailsDb(user.userId, user.email, user.username,
//                        JPAExpressions.select(challenge.createdAt.min())
//                                .from(challenge)
//                                .where(user.userId.eq(userId).and(challenge.status.eq(CHALLENGE)))))
//                .from(user)
//                .join(user.challenges, challenge)
//                .where(user.user.userId.eq(userId))
//                .fetchOne();
//    }
//
//
//
//    @Override
//    public List<UserDto.ChallengeDetailsDb> findChallengeDetails(Long userId) {
//        return jpaQueryFactory
//                .select(new QUserDto_ChallengeDetailsDb(challenge.challengeId, challenge.createdAt,
//                        habit.habitId, habit.subTitle,
//                        category.categoryId, category.type))
//                .from(category)
//                .join(category.habits, habit)
//                .join(habit.challenges, challenge)
//                .where(user.userId.eq(userId).and(challenge.status.eq(CHALLENGE).or(challenge.status.eq(SUCCESS))))
//                .fetch();
//    }
//}
