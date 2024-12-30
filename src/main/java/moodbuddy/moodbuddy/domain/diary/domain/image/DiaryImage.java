package moodbuddy.moodbuddy.domain.diary.domain.image;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "diary_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_image_id")
    private Long id;

    @Column(name = "diary_id")
    private Long diaryId;

    @Column(name = "diary_img_url")
    private String diaryImgURL;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_buddy_status")
    private MoodBuddyStatus moodBuddyStatus;

    public static DiaryImage of(Long diaryId, String diaryImgURL) {
        return DiaryImage.builder()
                .diaryId(diaryId)
                .diaryImgURL(diaryImgURL)
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) { this.moodBuddyStatus = moodBuddyStatus; }
}
