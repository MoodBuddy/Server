package moodbuddy.moodbuddy.domain.letter.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLetter is a Querydsl query type for Letter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLetter extends EntityPathBase<Letter> {

    private static final long serialVersionUID = -1754050540L;

    public static final QLetter letter = new QLetter("letter");

    public final moodbuddy.moodbuddy.global.common.base.QBaseEntity _super = new moodbuddy.moodbuddy.global.common.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath letterAnswerContent = createString("letterAnswerContent");

    public final DateTimePath<java.time.LocalDateTime> letterDate = createDateTime("letterDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> letterFormat = createNumber("letterFormat", Integer.class);

    public final StringPath letterWorryContent = createString("letterWorryContent");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QLetter(String variable) {
        super(Letter.class, forVariable(variable));
    }

    public QLetter(Path<? extends Letter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLetter(PathMetadata metadata) {
        super(Letter.class, metadata);
    }

}

