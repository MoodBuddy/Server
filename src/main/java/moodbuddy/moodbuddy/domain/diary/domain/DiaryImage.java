package moodbuddy.moodbuddy.domain.diary.domain;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column(name = "diary_img_url")
    private String diaryImgURL;

    @Enumerated(value = EnumType.STRING)
    private DiaryImageStatus diaryImageStatus;

    @Column(name = "diary_image_file_name")
    private String diaryImgFileName;

    @Column(name = "diary_img_width")
    private Double diaryImgWidth;

    @Column(name = "diary_img_height")
    private Double diaryImgHeight;

    @Column(name = "diary_img_thumb_file_name")
    private String diaryImgThumbFileName;

    @Column(name = "diary_img_thumb_url")
    private String diaryImgThumbURL;

    @Column(name = "diary_img_thumb_width")
    private Double diaryImgThumbWidth;

    @Column(name = "diary_img_thumb_height")
    private Double diaryImgThumbHeight;
}
