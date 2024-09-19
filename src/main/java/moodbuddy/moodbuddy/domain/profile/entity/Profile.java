package moodbuddy.moodbuddy.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;

@Entity
@Table(name = "profile")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "profile_comment", columnDefinition = "varchar(255)")
    private String profileComment;

    @Column(name = "user_id", columnDefinition = "bigint")
    private Long userId;

    protected Profile() {}
}