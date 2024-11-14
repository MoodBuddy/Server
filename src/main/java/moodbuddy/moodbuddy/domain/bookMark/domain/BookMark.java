package moodbuddy.moodbuddy.domain.bookMark.domain;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.global.common.base.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "book_mark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_mark_id")
    private Long bookMarkId;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "diary_id")
    private Long diaryId;

    public static BookMark of(Long userId, Long diaryId) {
        return BookMark.builder()
                .userId(userId)
                .diaryId(diaryId)
                .build();
    }
}