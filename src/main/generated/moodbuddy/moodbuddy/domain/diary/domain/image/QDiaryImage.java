package moodbuddy.moodbuddy.domain.diary.domain.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiaryImage is a Querydsl query type for DiaryImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiaryImage extends EntityPathBase<DiaryImage> {

    private static final long serialVersionUID = 1932983038L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiaryImage diaryImage = new QDiaryImage("diaryImage");

    public final moodbuddy.moodbuddy.global.common.base.QBaseEntity _super = new moodbuddy.moodbuddy.global.common.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final moodbuddy.moodbuddy.domain.diary.domain.QDiary diary;

    public final StringPath diaryImgFileName = createString("diaryImgFileName");

    public final NumberPath<Double> diaryImgHeight = createNumber("diaryImgHeight", Double.class);

    public final StringPath diaryImgThumbFileName = createString("diaryImgThumbFileName");

    public final NumberPath<Double> diaryImgThumbHeight = createNumber("diaryImgThumbHeight", Double.class);

    public final StringPath diaryImgThumbURL = createString("diaryImgThumbURL");

    public final NumberPath<Double> diaryImgThumbWidth = createNumber("diaryImgThumbWidth", Double.class);

    public final StringPath diaryImgURL = createString("diaryImgURL");

    public final NumberPath<Double> diaryImgWidth = createNumber("diaryImgWidth", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus> moodBuddyStatus = createEnum("moodBuddyStatus", moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QDiaryImage(String variable) {
        this(DiaryImage.class, forVariable(variable), INITS);
    }

    public QDiaryImage(Path<? extends DiaryImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiaryImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiaryImage(PathMetadata metadata, PathInits inits) {
        this(DiaryImage.class, metadata, inits);
    }

    public QDiaryImage(Class<? extends DiaryImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.diary = inits.isInitialized("diary") ? new moodbuddy.moodbuddy.domain.diary.domain.QDiary(forProperty("diary")) : null;
    }

}

