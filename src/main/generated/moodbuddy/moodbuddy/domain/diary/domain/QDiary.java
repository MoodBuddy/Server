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

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final BooleanPath diaryBookMarkCheck = createBoolean("diaryBookMarkCheck");

    public final StringPath diaryContent = createString("diaryContent");

    public final DatePath<java.time.LocalDate> diaryDate = createDate("diaryDate", java.time.LocalDate.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion> diaryEmotion = createEnum("diaryEmotion", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFont> diaryFont = createEnum("diaryFont", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFont.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFontSize> diaryFontSize = createEnum("diaryFontSize", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryFontSize.class);

    public final NumberPath<Long> diaryId = createNumber("diaryId", Long.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus> diaryStatus = createEnum("diaryStatus", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryStatus.class);

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject> diarySubject = createEnum("diarySubject", moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject.class);

    public final StringPath diarySummary = createString("diarySummary");

    public final StringPath diaryTitle = createString("diaryTitle");

    public final EnumPath<moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather> diaryWeather = createEnum("diaryWeather", moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather.class);

    public final EnumPath<moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus> moodBuddyStatus = createEnum("moodBuddyStatus", moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

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

