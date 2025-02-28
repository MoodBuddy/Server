package moodbuddy.moodbuddy.infra.batch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuddyTIBatchSchedulerTest {
    @Autowired
    private QuddyTIBatchScheduler quddyTIBatchScheduler;

    @MockBean
    private JobLauncher jobLauncher;

    @MockBean
    private Job quddyTIJob;

    @Test
    @DisplayName("QuddyTI 생성 스케줄러 테스트")
    public void QuddyTI_생성_스케줄러_테스트() throws JobExecutionException {
        JobExecution jobExecution = new JobExecution(1L);
        when(jobLauncher.run(eq(quddyTIJob), any(JobParameters.class))).thenReturn(jobExecution);
        quddyTIBatchScheduler.runBatchJob();
        verify(jobLauncher).run(eq(quddyTIJob), any(JobParameters.class));
    }
}