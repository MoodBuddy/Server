package moodbuddy.moodbuddy.domain.diary.facade.find;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.domain.diary.service.find.DiaryFindService;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DiaryFindFacadeImpl implements DiaryFindFacade {
    private final DiaryFindService diaryFindService;

    @Override
    public PageCustom<DiaryResFindDTO> getDiaries(Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        log.info("[getDiaries 조회] getDiaries(): {}", userId);
        return diaryFindService.getDiaries(pageable, userId);
    }

    @Override
    public PageCustom<DiaryResFindDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        log.info("[getDiariesByEmotion 조회] getDiariesByEmotion(): {}", userId);
        return diaryFindService.getDiariesByEmotion(diaryEmotion, pageable, userId);
    }

    @Override
    public PageCustom<DiaryResFindDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        log.info("[getDiariesByFilter 조회] getDiariesByFilter(): {}", userId);
        return diaryFindService.getDiariesByFilter(requestDTO, pageable, userId);
    }
}