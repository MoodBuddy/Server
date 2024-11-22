package moodbuddy.moodbuddy.domain.quddyTI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.repository.QuddyTIRepository;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.quddyTI.QuddyTINotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class QuddyTIServiceImpl implements QuddyTIService {
    private final QuddyTIRepository quddyTIRepository;

    @Override
    public QuddyTI findByDate(final Long userId, String year, String month) {
        return getQuddyTIByUserIdAndDate(userId, year, month);
    }

    @Override
    @Transactional
    public void createNewMonthQuddyTI(Long userId, LocalDate currentDate) {
        quddyTIRepository.save(QuddyTI.of(userId, formatYear(currentDate), formatMonth(currentDate)));
    }

    @Override
    @Transactional
    public void updateLastMonthQuddyTI(Long userId, LocalDate currentDate,
                                       Map<DiaryEmotion, Long> emotionCounts,
                                       Map<DiarySubject, Long> subjectCounts,
                                       String quddyTIType) {
        QuddyTI quddyTI = getQuddyTIByUserIdAndDate(userId, formatYear(currentDate), formatMonth(currentDate));
        quddyTI.updateQuddyTI(emotionCounts, subjectCounts, quddyTIType);
    }

    private QuddyTI getQuddyTIByUserIdAndDate(Long userId, String year, String month) {
        return quddyTIRepository.findByUserIdAndQuddyTIYearAndQuddyTIMonth(userId, year, month)
                .orElseThrow(() -> new QuddyTINotFoundException(ErrorCode.NOT_FOUND_QUDDYTI));
    }

    private String formatYear(LocalDate date) {
        return String.valueOf(date.getYear());
    }

    private String formatMonth(LocalDate date) {
        return String.format("%02d", date.getMonthValue());
    }
}