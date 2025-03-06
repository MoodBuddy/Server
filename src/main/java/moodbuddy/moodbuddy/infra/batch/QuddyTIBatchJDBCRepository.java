package moodbuddy.moodbuddy.infra.batch;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class QuddyTIBatchJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public Long save(QuddyTI quddyTI) {
        String sql = """
            INSERT INTO quddy_ti (
                user_id, quddy_ti_year, quddy_ti_month, diary_frequency, daily_count, growth_count,
                emotion_count, travel_count, happiness_count, anger_count, disgust_count, fear_count,
                neutral_count, sadness_count, surprise_count, quddy_ti_type, mood_buddy_status
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, quddyTI.getUserId());
            ps.setString(2, quddyTI.getQuddyTIYear());
            ps.setString(3, quddyTI.getQuddyTIMonth());
            ps.setInt(4, quddyTI.getDiaryFrequency());
            ps.setInt(5, quddyTI.getDailyCount());
            ps.setInt(6, quddyTI.getGrowthCount());
            ps.setInt(7, quddyTI.getEmotionCount());
            ps.setInt(8, quddyTI.getTravelCount());
            ps.setInt(9, quddyTI.getHappinessCount());
            ps.setInt(10, quddyTI.getAngerCount());
            ps.setInt(11, quddyTI.getDisgustCount());
            ps.setInt(12, quddyTI.getFearCount());
            ps.setInt(13, quddyTI.getNeutralCount());
            ps.setInt(14, quddyTI.getSadnessCount());
            ps.setInt(15, quddyTI.getSurpriseCount());
            ps.setString(16, quddyTI.getQuddyTIType());
            ps.setString(17, quddyTI.getMoodBuddyStatus().toString());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public long findEmotionCountsByUserIdAndDate(Long userId, DiaryEmotion emotion, LocalDate start, LocalDate end) {
        String sql = "SELECT COUNT(*) FROM diary WHERE user_id = ? AND date BETWEEN ? AND ? AND emotion = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, userId, start, end, emotion.name());
    }

    public long findSubjectCountsByUserIdAndDate(Long userId, DiarySubject subject, LocalDate start, LocalDate end) {
        String sql = "SELECT COUNT(*) FROM diary WHERE user_id = ? AND date BETWEEN ? AND ? AND subject = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, userId, start, end, subject.name());
    }
}