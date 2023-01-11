package challenge.server.challenge.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChallenge is a Querydsl query type for Challenge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChallenge extends EntityPathBase<Challenge> {

    private static final long serialVersionUID = -135909149L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChallenge challenge = new QChallenge("challenge");

    public final challenge.server.audit.QBaseTimeEntity _super = new challenge.server.audit.QBaseTimeEntity(this);

    public final ListPath<challenge.server.auth.entity.Auth, challenge.server.auth.entity.QAuth> auths = this.<challenge.server.auth.entity.Auth, challenge.server.auth.entity.QAuth>createList("auths", challenge.server.auth.entity.Auth.class, challenge.server.auth.entity.QAuth.class, PathInits.DIRECT2);

    public final NumberPath<Long> challengeId = createNumber("challengeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final challenge.server.habit.entity.QHabit habit;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final challenge.server.review.entity.QReview review;

    public final QChallengeStatus status;

    public final challenge.server.user.entity.QUser user;

    public final ListPath<Wildcard, QWildcard> wildcards = this.<Wildcard, QWildcard>createList("wildcards", Wildcard.class, QWildcard.class, PathInits.DIRECT2);

    public QChallenge(String variable) {
        this(Challenge.class, forVariable(variable), INITS);
    }

    public QChallenge(Path<? extends Challenge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChallenge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChallenge(PathMetadata metadata, PathInits inits) {
        this(Challenge.class, metadata, inits);
    }

    public QChallenge(Class<? extends Challenge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.habit = inits.isInitialized("habit") ? new challenge.server.habit.entity.QHabit(forProperty("habit"), inits.get("habit")) : null;
        this.review = inits.isInitialized("review") ? new challenge.server.review.entity.QReview(forProperty("review"), inits.get("review")) : null;
        this.status = inits.isInitialized("status") ? new QChallengeStatus(forProperty("status")) : null;
        this.user = inits.isInitialized("user") ? new challenge.server.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

