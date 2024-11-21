package moodbuddy.moodbuddy.domain.diary.domain.image;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudUploadDTO;

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

    @Column(name = "original_width")
    private int originalWidth;

    @Column(name = "original_height")
    private int originalHeight;

    @Column(name = "resize_width")
    private int resizeWidth;

    @Column(name = "resize_height")
    private int resizeHeight;

    @Column(name = "diary_img_file_name")
    private String diaryImgFileName;

    @Column(name = "diary_img_path")
    private String diaryImgPath;

    @Column(name = "diary_img_url")
    private String diaryImgURL;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_buddy_status")
    private MoodBuddyStatus moodBuddyStatus;

    public static DiaryImage from(CloudUploadDTO cloudUploadDTO) {
        return DiaryImage.builder()
                .diaryId(null)
                .diaryImgFileName(cloudUploadDTO.fileName())
                .diaryImgPath(cloudUploadDTO.filePath())
                .diaryImgURL(cloudUploadDTO.fileUrl())
                .originalWidth(cloudUploadDTO.originalWidth())
                .originalHeight(cloudUploadDTO.originalHeight())
                .resizeWidth(cloudUploadDTO.resizeWidth())
                .resizeHeight(cloudUploadDTO.resizeHeight())
                .moodBuddyStatus(MoodBuddyStatus.DIS_ACTIVE)
                .build();
    }

    public void updateStatus(Long diaryId) {
        this.diaryId = diaryId;
        this.moodBuddyStatus = MoodBuddyStatus.ACTIVE;
    }

    public void updateMoodBuddyStatus(MoodBuddyStatus moodBuddyStatus) { this.moodBuddyStatus = moodBuddyStatus; }
}
