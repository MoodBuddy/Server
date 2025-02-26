package moodbuddy.moodbuddy.domain.draftDiary.domain;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.type.*;
import moodbuddy.moodbuddy.domain.draftDiary.dto.request.DraftDiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.draftDiary.exception.DraftDiaryNoAccessException;
import moodbuddy.moodbuddy.global.common.base.BaseTimeEntity;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.error.ErrorCode;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "draft_diary")
public class DraftDiary extends BaseTimeEntity {
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

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "font")
    private DiaryFont font;

    @Enumerated(EnumType.STRING)
    @Column(name = "font_size")
    private DiaryFontSize fontSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_buddy_status")
    private MoodBuddyStatus moodBuddyStatus;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    public static DraftDiary of(DraftDiaryReqSaveDTO requestDTO, Long userId) {
        return DraftDiary.builder()
                .title(requestDTO.diaryTitle())
                .date(requestDTO.diaryDate())
                .content(requestDTO.diaryContent())
                .weather(requestDTO.diaryWeather())
                .userId(userId)
                .font(requestDTO.diaryFont())
                .fontSize(requestDTO.diaryFontSize())
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) {
        this.moodBuddyStatus = moodBuddyStatus;
    }

    public void validateDraftDiaryAccess(final Long userId) {
        if (!this.getUserId().equals(userId)) {
            throw new DraftDiaryNoAccessException(ErrorCode.DRAFT_DIARY_NO_ACCESS);
        }
    }
}
