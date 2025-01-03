package moodbuddy.moodbuddy.domain.bookMark.repository;

import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface BookMarkRepositoryCustom {
    PageCustom<DiaryResFindDTO> getBookMarksWithPageable(Long userId, Pageable pageable);
}
