package moodbuddy.moodbuddy.domain.diary.service.count;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DiaryCountServiceImpl implements DiaryCountService {
    private final DiaryRepository diaryRepository;

    @Override
    public long getDiaryEmotionCount(DiaryEmotion diaryEmotion, LocalDate start, LocalDate end) {
        return diaryRepository.countByEmotionAndDateRange(diaryEmotion, start, end);
    }

    @Override
    public long getDiarySubjectCount(DiarySubject subject, LocalDate start, LocalDate end) {
        return diaryRepository.countBySubjectAndDateRange(subject, start, end);
    }
}
