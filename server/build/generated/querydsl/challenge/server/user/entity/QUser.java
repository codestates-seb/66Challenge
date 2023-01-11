package challenge.server.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -654923725L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final challenge.server.audit.QBaseTimeEntity _super = new challenge.server.audit.QBaseTimeEntity(this);

    public final ListPath<challenge.server.bookmark.entity.Bookmark, challenge.server.bookmark.entity.QBookmark> bookmarks = this.<challenge.server.bookmark.entity.Bookmark, challenge.server.bookmark.entity.QBookmark>createList("bookmarks", challenge.server.bookmark.entity.Bookmark.class, challenge.server.bookmark.entity.QBookmark.class, PathInits.DIRECT2);

    public final ListPath<challenge.server.challenge.entity.Challenge, challenge.server.challenge.entity.QChallenge> challenges = this.<challenge.server.challenge.entity.Challenge, challenge.server.challenge.entity.QChallenge>createList("challenges", challenge.server.challenge.entity.Challenge.class, challenge.server.challenge.entity.QChallenge.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final ListPath<challenge.server.habit.entity.Habit, challenge.server.habit.entity.QHabit> habits = this.<challenge.server.habit.entity.Habit, challenge.server.habit.entity.QHabit>createList("habits", challenge.server.habit.entity.Habit.class, challenge.server.habit.entity.QHabit.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final StringPath password = createString("password");

    public final ListPath<challenge.server.report.entity.Report, challenge.server.report.entity.QReport> reports = this.<challenge.server.report.entity.Report, challenge.server.report.entity.QReport>createList("reports", challenge.server.report.entity.Report.class, challenge.server.report.entity.QReport.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public final QUserStatus userStatus;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userStatus = inits.isInitialized("userStatus") ? new QUserStatus(forProperty("userStatus")) : null;
    }

}

