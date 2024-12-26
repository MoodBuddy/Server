package moodbuddy.moodbuddy.domain.quddyTI.facade;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.service.count.DiaryCountService;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.mapper.QuddyTIMapper;
import moodbuddy.moodbuddy.domain.quddyTI.service.QuddyTIService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuddyTIFacadeImpl implements QuddyTIFacade {
    private final QuddyTIService quddyTIService;
    private final DiaryCountService diaryCountService;
    private final QuddyTIMapper quddyTIMapper;

    @Override
    public QuddyTIResDetailDTO getQuddyTIByDate(String year, String month) {
        Long userId = JwtUtil.getUserId();
        return quddyTIMapper.toResDetailDTO(
                quddyTIService.getQuddyTIByDate(userId, year, month)
        );
    }

    @Override
    @Transactional
    public void createAndUpadteQuddyTI() {
        Long userId = JwtUtil.getUserId();
        quddyTIService.createNewMonth(userId, LocalDate.now());
        LocalDate[] dates = getLastMonthDates();
        Map<DiaryEmotion, Long> emotionCounts = diaryCountService.getEmotionCountsByDate(dates);
        Map<DiarySubject, Long> subjectCounts = diaryCountService.getSubjectCountsByDate(dates);

        quddyTIService.processLastMonth(userId, getLastMonthDates(), emotionCounts, subjectCounts);
    }

    private LocalDate[] getLastMonthDates() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        return new LocalDate[]{
                lastMonth.atDay(1),
                lastMonth.atEndOfMonth()
        };
    }
}