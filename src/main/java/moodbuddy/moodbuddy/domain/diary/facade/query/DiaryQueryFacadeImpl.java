package moodbuddy.moodbuddy.domain.diary.facade.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.query.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.domain.diary.service.query.DiaryQueryService;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import moodbuddy.moodbuddy.global.util.JwtUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DiaryQueryFacadeImpl implements DiaryQueryFacade {
    private final DiaryQueryService diaryQueryService;

    @Override
    public PageCustom<DiaryResQueryDTO> getDiaries(Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryQueryService.getDiaries(userId, pageable);
    }

    @Override
    public PageCustom<DiaryResQueryDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryQueryService.getDiariesByEmotion(userId, diaryEmotion, pageable);
    }

    @Override
    public PageCustom<DiaryResQueryDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryQueryService.getDiariesByFilter(userId, requestDTO, pageable);
    }
}