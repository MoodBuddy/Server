package moodbuddy.moodbuddy.domain.quddyTI.mapper;

import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuddyTIRowMapper implements RowMapper<QuddyTI> {
    @Override
    public QuddyTI mapRow(ResultSet rs, int rowNum) throws SQLException {
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
    }
}
