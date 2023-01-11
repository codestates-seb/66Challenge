package challenge.server.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -1660869307L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final challenge.server.audit.QBaseTimeEntity _super = new challenge.server.audit.QBaseTimeEntity(this);

    public final challenge.server.auth.entity.QAuth auth;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final challenge.server.habit.entity.QHabit habit;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final challenge.server.user.entity.QUser reporter;

    public final NumberPath<Long> reportId = createNumber("reportId", Long.class);

    public final challenge.server.review.entity.QReview review;

    public final EnumPath<Report.Type> type = createEnum("type", Report.Type.class);

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auth = inits.isInitialized("auth") ? new challenge.server.auth.entity.QAuth(forProperty("auth"), inits.get("auth")) : null;
        this.habit = inits.isInitialized("habit") ? new challenge.server.habit.entity.QHabit(forProperty("habit"), inits.get("habit")) : null;
        this.reporter = inits.isInitialized("reporter") ? new challenge.server.user.entity.QUser(forProperty("reporter"), inits.get("reporter")) : null;
        this.review = inits.isInitialized("review") ? new challenge.server.review.entity.QReview(forProperty("review"), inits.get("review")) : null;
    }

}

