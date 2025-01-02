package moodbuddy.moodbuddy.domain.diary.service.find;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.domain.diary.repository.find.DiaryFindRepository;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryFindServiceImpl implements DiaryFindService {
    private final DiaryFindRepository diaryFindRepository;

    @Override
//    @Cacheable(cacheNames = "getDiaries", key = "#userId", unless = "#result == null")
    public PageCustom<DiaryResFindDTO> getDiaries(Pageable pageable, final Long userId) {
        return diaryFindRepository.findDiariesWithPageable(userId, pageable);
    }

    @Override
//    @Cacheable(cacheNames = "getDiariesByEmotion", key = "#userId + #pageable.pageNumber", unless = "#result == null")
    public PageCustom<DiaryResFindDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId) {
        return diaryFindRepository.findDiariesByEmotionWithPageable(diaryEmotion, userId, pageable);
    }

    @Override
//    @Cacheable(cacheNames = "getDiariesByFilter", key = "#userId + #pageable.pageNumber", unless = "#result == null")
    public PageCustom<DiaryResFindDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId) {
        return diaryFindRepository.findDiariesByFilterWithPageable(requestDTO, userId, pageable);
    }
}
