package moodbuddy.moodbuddy.domain.diary.domain;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.domain.diary.dto.request.save.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.draftDiary.domain.DraftDiary;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqPublishDTO;
import moodbuddy.moodbuddy.global.common.base.BaseTimeEntity;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;

import java.time.LocalDate;
import java.util.Map;

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

    @Column(name = "thumbnail", columnDefinition = "varchar(255)")
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_buddy_status")
    private MoodBuddyStatus moodBuddyStatus;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    public static Diary of(DiaryReqSaveDTO requestDTO, Long userId) {
        return Diary.builder()
                .title(requestDTO.diaryTitle())
                .date(requestDTO.diaryDate())
                .content(requestDTO.diaryContent())
                .weather(requestDTO.diaryWeather())
                .emotion(null)
                .summary(null)
                .subject(null)
                .userId(userId)
                .bookMark(false)
                .font(requestDTO.diaryFont())
                .fontSize(requestDTO.diaryFontSize())
                .thumbnail(
                        (requestDTO.diaryImageUrls() != null && !requestDTO.diaryImageUrls().isEmpty())
                                ? requestDTO.diaryImageUrls().getFirst()
                                : null
                )
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public static Diary publish(Long userId, DraftDiaryReqPublishDTO requestDTO) {
        return Diary.builder()
                .title(requestDTO.diaryTitle())
                .date(requestDTO.diaryDate())
                .content(requestDTO.diaryContent())
                .weather(requestDTO.diaryWeather())
                .emotion(null)
                .summary(null)
                .subject(null)
                .userId(userId)
                .bookMark(false)
                .font(requestDTO.diaryFont())
                .fontSize(requestDTO.diaryFontSize())
                .thumbnail(
                        (requestDTO.diaryImageUrls() != null && !requestDTO.diaryImageUrls().isEmpty())
                                ? requestDTO.diaryImageUrls().getFirst()
                                : null
                )
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public void updateDiary(DiaryReqUpdateDTO requestDTO) {
        this.title = requestDTO.diaryTitle();
        this.content = requestDTO.diaryContent();
        this.weather = requestDTO.diaryWeather();
        this.emotion = null;
        this.summary = null;
        this.subject = null;
        this.font = requestDTO.diaryFont();
        this.fontSize = requestDTO.diaryFontSize();
        this.thumbnail =
                (requestDTO.diaryImageUrls() != null && !requestDTO.diaryImageUrls().isEmpty())
                        ? requestDTO.diaryImageUrls().getFirst()
                        : null;
    }

    public void analyzeDiaryResult(Map<String, String> gptResponse) {
        this.subject = DiarySubject.valueOf(gptResponse.get("subject"));
        this.summary = gptResponse.get("summary");
        this.emotion = DiaryEmotion.valueOf(gptResponse.get("emotion"));
    }
    public void updateDiaryBookMarkCheck(Boolean diaryBookMarkCheck) { this.bookMark = diaryBookMarkCheck; }
    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) { this.moodBuddyStatus = moodBuddyStatus; }
}