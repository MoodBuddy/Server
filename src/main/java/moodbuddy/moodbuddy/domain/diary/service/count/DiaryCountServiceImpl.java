package moodbuddy.moodbuddy.domain.diary.service.count;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.repository.count.DiaryCountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryCountServiceImpl implements DiaryCountService {
    private final DiaryCountRepository DiaryCountRepository;

    @Override
    public long getDiaryEmotionCount(DiaryEmotion diaryEmotion, LocalDate start, LocalDate end) {
        return DiaryCountRepository.countByEmotionAndDateRange(diaryEmotion, start, end);
    }

    @Override
    public long getDiarySubjectCount(DiarySubject subject, LocalDate start, LocalDate end) {
        return DiaryCountRepository.countBySubjectAndDateRange(subject, start, end);
    }
}
