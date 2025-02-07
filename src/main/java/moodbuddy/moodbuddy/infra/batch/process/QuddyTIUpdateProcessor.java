package moodbuddy.moodbuddy.infra.batch.process;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.service.count.DiaryCountService;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.domain.type.QuddyTIType;
import moodbuddy.moodbuddy.domain.quddyTI.exception.QuddyTIInvalidateException;
import moodbuddy.moodbuddy.domain.quddyTI.mapper.QuddyTIRowMapper;
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.global.util.DateUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuddyTIUpdateProcessor implements ItemProcessor<Long, QuddyTI> {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_BY_USERID_AND_DATE_SQL =
            "SELECT * FROM quddy_ti WHERE user_id = ? AND year = ? AND month = ?";

    @Override
    public QuddyTI process(Long userId) {
        LocalDate[] dateRange = DateUtils.getLastMonthDates();
        return jdbcTemplate.queryForObject(
                FIND_BY_USERID_AND_DATE_SQL,
                new Object[]{userId, DateUtils.formatYear(dateRange[0]), DateUtils.formatMonth(dateRange[1])},
                new QuddyTIRowMapper()
        );
    }
}