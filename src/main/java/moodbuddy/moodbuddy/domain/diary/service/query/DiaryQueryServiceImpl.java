package moodbuddy.moodbuddy.domain.diary.service.query;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
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
    @Cacheable(cacheNames = "getDiaries", key = "'userId:'+#userId+'_'+'pageable.offset:'+#pageable.offset+'_'+'pageable.pageSize:'+#pageable.pageSize", unless = "#result == null")
    public PageCustom<DiaryResQueryDTO> getDiaries(Pageable pageable, final Long userId) {
        return diaryQueryRepository.findDiariesWithPageable(userId, pageable);
    }

    @Override
    @Cacheable(cacheNames = "getDiariesByEmotion", key = "'userId:'+#userId+'_'+'diaryEmotion:'+#diaryEmotion+'_'+'pageable.offset:'+#pageable.offset+'_'+'pageable.pageSize:'+#pageable.pageSize", unless = "#result == null")
    public PageCustom<DiaryResQueryDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable, final Long userId) {
        return diaryQueryRepository.findDiariesByEmotionWithPageable(diaryEmotion, userId, pageable);
    }

    @Override
    @Cacheable(
            cacheNames = "getDiariesByFilter",
            key = "'userId:' + #userId + '_'+'filter:' + (#requestDTO.diaryEmotion() ?: 'defaultEmotion') + (#requestDTO.diarySubject() ?: 'defaultSubject') + (#requestDTO.keyWord() ?: 'defaultKeyword') + (#requestDTO.year() ?: 'defaultYear') + (#requestDTO.month() ?: 'defaultMonth') + '_'+'pageable.offset:' + #pageable.offset + '_'+'pageable.pageSize:' + #pageable.pageSize", unless = "#result == null"
    )
    public PageCustom<DiaryResQueryDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable, final Long userId) {
        return diaryQueryRepository.findDiariesByFilterWithPageable(requestDTO, userId, pageable);
    }
}
