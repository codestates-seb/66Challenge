package challenge.server.user.repository;

import challenge.server.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static challenge.server.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QUserRepository  {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    // TODO 이름 순 + no offset
    public List<User> findAllUsers(Long lastUserId, int size) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(gtUserId(lastUserId))
                .orderBy(user.userId.asc())
                .limit(size)
                .fetch();
    }

    public List<User> findUsersByKeyword(Long lastUserId, int size, String keyword) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.username.contains(keyword),
                        gtUserId(lastUserId))
                .orderBy(user.userId.asc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression gtUserId(Long lastUserId) {
        return lastUserId==null ? null : user.userId.gt(lastUserId);
    }
}