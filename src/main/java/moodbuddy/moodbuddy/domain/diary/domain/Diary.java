package moodbuddy.moodbuddy.domain.diary.domain;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqPublishDTO;
import moodbuddy.moodbuddy.global.common.base.BaseTimeEntity;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static moodbuddy.moodbuddy.global.common.constants.DiaryConstants.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary")
public class Diary extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "weather", nullable = false)
    private DiaryWeather weather;

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion")
    private DiaryEmotion emotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private DiarySubject subject;

    @Column(name = "summary", columnDefinition = "varchar(255)")
    private String summary;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "book_mark")
    private Boolean bookMark;

    @Enumerated(EnumType.STRING)
    @Column(name = "font")
    private DiaryFont font;

    @Enumerated(EnumType.STRING)
    @Column(name = "font_size")
    private DiaryFontSize fontSize;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_buddy_status")
    private MoodBuddyStatus moodBuddyStatus;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public static Diary of(DiaryReqSaveDTO requestDTO, Long userId) {
        return build(requestDTO.diaryTitle(), requestDTO.diaryDate(), requestDTO.diaryContent(),
                requestDTO.diaryWeather(), requestDTO.diaryFont(), requestDTO.diaryFontSize(),
                extractThumbnail(requestDTO.diaryImageUrls()), userId);
    }

    public static Diary publish(Long userId, DraftDiaryReqPublishDTO requestDTO) {
        return build(requestDTO.diaryTitle(), requestDTO.diaryDate(), requestDTO.diaryContent(),
                requestDTO.diaryWeather(), requestDTO.diaryFont(), requestDTO.diaryFontSize(),
                extractThumbnail(requestDTO.diaryImageUrls()), userId);
    }

    private static String extractThumbnail(List<String> imageUrls) {
        return (imageUrls != null && !imageUrls.isEmpty()) ? imageUrls.getFirst() : null;
    }

    private static Diary build(String title, LocalDate date, String content, DiaryWeather weather,
                               DiaryFont font, DiaryFontSize fontSize, String thumbnail, Long userId) {
        return Diary.builder()
                .title(title)
                .date(date)
                .content(content)
                .weather(weather)
                .emotion(null)
                .summary(null)
                .subject(null)
                .userId(userId)
                .bookMark(false)
                .font(font)
                .fontSize(fontSize)
                .thumbnail(thumbnail)
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public void updateDiary(DiaryReqUpdateDTO requestDTO) {
        this.title = requestDTO.diaryTitle();
        this.content = requestDTO.diaryContent();
        this.weather = requestDTO.diaryWeather();
        this.font = requestDTO.diaryFont();
        this.fontSize = requestDTO.diaryFontSize();
        this.thumbnail = extractThumbnail(requestDTO.diaryImageUrls());
        resetAnalysisFields();
    }

    private void resetAnalysisFields() {
        this.emotion = null;
        this.summary = null;
        this.subject = null;
    }

    public void analyzeDiaryResult(Map<String, String> gptResponse) {
        this.subject = DiarySubject.valueOf(gptResponse.get(DIARY_SUBJECT));
        this.summary = gptResponse.get(DIARY_SUMMARY);
        this.emotion = DiaryEmotion.valueOf(gptResponse.get(DIARY_EMOTION));
    }
    public void updateDiaryBookMarkCheck(Boolean diaryBookMarkCheck) { this.bookMark = diaryBookMarkCheck; }
    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) { this.moodBuddyStatus = moodBuddyStatus; }
}