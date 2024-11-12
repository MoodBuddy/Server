package moodbuddy.moodbuddy.domain.diary.domain;

import jakarta.persistence.*;
import lombok.*;
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
    @Column(name = "id")
    private Long id;

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

    public void updateDiary(DiaryReqUpdateDTO diaryReqUpdateDTO, Map<String, String> gptResults) {
        this.diaryTitle = diaryReqUpdateDTO.diaryTitle();
        this.diaryDate = diaryReqUpdateDTO.diaryDate();
        this.diaryContent = diaryReqUpdateDTO.diaryContent();
        this.diaryWeather = diaryReqUpdateDTO.diaryWeather();
        this.diarySummary = gptResults.get("summary");
        this.diaryStatus = DiaryStatus.PUBLISHED;
        this.diarySubject = DiarySubject.valueOf(gptResults.get("subject"));
        this.diaryFont = diaryReqUpdateDTO.diaryFont();
        this.diaryFontSize = diaryReqUpdateDTO.diaryFontSize();
        this.moodBuddyStatus = MoodBuddyStatus.valueOf(gptResults.get("status"));
    }

    public void updateDiaryEmotion(DiaryEmotion diaryEmotion) {
        this.diaryEmotion = diaryEmotion;
    }
    public void updateDiaryBookMarkCheck(Boolean diaryBookMarkCheck) { this.diaryBookMarkCheck = diaryBookMarkCheck; }
    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) { this.moodBuddyStatus = moodBuddyStatus; }

    public static Diary ofPublished(DiaryReqSaveDTO diaryReqSaveDTO, Long userId, String diarySummary, DiarySubject diarySubject) {
        return Diary.builder()
                .diaryTitle(diaryReqSaveDTO.diaryTitle())
                .diaryDate(diaryReqSaveDTO.diaryDate())
                .diaryContent(diaryReqSaveDTO.diaryContent())
                .diaryWeather(diaryReqSaveDTO.diaryWeather())
                .diaryStatus(DiaryStatus.PUBLISHED)
                .diarySummary(diarySummary)
                .diarySubject(diarySubject)
                .userId(userId)
                .diaryBookMarkCheck(false)
                .diaryFont(diaryReqSaveDTO.diaryFont())
                .diaryFontSize(diaryReqSaveDTO.diaryFontSize())
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public static Diary ofDraft(DiaryReqSaveDTO diaryReqSaveDTO, Long userId) {
        return Diary.builder()
                .diaryTitle(diaryReqSaveDTO.diaryTitle())
                .diaryDate(diaryReqSaveDTO.diaryDate())
                .diaryContent(diaryReqSaveDTO.diaryContent())
                .diaryWeather(diaryReqSaveDTO.diaryWeather())
                .diaryStatus(DiaryStatus.PUBLISHED)
                .userId(userId)
                .diaryBookMarkCheck(false)
                .diaryFont(diaryReqSaveDTO.diaryFont())
                .diaryFontSize(diaryReqSaveDTO.diaryFontSize())
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }
}