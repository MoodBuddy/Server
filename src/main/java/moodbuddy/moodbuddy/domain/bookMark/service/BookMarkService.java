package moodbuddy.moodbuddy.domain.bookMark.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface BookMarkService {
    boolean toggle(Diary diary, final Long userId);
    PageCustom<DiaryResQueryDTO> getBookMarks(Pageable pageable, final Long userId);
    void deleteByDiaryId(Long diaryId);
}
