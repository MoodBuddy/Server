package moodbuddy.moodbuddy.domain.bookMark.repository;

import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface BookMarkRepositoryCustom {
    PageCustom<DiaryResQueryDTO> getBookMarksWithPageable(Long userId, Pageable pageable);
}
