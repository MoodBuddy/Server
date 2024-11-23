package moodbuddy.moodbuddy.domain.quddyTI.scheduler;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.quddyTI.facade.QuddyTIFacade;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuddyTIScheduler {
    private final QuddyTIFacade quddyTIFacade;

    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional
    public void aggregateAndSaveDiaryData() {
        quddyTIFacade.createAndUpadteQuddyTI();
    }
}