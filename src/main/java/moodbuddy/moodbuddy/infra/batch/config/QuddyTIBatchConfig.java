package moodbuddy.moodbuddy.infra.batch.config;

import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.infra.batch.process.QuddyTICreatProcessor;
import moodbuddy.moodbuddy.infra.batch.process.QuddyTIUpdateProcessor;
import moodbuddy.moodbuddy.infra.batch.read.QuddyTIReader;
import moodbuddy.moodbuddy.infra.batch.write.QuddyTIWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
@EnableBatchProcessing
public class QuddyTIBatchConfig {
    @Bean
    public Job quddyTIJob(JobRepository jobRepository, Step quddyTIStep) {
        return new JobBuilder("quddyTIJob", jobRepository)
                .start(quddyTIStep)
                .build();
    }

    @Bean
    public Step quddyTIStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            QuddyTIReader reader, CompositeItemProcessor<Long, QuddyTI> compositeProcessor,
                            QuddyTIWriter writer) {
        return new StepBuilder("quddyTIStep", jobRepository)
                .<Long, QuddyTI>chunk(10, transactionManager)
                .reader(reader)
                .processor(compositeProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public CompositeItemProcessor<Long, QuddyTI> quddyTICompositeProcessor(
            QuddyTICreatProcessor createProcessor, QuddyTIUpdateProcessor updateProcessor) {
        CompositeItemProcessor<Long, QuddyTI> compositeProcessor = new CompositeItemProcessor<>();
        compositeProcessor.setDelegates(Arrays.asList(createProcessor, updateProcessor));
        return compositeProcessor;
    }
}