package moodbuddy.moodbuddy.domain.quddyTI.entity;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quddy_ti")
public class QuddyTI extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    /** 월 **/
    @Column(name = "quddy_ti_year_month", columnDefinition = "varchar(7)")
    private String quddyTIYearMonth;

    /** 일기 작성 빈도수 **/
    @Column(name = "diary_frequency", columnDefinition = "int")
    private Integer diaryFrequency;

    /** 일기 주제 **/
    @Column(name = "daily_count", columnDefinition = "int")
    private Integer dailyCount;
    @Column(name = "growth_count", columnDefinition = "int")
    private Integer growthCount;
    @Column(name = "emotion_count", columnDefinition = "int")
    private Integer emotionCount;
    @Column(name = "travel_count", columnDefinition = "int")
    private Integer travelCount;

    /** 감정 **/
    @Column(name = "happiness_count", columnDefinition = "int")
    private Integer happinessCount;
    @Column(name = "anger_count", columnDefinition = "int")
    private Integer angerCount;
    @Column(name = "disgust_count", columnDefinition = "int")
    private Integer disgustCount;
    @Column(name = "fear_count", columnDefinition = "int")
    private Integer fearCount;
    @Column(name = "neutral_count", columnDefinition = "int")
    private Integer neutralCount;
    @Column(name = "sadness_count", columnDefinition = "int")
    private Integer sadnessCount;
    @Column(name = "surprise_count", columnDefinition = "int")
    private Integer surpriseCount;

    /** 쿼디티아이 **/
    @Column(name = "quddy_ti_type", columnDefinition = "varchar(10)")
    private String quddyTIType ;
}
