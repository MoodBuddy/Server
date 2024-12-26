package moodbuddy.moodbuddy.domain.diary.service.find;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.repository.find.DiaryFindRepository;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNoAccessException;
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
    public Page<DiaryResDetailDTO> getDiaries(Pageable pageable, final Long userId) {
        return diaryFindRepository.findAllByUserIdWithPageable(userId, pageable);
    }

    @Override
    public Page<DiaryResDetailDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId) {
        return diaryFindRepository.findAllByEmotionWithPageable(diaryEmotion, userId, pageable);
    }

    @Override
    public Page<DiaryResDetailDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId) {
        return diaryFindRepository.findAllByFilterWithPageable(requestDTO, userId, pageable);
    }

    private void validateDiaryAccess(Diary findDiary, Long userId) {
        if (!findDiary.getUserId().equals(userId)) {
            throw new DiaryNoAccessException(ErrorCode.NO_ACCESS_DIARY);
        }
    }
}
