package moodbuddy.moodbuddy.infra.batch.process;

import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.global.util.DateUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class QuddyTICreatProcessor implements ItemProcessor<Long, QuddyTI> {

    @Override
    public QuddyTI process(Long userId) {
        LocalDate currentDate = LocalDate.now();
        return QuddyTI.of(userId, DateUtils.formatYear(currentDate), DateUtils.formatMonth(currentDate));
    }
}