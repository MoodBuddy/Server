package moodbuddy.moodbuddy.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "bigint")
    private Long userId;

    @Column(name = "user_role", columnDefinition = "varchar", length = 50)
    private String userRole;

    @Column(name = "nickname", columnDefinition = "varchar", length = 50)
    private String nickname;

    @Column(name = "kakao_id", columnDefinition = "bigint")
    private Long kakaoId;

    @Column(name = "alarm", columnDefinition = "tinyint")
    private Boolean alarm;

    @Column(name = "alarm_time", columnDefinition = "date")
    private LocalDateTime alarmTime;

    @Column(name = "birthday", columnDefinition = "datetime(6)")
    private LocalDateTime birthday;

    @Column(name = "gender", columnDefinition = "tinyint")
    private Boolean gender;

    @Column(name = "user_cur_diary_nums", columnDefinition = "int")
    private Integer userCurDiaryNums;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @Column(name="deleted", columnDefinition = "tinyint")
    private Boolean deleted;

    @Column(name = "access_token", columnDefinition = "text")
    private String accessToken;

    @Column(name = "access_token_expired_at", columnDefinition = "date")
    private LocalDate accessTokenExpiredAt;

    @Column(name = "refresh_token", columnDefinition = "text")
    private String refreshToken;

    @Column(name = "refresh_token_expired_at", columnDefinition = "date")
    private LocalDate refreshTokenExpiredAt;

}
