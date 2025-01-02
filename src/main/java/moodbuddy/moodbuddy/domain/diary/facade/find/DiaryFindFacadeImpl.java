package moodbuddy.moodbuddy.domain.diary.facade.find;

import lombok.RequiredArgsConstructor;
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
public class DiaryFindFacadeImpl implements DiaryFindFacade {
    private final DiaryFindService diaryFindService;

    @Override
    public PageCustom<DiaryResFindDTO> getDiaries(Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.getDiaries(pageable, userId);
    }

    @Override
    public PageCustom<DiaryResFindDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.getDiariesByEmotion(diaryEmotion, pageable, userId);
    }

    @Override
    public PageCustom<DiaryResFindDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.getDiariesByFilter(requestDTO, pageable, userId);
    }
}
