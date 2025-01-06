package moodbuddy.moodbuddy.domain.diary.facade.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.domain.diary.service.query.DiaryQueryService;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
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
        log.info("[getDiaries 조회] getDiaries(): {}", userId);
        return diaryQueryService.getDiaries(pageable, userId);
    }

    @Override
    public PageCustom<DiaryResQueryDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        log.info("[getDiariesByEmotion 조회] getDiariesByEmotion(): {}", userId);
        return diaryQueryService.getDiariesByEmotion(diaryEmotion, pageable, userId);
    }

    @Override
    public PageCustom<DiaryResQueryDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        log.info("[getDiariesByFilter 조회] getDiariesByFilter(): {}", userId);
        return diaryQueryService.getDiariesByFilter(requestDTO, pageable, userId);
    }
}