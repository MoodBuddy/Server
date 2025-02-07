package moodbuddy.moodbuddy.infra.quartz.config;

import moodbuddy.moodbuddy.infra.quartz.job.QuddyTIBatchJobScheduler;
import org.quartz.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(QuddyTIBatchJobScheduler.class)
                .withIdentity("quudyTIBatchJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("quddyTIBatchJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 * ?"))
                .build();
    }
}