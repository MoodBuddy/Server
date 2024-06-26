package moodbuddy.moodbuddy.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import moodbuddy.moodbuddy.domain.letter.entity.Letter;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Table(name = "member")
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;
    private String userName;
    private String userNickName;
    private String userBirth;
    private LocalDateTime userNoticeTime;
    private Integer userDiaryNums = 0;
    private String userRole = "ROLE_USER";

    // deleted 역할이 무엇인 지 몰라서 빼둠

    /** 생성자 **/
    protected User() {}

}
