package challenge.server.challenge.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChallengeStatus is a Querydsl query type for ChallengeStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChallengeStatus extends EntityPathBase<ChallengeStatus> {

    private static final long serialVersionUID = -1696893259L;

    public static final QChallengeStatus challengeStatus = new QChallengeStatus("challengeStatus");

    public final challenge.server.audit.QBaseTimeEntity _super = new challenge.server.audit.QBaseTimeEntity(this);

    public final ListPath<Challenge, QChallenge> challenges = this.<Challenge, QChallenge>createList("challenges", Challenge.class, QChallenge.class, PathInits.DIRECT2);

    public final NumberPath<Long> challengeStatusId = createNumber("challengeStatusId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final EnumPath<ChallengeStatus.Type> status = createEnum("status", ChallengeStatus.Type.class);

    public QChallengeStatus(String variable) {
        super(ChallengeStatus.class, forVariable(variable));
    }

    public QChallengeStatus(Path<? extends ChallengeStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChallengeStatus(PathMetadata metadata) {
        super(ChallengeStatus.class, metadata);
    }

}

