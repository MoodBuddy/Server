package moodbuddy.moodbuddy.domain.bookMark.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookMarkService {
    boolean toggle(Diary diary, final Long userId);
    Page<DiaryResDetailDTO> bookMarkFindAllByWithPageable(Pageable pageable, final Long userId);
    void deleteByDiaryId(Long diaryId);
}
