package moodbuddy.moodbuddy.infra.batch;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.service.count.DiaryCountService;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.global.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class QuddyTIBatchConfig {
    private final QuddyTIBatchJDBCRepository quddyTIBatchJDBCRepository;
    private final DiaryCountService diaryCountService;
    private final JobRepository jobRepository;
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job quddyTIJob(Step updateUserStatusStep) {
        return new JobBuilder("quddyTIJob", jobRepository)
                .start(updateUserStatusStep)
                .build();
    }

    @Bean
    public Step quddyTIStep(CompositeItemWriter<QuddyTI> compositeWriter) {
        return new StepBuilder("quddyTIStep", jobRepository)
                .<Long, QuddyTI>chunk(100, transactionManager)
                .reader(userIdReader())
                .processor(compositeProcessor())
                .writer(compositeWriter)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Long> userIdReader() {
        return new JdbcCursorItemReaderBuilder<Long>()
                .dataSource(dataSource)
                .name("userIdReader")
                .sql("SELECT user_id FROM user WHERE deleted = 0 AND user_role = 'ROLE_USER'")
                .rowMapper((rs, rowNum) -> rs.getLong("user_id"))
                .build();
    }

    @Bean
    public CompositeItemProcessor<Long, QuddyTI> compositeProcessor() {
        CompositeItemProcessor<Long, QuddyTI> processor = new CompositeItemProcessor<>();
        processor.setDelegates(Arrays.asList(quddyTICreator(), quddyTIUpdater()));
        return processor;
    }

    @Bean
    public ItemProcessor<Long, QuddyTI> quddyTICreator() {
        return userId -> QuddyTI.of(userId, DateUtil.formatYear(LocalDate.now()), DateUtil.formatMonth(LocalDate.now()));
    }

    @Bean
    public ItemProcessor<Long, QuddyTI> quddyTIUpdater() {
        return userId -> {
            LocalDate[] dates = DateUtil.getLastMonthDates();
            Map<DiaryEmotion, Long> emotionCounts = diaryCountService.getEmotionCountsByDate(dates);
            Map<DiarySubject, Long> subjectCounts = diaryCountService.getSubjectCountsByDate(dates);
            QuddyTI quddyTI = quddyTIBatchJDBCRepository.findQuddyTIByUserIdAndDate(userId, DateUtil.formatYear(dates[0]), DateUtil.formatMonth(dates[1]));
            quddyTI.update(emotionCounts, subjectCounts);
            return quddyTI;
        };
    }

    @Bean
    public CompositeItemWriter<QuddyTI> compositeWriter() {
        List<ItemWriter<? super QuddyTI>> writers = List.of(saveQuddyTI(), updateQuddyTI());
        CompositeItemWriter<QuddyTI> writer = new CompositeItemWriter<>();
        writer.setDelegates(writers);
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<QuddyTI> saveQuddyTI() {
        return new JdbcBatchItemWriterBuilder<QuddyTI>()
                .dataSource(dataSource)
                .sql("INSERT INTO quddy_ti (user_id, quddy_ti_year, quddy_ti_month, diary_frequency, daily_count, growth_count, " +
                        "emotion_count, travel_count, happiness_count, anger_count, disgust_count, fear_count, neutral_count, " +
                        "sadness_count, surprise_count, quddy_ti_type, mood_buddy_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .beanMapped()
                .itemPreparedStatementSetter(new ItemPreparedStatementSetter<QuddyTI>() {
                    @Override
                    public void setValues(QuddyTI item, PreparedStatement ps) throws SQLException {
                        ps.setLong(1, item.getUserId());
                        ps.setString(2, item.getQuddyTIYear());
                        ps.setString(3, item.getQuddyTIMonth());
                        ps.setInt(4, item.getDiaryFrequency());
                        ps.setInt(5, item.getDailyCount());
                        ps.setInt(6, item.getGrowthCount());
                        ps.setInt(7, item.getEmotionCount());
                        ps.setInt(8, item.getTravelCount());
                        ps.setInt(9, item.getHappinessCount());
                        ps.setInt(10, item.getAngerCount());
                        ps.setInt(11, item.getDisgustCount());
                        ps.setInt(12, item.getFearCount());
                        ps.setInt(13, item.getNeutralCount());
                        ps.setInt(14, item.getSadnessCount());
                        ps.setInt(15, item.getSurpriseCount());
                        ps.setString(16, item.getQuddyTIType());
                        ps.setString(17, item.getMoodBuddyStatus().name());
                    }
                })
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<QuddyTI> updateQuddyTI() {
        return new JdbcBatchItemWriterBuilder<QuddyTI>()
                .dataSource(dataSource)
                .sql("UPDATE quddy_ti SET diary_frequency = ?, daily_count = ?, growth_count = ?, emotion_count = ?, " +
                        "travel_count = ?, happiness_count = ?, anger_count = ?, disgust_count = ?, fear_count = ?, neutral_count = ?, " +
                        "sadness_count = ?, surprise_count = ?, quddy_ti_type = ? WHERE id = ? AND user_id = ? AND quddy_ti_year = ? AND quddy_ti_month = ?")
                .beanMapped()
                .itemPreparedStatementSetter(new ItemPreparedStatementSetter<QuddyTI>() {
                    @Override
                    public void setValues(QuddyTI item, PreparedStatement ps) throws SQLException {
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
                        ps.setLong(14, item.getId());
                        ps.setLong(15, item.getUserId());
                        ps.setString(16, item.getQuddyTIYear());
                        ps.setString(17, item.getQuddyTIMonth());
                    }
                })
                .build();
    }
}