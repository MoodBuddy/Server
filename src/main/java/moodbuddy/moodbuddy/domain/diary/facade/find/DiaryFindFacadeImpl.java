package moodbuddy.moodbuddy.domain.diary.facade.find;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.domain.base.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.service.find.DiaryFindService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiaryFindFacadeImpl implements DiaryFindFacade {
    private final DiaryFindService diaryFindService;

    @Override
    public DiaryResDetailDTO findOneByDiaryId(final Long diaryId) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.findOneByDiaryId(diaryId, userId);
    }

    @Override
    public Page<DiaryResDetailDTO> findAll(Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.findAll(pageable, userId);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByEmotion(DiaryEmotion diaryEmotion, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.findAllByEmotion(diaryEmotion, pageable, userId);
    }

    @Override
    public Page<DiaryResDetailDTO> findAllByFilter(DiaryReqFilterDTO requestDTO, Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return diaryFindService.findAllByFilter(requestDTO, pageable, userId);
    }
}
