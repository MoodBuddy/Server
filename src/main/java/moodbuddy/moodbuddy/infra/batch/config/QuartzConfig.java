package moodbuddy.moodbuddy.infra.batch.config;

import moodbuddy.moodbuddy.infra.batch.job.QuddyTIQuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail quddyTIJobDetail() {
        return JobBuilder.newJob(QuddyTIQuartzJob.class)
                .withIdentity("quddyTIQuartzJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger quddyTIJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(quddyTIJobDetail())
                .withIdentity("quddyTIQuartzTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 * ?")) // 매월 1일 00:00 실행
                .build();
    }
}