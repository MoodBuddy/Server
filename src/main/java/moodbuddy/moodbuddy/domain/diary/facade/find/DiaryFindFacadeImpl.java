package moodbuddy.moodbuddy.domain.diary.facade.find;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.service.find.DiaryFindService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryFindFacadeImpl implements DiaryFindFacade {
    private final DiaryFindService diaryFindService;

    @Override
    public Page<DiaryResDetailDTO> getDiaries(Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.getDiaries(pageable, userId);
    }

    @Override
    public Page<DiaryResDetailDTO> getDiariesByEmotion(DiaryEmotion diaryEmotion, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.getDiariesByEmotion(diaryEmotion, pageable, userId);
    }

    @Override
    public Page<DiaryResDetailDTO> getDiariesByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.getDiariesByFilter(requestDTO, pageable, userId);
    }
}
