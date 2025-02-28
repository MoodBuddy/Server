package moodbuddy.moodbuddy.domain.quddyTI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.domain.type.QuddyTIType;
import moodbuddy.moodbuddy.domain.quddyTI.repository.QuddyTIRepository;
import moodbuddy.moodbuddy.global.error.ErrorCode;
import moodbuddy.moodbuddy.domain.quddyTI.exception.QuddyTIInvalidateException;
import moodbuddy.moodbuddy.domain.quddyTI.exception.QuddyTINotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class QuddyTIServiceImpl implements QuddyTIService {
    private final QuddyTIRepository quddyTIRepository;

    @Override
    public QuddyTI getQuddyTIByDate(final Long userId, String year, String month) {
        return getQuddyTIByUserIdAndDate(userId, year, month);
    }

    private QuddyTI getQuddyTIByUserIdAndDate(Long userId, String year, String month) {
        return quddyTIRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .orElseThrow(() -> new QuddyTINotFoundException(ErrorCode.QUDDYTI_NOT_FOUND));
    }
}