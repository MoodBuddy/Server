package moodbuddy.moodbuddy.infra.batch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@SpringBatchTest
class QuddyTIBatchSchedulerTest {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job quddyTIJob;

    @Test
    @DisplayName("Batch Job 테스트")
    void Batch_Job_테스트() throws Exception {
        JobExecution jobExecution = jobLauncher.run(quddyTIJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());

        assertEquals("COMPLETED", jobExecution.getStatus().toString());
    }
}