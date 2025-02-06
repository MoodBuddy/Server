package moodbuddy.moodbuddy.infra.batch.config;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.quddyTI.facade.QuddyTIFacade;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class QuddyTIBatchConfig {
    private final DataSource dataSource;
    private final QuddyTIFacade quddyTIFacade;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Spring Batch 5.x에서 `JobRepository`를 직접 등록해야 함
     */
    @Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    /**
     * Spring Batch Job 정의
     */
    @Bean
    public Job calculationJob(JobRepository jobRepository) {
        return new JobBuilder("quddyTICalculationJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step(jobRepository))
                .build();
    }

    /**
     * Step 정의 (Reader, Processor, Writer 포함)
     */
    @Bean
    public Step step(JobRepository jobRepository) {
        return new StepBuilder("calculateQuddyTIStep", jobRepository)
                .<Long, Long>chunk(1000, transactionManager())
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public ListItemReader<Long> reader() {
        String sql = "SELECT user_id FROM user WHERE user_role = 'ROLE_USER' AND mood_buddy_status = 'ACTIVE'";
        List<Long> userIds = jdbcTemplate.queryForList(sql, Long.class);
        return new ListItemReader<>(userIds);
    }

    @Bean
    public ItemProcessor<Long, Long> processor() {
        return userId -> {
            quddyTIFacade.createAndUpdateQuddyTI(userId);
            return userId;
        };
    }

    @Bean
    public JdbcBatchItemWriter<Long> writer() {
        return new JdbcBatchItemWriterBuilder<Long>()
                .dataSource(dataSource)
                .sql("UPDATE quddy_ti SET last_updated = NOW() WHERE user_id = :userId")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Bean(name = "taskExecutor-quddyTI")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.initialize();
        return executor;
    }

    /**
     * 트랜잭션 관리 빈 등록
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Scheduled(cron = "59 59 23 L * ?")
    public void executeMonthlyBatch() throws Exception {
        calculationJob(jobRepository());
    }
}