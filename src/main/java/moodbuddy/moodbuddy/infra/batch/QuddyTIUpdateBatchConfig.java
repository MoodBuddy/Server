package moodbuddy.moodbuddy.infra.batch;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.service.count.DiaryCountService;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class QuddyTIUpdateBatchConfig {
    private final DataSource dataSource;
    private final JobRepository jobRepository;
    private final DiaryCountService diaryCountService;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job quddyTIUpdateJob(Step quddyTIUpdateStep) {
        return new JobBuilder("quddyTIUpdateJob", jobRepository)
                .start(quddyTIUpdateStep)
                .build();
    }

    @Bean
    public Step quddyTIUpdateStep() {
        return new StepBuilder("quddyTIUpdateStep", jobRepository)
                .<QuddyTI, QuddyTI>chunk(50, transactionManager)
                .reader(quddyTIReader())
                .processor(findCountProcessor())
                .writer(updateQuddyTIWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<QuddyTI> quddyTIReader() {
        LocalDate[] dates = DateUtil.getLastMonthDates();
        String lastYear = DateUtil.formatYear(dates[0]);
        String lastMonth = DateUtil.formatMonth(dates[1]);
        String status = MoodBuddyStatus.DIS_ACTIVE.toString();
        return new JdbcCursorItemReaderBuilder<QuddyTI>()
                .dataSource(dataSource)
                .name("quddyTIReader")
                .sql("SELECT * FROM quddy_ti WHERE quddy_ti_year = ? AND quddy_ti_month = ? AND mood_buddy_status = ?")
                .queryArguments(lastYear, lastMonth, status)
                .rowMapper(new BeanPropertyRowMapper<>(QuddyTI.class))
                .fetchSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<QuddyTI, QuddyTI> findCountProcessor() {
        return quddyTI -> {
            LocalDate[] dates = DateUtil.getLastMonthDates();
            Map<DiaryEmotion, Long> emotionCounts = diaryCountService.getEmotionCountsByDate(quddyTI.getUserId(), dates);
            Map<DiarySubject, Long> subjectCounts = diaryCountService.getSubjectCountsByDate(quddyTI.getUserId(), dates);
            quddyTI.update(emotionCounts, subjectCounts);
            return quddyTI;
        };
    }

    @Bean
    public JdbcBatchItemWriter<QuddyTI> updateQuddyTIWriter() {
        return new JdbcBatchItemWriterBuilder<QuddyTI>()
                .dataSource(dataSource)
                .sql("UPDATE quddy_ti SET " +
                        "diary_frequency = ?, daily_count = ?, growth_count = ?, emotion_count = ?, travel_count = ?, " +
                        "happiness_count = ?, anger_count = ?, disgust_count = ?, fear_count = ?, neutral_count = ?, " +
                        "sadness_count = ?, surprise_count = ?, quddy_ti_type = ?, mood_buddy_status = ?, updated_time = NOW() " +
                        "WHERE id = ?")
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setInt(1, item.getDiaryFrequency());
                    ps.setInt(2, item.getDailyCount());
                    ps.setInt(3, item.getGrowthCount());
                    ps.setInt(4, item.getEmotionCount());
                    ps.setInt(5, item.getTravelCount());
                    ps.setInt(6, item.getHappinessCount());
                    ps.setInt(7, item.getAngerCount());
                    ps.setInt(8, item.getDisgustCount());
                    ps.setInt(9, item.getFearCount());
                    ps.setInt(10, item.getNeutralCount());
                    ps.setInt(11, item.getSadnessCount());
                    ps.setInt(12, item.getSurpriseCount());
                    ps.setString(13, item.getQuddyTIType());
                    ps.setString(14, item.getMoodBuddyStatus().toString());
                    ps.setLong(15, item.getId());
                })
                .assertUpdates(true)
                .build();
    }
}