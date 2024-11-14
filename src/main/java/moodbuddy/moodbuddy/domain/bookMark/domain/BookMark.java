package moodbuddy.moodbuddy.domain.bookMark.domain;

import jakarta.persistence.*;
import lombok.*;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    public static BookMark of(Long userId, Diary diary) {
        return BookMark.builder()
                .userId(userId)
                .diary(diary)
                .build();
    }
}