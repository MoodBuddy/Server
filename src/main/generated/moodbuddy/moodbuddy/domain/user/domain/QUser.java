package moodbuddy.moodbuddy.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1833284002L;

    public static final QUser user = new QUser("user");

    public final moodbuddy.moodbuddy.global.common.base.QBaseEntity _super = new moodbuddy.moodbuddy.global.common.base.QBaseEntity(this);

    public final BooleanPath alarm = createBoolean("alarm");

    public final StringPath alarmTime = createString("alarmTime");

    public final StringPath birthday = createString("birthday");

    public final BooleanPath checkTodayDairy = createBoolean("checkTodayDairy");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final BooleanPath deleted = createBoolean("deleted");

    public final BooleanPath gender = createBoolean("gender");

    public final NumberPath<Long> kakaoId = createNumber("kakaoId", Long.class);

    public final BooleanPath letterAlarm = createBoolean("letterAlarm");

    public final StringPath nickname = createString("nickname");

    public final StringPath phoneNumber = createString("phoneNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final NumberPath<Integer> userCurDiaryNums = createNumber("userCurDiaryNums", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Integer> userLastDiaryNums = createNumber("userLastDiaryNums", Integer.class);

    public final NumberPath<Integer> userLetterNums = createNumber("userLetterNums", Integer.class);

    public final StringPath userRole = createString("userRole");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

