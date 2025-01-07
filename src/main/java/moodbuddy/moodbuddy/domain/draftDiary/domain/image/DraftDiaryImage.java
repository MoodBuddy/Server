package moodbuddy.moodbuddy.domain.draftDiary.domain.image;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.global.common.base.BaseTimeEntity;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "draft_diary_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DraftDiaryImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "draft_diary_id")
    private Long draftDiaryId;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_buddy_status")
    private MoodBuddyStatus moodBuddyStatus;

    public static DraftDiaryImage of(Long draftDiaryId, String imageUrl) {
        return DraftDiaryImage.builder()
                .draftDiaryId(draftDiaryId)
                .imageUrl(imageUrl)
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
    }

    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) { this.moodBuddyStatus = moodBuddyStatus; }
}
