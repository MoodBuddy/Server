package moodbuddy.moodbuddy.domain.diary.service.find;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.global.common.base.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.common.exception.ErrorCode;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNoAccessException;
import moodbuddy.moodbuddy.global.common.exception.diary.DiaryNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static moodbuddy.moodbuddy.global.common.exception.ErrorCode.NOT_FOUND_DIARY;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DiaryFindServiceImpl implements DiaryFindService {
    private final DiaryRepository diaryRepository;

    @Override
    public DiaryResDetailDTO findOneByDiaryId(final Long diaryId, final Long userId) {
        final Diary findDiary = findDiaryById(diaryId);
        validateDiaryAccess(findDiary, userId);
        return diaryRepository.findOneByDiaryId(diaryId);
    }

    @Override
    public Page<DiaryResDetailDTO> findAll(Pageable pageable, final Long userId) {
        return diaryRepository.findAllByUserIdWithPageable(userId, pageable);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId) {
        return diaryRepository.findAllByEmotionWithPageable(diaryEmotion, userId, pageable);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId) {
        return diaryRepository.findAllByFilterWithPageable(requestDTO, userId, pageable);
    }

    private Diary findDiaryById(Long diaryId) {
        return diaryRepository.findByDiaryIdAndMoodBuddyStatus(diaryId, MoodBuddyStatus.ACTIVE)
                .orElseThrow(() -> new DiaryNotFoundException(NOT_FOUND_DIARY));
    }

    private void validateDiaryAccess(Diary findDiary, Long userId) {
        if (!findDiary.getUserId().equals(userId)) {
            throw new DiaryNoAccessException(ErrorCode.NO_ACCESS_DIARY);
        }
    }
}
