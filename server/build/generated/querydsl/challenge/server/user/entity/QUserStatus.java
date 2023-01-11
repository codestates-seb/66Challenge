package challenge.server.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserStatus is a Querydsl query type for UserStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserStatus extends EntityPathBase<UserStatus> {

    private static final long serialVersionUID = -43437563L;

    public static final QUserStatus userStatus = new QUserStatus("userStatus");

    public final challenge.server.audit.QBaseTimeEntity _super = new challenge.server.audit.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final EnumPath<UserStatus.Type> type = createEnum("type", UserStatus.Type.class);

    public final ListPath<User, QUser> users = this.<User, QUser>createList("users", User.class, QUser.class, PathInits.DIRECT2);

    public final NumberPath<Long> userStatusId = createNumber("userStatusId", Long.class);

    public QUserStatus(String variable) {
        super(UserStatus.class, forVariable(variable));
    }

    public QUserStatus(Path<? extends UserStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserStatus(PathMetadata metadata) {
        super(UserStatus.class, metadata);
    }

}

