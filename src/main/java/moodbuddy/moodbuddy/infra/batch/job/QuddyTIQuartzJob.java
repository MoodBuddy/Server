package moodbuddy.moodbuddy.infra.batch.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class QuddyTIQuartzJob implements Job {
    private final JobLauncher jobLauncher;
    private final org.springframework.batch.core.Job quddyTICalculationJob;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("runTime", new Date())
                    .toJobParameters();

            jobLauncher.run(quddyTICalculationJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}