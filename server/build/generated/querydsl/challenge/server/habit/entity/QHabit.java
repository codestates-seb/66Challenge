package challenge.server.habit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHabit is a Querydsl query type for Habit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHabit extends EntityPathBase<Habit> {

    private static final long serialVersionUID = 375542915L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHabit habit = new QHabit("habit");

    public final challenge.server.audit.QBaseTimeEntity _super = new challenge.server.audit.QBaseTimeEntity(this);

    public final DateTimePath<java.time.LocalDateTime> authEndTime = createDateTime("authEndTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> authStartTime = createDateTime("authStartTime", java.time.LocalDateTime.class);

    public final StringPath body = createString("body");

    public final ListPath<challenge.server.bookmark.entity.Bookmark, challenge.server.bookmark.entity.QBookmark> bookmarks = this.<challenge.server.bookmark.entity.Bookmark, challenge.server.bookmark.entity.QBookmark>createList("bookmarks", challenge.server.bookmark.entity.Bookmark.class, challenge.server.bookmark.entity.QBookmark.class, PathInits.DIRECT2);

    public final QCategory category;

    public final ListPath<challenge.server.challenge.entity.Challenge, challenge.server.challenge.entity.QChallenge> challenges = this.<challenge.server.challenge.entity.Challenge, challenge.server.challenge.entity.QChallenge>createList("challenges", challenge.server.challenge.entity.Challenge.class, challenge.server.challenge.entity.QChallenge.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> habitId = createNumber("habitId", Long.class);

    public final challenge.server.user.entity.QUser host;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final ListPath<challenge.server.report.entity.Report, challenge.server.report.entity.QReport> reports = this.<challenge.server.report.entity.Report, challenge.server.report.entity.QReport>createList("reports", challenge.server.report.entity.Report.class, challenge.server.report.entity.QReport.class, PathInits.DIRECT2);

    public final StringPath subTitle = createString("subTitle");

    public final StringPath title = createString("title");

    public QHabit(String variable) {
        this(Habit.class, forVariable(variable), INITS);
    }

    public QHabit(Path<? extends Habit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHabit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHabit(PathMetadata metadata, PathInits inits) {
        this(Habit.class, metadata, inits);
    }

    public QHabit(Class<? extends Habit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.host = inits.isInitialized("host") ? new challenge.server.user.entity.QUser(forProperty("host"), inits.get("host")) : null;
    }

}

