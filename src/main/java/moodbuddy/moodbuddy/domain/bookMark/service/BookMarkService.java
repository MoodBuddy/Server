package moodbuddy.moodbuddy.domain.bookMark.service;

import moodbuddy.moodbuddy.domain.bookMark.dto.response.BookMarkResToggleDTO;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookMarkService {
    BookMarkResToggleDTO toggle(Diary diary, final Long userId);
    Page<DiaryResDetailDTO> bookMarkFindAllByWithPageable(Pageable pageable, final Long userId);
}
