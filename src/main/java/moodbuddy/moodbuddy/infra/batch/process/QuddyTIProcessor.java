package moodbuddy.moodbuddy.infra.batch.process;

import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class QuddyTIProcessor implements ItemProcessor<Long, QuddyTI> {
    private static final String formatMonth = "%02d";

    @Override
    public QuddyTI process(Long userId) {
        LocalDate currentDate = LocalDate.now();
        return QuddyTI.of(userId, formatYear(currentDate), formatMonth(currentDate));
    }

    private String formatYear(LocalDate date) {
        return String.valueOf(date.getYear());
    }
    private String formatMonth(LocalDate date) {
        return String.format(formatMonth, date.getMonthValue());
    }
}