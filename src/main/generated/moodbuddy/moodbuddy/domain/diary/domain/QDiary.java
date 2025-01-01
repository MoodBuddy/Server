package moodbuddy.moodbuddy.domain.diary.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDiary is a Querydsl query type for Diary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiary extends EntityPathBase<Diary> {

    private static final long serialVersionUID = -2103895952L;

    public static final QDiary diary = new QDiary("diary");

    public final moodbuddy.moodbuddy.global.common.base.QBaseEntity _super = new moodbuddy.moodbuddy.global.common.base.QBaseEntity(this);

    public final BooleanPath bookMark = createBoolean("bookMark");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion> emotion = createEnum("emotion", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFont> font = createEnum("font", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFont.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFontSize> fontSize = createEnum("fontSize", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFontSize.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus> moodBuddyStatus = createEnum("moodBuddyStatus", moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus> status = createEnum("status", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject> subject = createEnum("subject", moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject.class);

    public final StringPath summary = createString("summary");

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather> weather = createEnum("weather", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather.class);

    public QDiary(String variable) {
        super(Diary.class, forVariable(variable));
    }

    public QDiary(Path<? extends Diary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDiary(PathMetadata metadata) {
        super(Diary.class, metadata);
    }

}

