package moodbuddy.moodbuddy.domain.bookMark.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookMark is a Querydsl query type for BookMark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookMark extends EntityPathBase<BookMark> {

    private static final long serialVersionUID = -38585356L;

    public static final QBookMark bookMark = new QBookMark("bookMark");

    public final moodbuddy.moodbuddy.global.common.base.QBaseEntity _super = new moodbuddy.moodbuddy.global.common.base.QBaseEntity(this);

    public final NumberPath<Long> bookMarkId = createNumber("bookMarkId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> diaryId = createNumber("diaryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBookMark(String variable) {
        super(BookMark.class, forVariable(variable));
    }

    public QBookMark(Path<? extends BookMark> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookMark(PathMetadata metadata) {
        super(BookMark.class, metadata);
    }

}

