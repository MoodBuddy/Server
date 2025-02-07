package moodbuddy.moodbuddy.infra.batch.write;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuddyTIWriter implements ItemWriter<QuddyTI> {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL =
            "INSERT INTO quddy_ti (user_id, year, month, diary_frequency, daily_count, growth_count, " +
                    "emotion_count, travel_count, happiness_count, anger_count, disgust_count, fear_count, " +
                    "neutral_count, sadness_count, surprise_count, quddy_ti, mood_buddy_status, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

    private static final String UPDATE_SQL = "UPDATE quddy_ti SET " +
            "diary_frequency = ?, daily_count = ?, growth_count = ?, emotion_count = ?, travel_count = ?, " +
            "happiness_count = ?, anger_count = ?, disgust_count = ?, fear_count = ?, neutral_count = ?, " +
            "sadness_count = ?, surprise_count = ?, quddy_ti = ?, mood_buddy_status = ?, updated_at = NOW() " +
            "WHERE user_id = ? AND year = ? AND month = ?";

    @Override
    public void write(@NotNull Chunk<? extends QuddyTI> chunk) {
        List<Object[]> insertBatchArgs = new ArrayList<>();
        List<Object[]> updateBatchArgs = new ArrayList<>();

        for (QuddyTI quddyTI : chunk.getItems()) {
            if (quddyTI.getId() == null) {
                insertBatchArgs.add(new Object[]{
                        quddyTI.getUserId(), quddyTI.getYear(), quddyTI.getMonth(), quddyTI.getDiaryFrequency(),
                        quddyTI.getDailyCount(), quddyTI.getGrowthCount(), quddyTI.getEmotionCount(), quddyTI.getTravelCount(),
                        quddyTI.getHappinessCount(), quddyTI.getAngerCount(), quddyTI.getDisgustCount(), quddyTI.getFearCount(),
                        quddyTI.getNeutralCount(), quddyTI.getSadnessCount(), quddyTI.getSurpriseCount(),
                        quddyTI.getQuddyTI(), quddyTI.getMoodBuddyStatus().name()
                });
            } else {
                updateBatchArgs.add(new Object[]{
                        quddyTI.getDiaryFrequency(), quddyTI.getDailyCount(), quddyTI.getGrowthCount(),
                        quddyTI.getEmotionCount(), quddyTI.getTravelCount(), quddyTI.getHappinessCount(),
                        quddyTI.getAngerCount(), quddyTI.getDisgustCount(), quddyTI.getFearCount(),
                        quddyTI.getNeutralCount(), quddyTI.getSadnessCount(), quddyTI.getSurpriseCount(),
                        quddyTI.getQuddyTI(), MoodBuddyStatus.ACTIVE.name(), quddyTI.getUserId(),
                        quddyTI.getYear(), quddyTI.getMonth()
                });
            }
        }

        if (!insertBatchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(INSERT_SQL, insertBatchArgs);
        }

        if (!updateBatchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(UPDATE_SQL, updateBatchArgs);
        }
    }
}
