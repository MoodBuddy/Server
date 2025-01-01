package moodbuddy.moodbuddy.domain.diary.domain.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDiaryImage is a Querydsl query type for DiaryImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiaryImage extends EntityPathBase<DiaryImage> {

    private static final long serialVersionUID = 1932983038L;

    public static final QDiaryImage diaryImage = new QDiaryImage("diaryImage");

    public final moodbuddy.moodbuddy.global.common.base.QBaseEntity _super = new moodbuddy.moodbuddy.global.common.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> diaryId = createNumber("diaryId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final EnumPath<moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus> moodBuddyStatus = createEnum("moodBuddyStatus", moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QDiaryImage(String variable) {
        super(DiaryImage.class, forVariable(variable));
    }

    public QDiaryImage(Path<? extends DiaryImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDiaryImage(PathMetadata metadata) {
        super(DiaryImage.class, metadata);
    }

}

