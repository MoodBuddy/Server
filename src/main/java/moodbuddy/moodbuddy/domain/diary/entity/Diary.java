package moodbuddy.moodbuddy.domain.diary.entity;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;

import java.time.LocalDate;

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
    private Boolean diaryBookMarkCheck; // 북마크 여부

    /** 추가 칼럼 **/
    @Enumerated(EnumType.STRING)
    @Column(name = "diary_font")
    private DiaryFont diaryFont;

    @Enumerated(EnumType.STRING)
    @Column(name = "diary_font_size")
    private DiaryFontSize diaryFontSize;

    public void updateDiary(DiaryReqUpdateDTO diaryReqUpdateDTO, String diarySummary, DiarySubject diarySubject) {
        this.diaryTitle = diaryReqUpdateDTO.getDiaryTitle();
        this.diaryDate = diaryReqUpdateDTO.getDiaryDate();
        this.diaryContent = diaryReqUpdateDTO.getDiaryContent();
        this.diaryWeather = diaryReqUpdateDTO.getDiaryWeather();
        this.diarySummary = diarySummary;
        this.diaryStatus = DiaryStatus.PUBLISHED;
        this.diarySubject = diarySubject;
        this.diaryFont = diaryReqUpdateDTO.getDiaryFont();
        this.diaryFontSize = diaryReqUpdateDTO.getDiaryFontSize();
    }

    public void setDiaryEmotion(DiaryEmotion diaryEmotion) {
        this.diaryEmotion = diaryEmotion;
    }
    public void setDiaryBookMarkCheck(Boolean diaryBookMarkCheck) { this.diaryBookMarkCheck = diaryBookMarkCheck; }
}