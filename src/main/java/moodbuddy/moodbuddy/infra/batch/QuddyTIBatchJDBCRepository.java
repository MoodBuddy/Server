package moodbuddy.moodbuddy.infra.batch;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class QuddyTIBatchJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public QuddyTI findQuddyTIByUserIdAndDate(Long userId, String year, String month) {
        String sql = "SELECT * FROM quddy_ti WHERE user_id = ? AND year = ? AND month = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            return QuddyTI.builder()
                    .id(rs.getLong("id"))
                    .userId(rs.getLong("user_id"))
                    .year(rs.getString("year"))
                    .month(rs.getString("month"))
                    .diaryFrequency(rs.getInt("diary_frequency"))
                    .dailyCount(rs.getInt("daily_count"))
                    .growthCount(rs.getInt("growth_count"))
                    .emotionCount(rs.getInt("emotion_count"))
                    .travelCount(rs.getInt("travel_count"))
                    .happinessCount(rs.getInt("happiness_count"))
                    .angerCount(rs.getInt("anger_count"))
                    .disgustCount(rs.getInt("disgust_count"))
                    .fearCount(rs.getInt("fear_count"))
                    .neutralCount(rs.getInt("neutral_count"))
                    .sadnessCount(rs.getInt("sadness_count"))
                    .surpriseCount(rs.getInt("surprise_count"))
                    .quddyTI(rs.getString("quddy_ti"))
                    .moodBuddyStatus(MoodBuddyStatus.valueOf(rs.getString("mood_buddy_status")))
                    .build();
        }, userId, year, month);
    }

    public long findEmotionCountsByDate(DiaryEmotion emotion, LocalDate start, LocalDate end) {
        String sql = "SELECT COUNT(*) FROM diary WHERE date BETWEEN ? AND ? AND emotion = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, start, end, emotion.name());
    }

    public long findSubjectCountsByDate(DiarySubject subject, LocalDate start, LocalDate end) {
        String sql = "SELECT COUNT(*) FROM diary WHERE date BETWEEN ? AND ? AND subject = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, start, end, subject.name());
    }
}