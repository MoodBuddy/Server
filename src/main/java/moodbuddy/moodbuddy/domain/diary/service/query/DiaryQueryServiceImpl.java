package moodbuddy.moodbuddy.domain.diary.service.query;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.query.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.domain.diary.repository.query.DiaryQueryRepository;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryQueryServiceImpl implements DiaryQueryService {
    private final DiaryQueryRepository diaryQueryRepository;

    @Override
    @Cacheable(
            cacheNames = "getDiaries",
            key = "'userId:'+#userId+'_'+'pageable.offset:'+#pageable.offset+'_'+'pageable.pageSize:'+#pageable.pageSize",
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
}