package moodbuddy.moodbuddy.domain.diary.service.query;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.query.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.domain.diary.repository.query.DiaryQueryRepository;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryQueryServiceImpl implements DiaryQueryService {
    private final DiaryQueryRepository diaryQueryRepository;

    //TODO 오름차순 내림차순 생각해야 함.
    @Override
    @Cacheable(
            cacheNames = "diaries",
            key = "'userId:' + #userId + '_page:' + #pageable.pageNumber + '_size:' + #pageable.pageSize",
            unless = "#result == null"
    )
    public PageCustom<DiaryResQueryDTO> getDiaries(final Long userId, Pageable pageable) {
        return diaryQueryRepository.findDiariesWithPageable(userId, pageable);
    }

    @Override
    public PageCustom<DiaryResQueryDTO> getDiariesByEmotion(final Long userId, DiaryEmotion diaryEmotion, Pageable pageable) {
        return diaryQueryRepository.findDiariesByEmotionWithPageable(userId, diaryEmotion, pageable);
    }

    @Override
    public PageCustom<DiaryResQueryDTO> getDiariesByFilter(final Long userId, DiaryReqFilterDTO requestDTO, Pageable pageable) {
        return diaryQueryRepository.findDiariesByFilterWithPageable(userId, requestDTO, pageable);
    }

    @Override
    @CachePut(
            cacheNames = "diaries",
            key = "'userId:' + #userId + '_page:' + #pageable.pageNumber + '_size:' + #pageable.pageSize"
    )
    public PageCustom<DiaryResQueryDTO> refreshDiariesCache(final Long userId, Pageable pageable) {
        return diaryQueryRepository.findDiariesWithPageable(userId, pageable);
    }
}