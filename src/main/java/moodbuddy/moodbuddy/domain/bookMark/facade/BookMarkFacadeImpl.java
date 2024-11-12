package moodbuddy.moodbuddy.domain.bookMark.facade;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.bookMark.dto.response.BookMarkResToggleDTO;
import moodbuddy.moodbuddy.domain.bookMark.service.BookMarkService;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.service.base.DiaryService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookMarkFacadeImpl implements BookMarkFacade {
    private final BookMarkService bookMarkService;
    private final DiaryService diaryService;

    @Override
    public BookMarkResToggleDTO toggle(final Long diaryId) {
        final Long userId = JwtUtil.getUserId();
        Diary findDiary = diaryService.getDiaryById(diaryId);
        diaryService.validateDiaryAccess(findDiary, userId);
        return bookMarkService.toggle(findDiary, userId);
    }

    @Override
    public Page<DiaryResDetailDTO> bookMarkFindAllByWithPageable(Pageable pageable) {
        final Long userId = JwtUtil.getUserId();
        return bookMarkService.bookMarkFindAllByWithPageable(pageable, userId);
    }
}
