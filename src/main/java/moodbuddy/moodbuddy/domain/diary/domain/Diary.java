package moodbuddy.moodbuddy.domain.diary.domain;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary")
public class Diary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long diaryId;

    @Column(name = "diary_title", nullable = false)
    private String diaryTitle;

    @Column(name = "diary_date", nullable = false)
    private LocalDate diaryDate;

    @Column(name = "diary_content", nullable = false, columnDefinition = "text")
    private String diaryContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "diary_weather", nullable = false)
    private DiaryWeather diaryWeather;

    @Enumerated(EnumType.STRING)
    @Column(name = "diary_emotion")
    private DiaryEmotion diaryEmotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "diary_status", nullable = false)
    private DiaryStatus diaryStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "diary_subject")
    private DiarySubject diarySubject;

    @Column(name = "diary_summary", columnDefinition = "varchar(255)")
    private String diarySummary;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "diary_book_mark_check")
    private Boolean diaryBookMarkCheck;

    @Enumerated(EnumType.STRING)
    @Column(name = "diary_font")
    private DiaryFont diaryFont;

    @Enumerated(EnumType.STRING)
    @Column(name = "diary_font_size")
    private DiaryFontSize diaryFontSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_buddy_status")
    private MoodBuddyStatus moodBuddyStatus;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    public static Diary ofPublished(DiaryReqSaveDTO requestDTO, Long userId, String diarySummary, DiarySubject diarySubject) {
        return Diary.builder()
                .diaryTitle(requestDTO.diaryTitle())
                .diaryDate(requestDTO.diaryDate())
                .diaryContent(requestDTO.diaryContent())
                .diaryWeather(requestDTO.diaryWeather())
                .diaryStatus(DiaryStatus.PUBLISHED)
                .diarySummary(diarySummary)
                .diarySubject(diarySubject)
                .userId(userId)
                .diaryBookMarkCheck(false)
                .diaryFont(requestDTO.diaryFont())
                .diaryFontSize(requestDTO.diaryFontSize())
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public static Diary ofDraft(DiaryReqSaveDTO requestDTO, Long userId) {
        return Diary.builder()
                .diaryTitle(requestDTO.diaryTitle())
                .diaryDate(requestDTO.diaryDate())
                .diaryContent(requestDTO.diaryContent())
                .diaryWeather(requestDTO.diaryWeather())
                .diaryStatus(DiaryStatus.DRAFT)
                .userId(userId)
                .diaryBookMarkCheck(false)
                .diaryFont(requestDTO.diaryFont())
                .diaryFontSize(requestDTO.diaryFontSize())
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public void updateDiary(DiaryReqUpdateDTO requestDTO, Map<String, String> gptResults) {
        this.diaryTitle = requestDTO.diaryTitle();
        this.diaryDate = requestDTO.diaryDate();
        this.diaryContent = requestDTO.diaryContent();
        this.diaryWeather = requestDTO.diaryWeather();
        this.diarySummary = gptResults.get("summary");
        this.diaryStatus = DiaryStatus.PUBLISHED;
        this.diarySubject = DiarySubject.valueOf(gptResults.get("subject"));
        this.diaryFont = requestDTO.diaryFont();
        this.diaryFontSize = requestDTO.diaryFontSize();
    }

    public void updateDiaryEmotion(DiaryEmotion diaryEmotion) {
        this.diaryEmotion = diaryEmotion;
    }
    public void updateDiaryBookMarkCheck(Boolean diaryBookMarkCheck) { this.diaryBookMarkCheck = diaryBookMarkCheck; }
    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) { this.moodBuddyStatus = moodBuddyStatus; }
}