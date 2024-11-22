package moodbuddy.moodbuddy.domain.quddyTI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.service.count.DiaryCountService;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.mapper.QuddyTIMapper;
import moodbuddy.moodbuddy.domain.quddyTI.repository.QuddyTIRepository;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.quddyTI.QuddyTINotFoundException;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class QuddyTIServiceImpl implements QuddyTIService {
    private final QuddyTIRepository quddyTIRepository;

    @Override
    public List<QuddyTIResDetailDTO> findAll(final Long userId) {
        return quddyTIRepository.findByUserId(userId)
                .orElseThrow(() -> new QuddyTINotFoundException(ErrorCode.NOT_FOUND_QUDDYTI))
                .stream()
                .map(QuddyTIMapper::toQuddyTIResDetailDTO)
                .toList();
    }

    @Override
    @Transactional
    public void createNewMonthQuddyTI(Long userId, LocalDate currentMonth) {
        String yearMonth = formatYearMonth(currentMonth);
        quddyTIRepository.save(QuddyTIMapper.toQuddyTIEntity(userId, yearMonth));
    }

    @Override
    @Transactional
    public void updateLastMonthQuddyTI(Long userId, LocalDate start,
                                       Map<DiaryEmotion, Long> emotionCounts,
                                       Map<DiarySubject, Long> subjectCounts,
                                       String quddyTIType) {
        String yearMonth = formatYearMonth(start);
        QuddyTI lastMonthQuddyTI = quddyTIRepository.findByUserIdAndQuddyTIYearMonth(userId, yearMonth)
                .orElseThrow(() -> new QuddyTINotFoundException(ErrorCode.NOT_FOUND_QUDDYTI));

        lastMonthQuddyTI.updateQuddyTI(emotionCounts, subjectCounts, quddyTIType);
    }

    private String formatYearMonth(LocalDate date) {
        return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
    }
}