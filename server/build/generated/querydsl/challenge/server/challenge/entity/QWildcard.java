package challenge.server.challenge.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWildcard is a Querydsl query type for Wildcard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWildcard extends EntityPathBase<Wildcard> {

    private static final long serialVersionUID = 994111834L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWildcard wildcard = new QWildcard("wildcard");

    public final challenge.server.audit.QBaseTimeEntity _super = new challenge.server.audit.QBaseTimeEntity(this);

    public final QChallenge challenge;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> wildcardId = createNumber("wildcardId", Long.class);

    public QWildcard(String variable) {
        this(Wildcard.class, forVariable(variable), INITS);
    }

    public QWildcard(Path<? extends Wildcard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWildcard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWildcard(PathMetadata metadata, PathInits inits) {
        this(Wildcard.class, metadata, inits);
    }

    public QWildcard(Class<? extends Wildcard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.challenge = inits.isInitialized("challenge") ? new QChallenge(forProperty("challenge"), inits.get("challenge")) : null;
    }

}

