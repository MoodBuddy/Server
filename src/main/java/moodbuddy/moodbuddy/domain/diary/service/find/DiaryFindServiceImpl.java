package moodbuddy.moodbuddy.domain.diary.service.find;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.domain.diary.repository.find.DiaryFindRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryFindServiceImpl implements DiaryFindService {
    private final DiaryFindRepository diaryFindRepository;

    @Override
    public Page<DiaryResFindDTO> getDiaries(Pageable pageable, final Long userId) {
        return diaryFindRepository.findDiariesWithPageable(userId, pageable);
    }

    @Override
    public Page<DiaryResFindDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId) {
        return diaryFindRepository.findDiariesByEmotionWithPageable(diaryEmotion, userId, pageable);
    }

    @Override
    public Page<DiaryResFindDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId) {
        return diaryFindRepository.findDiariesByFilterWithPageable(requestDTO, userId, pageable);
    }
}
