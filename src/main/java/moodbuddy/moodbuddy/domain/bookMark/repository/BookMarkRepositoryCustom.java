package moodbuddy.moodbuddy.domain.bookMark.repository;

import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookMarkRepositoryCustom {
    Page<DiaryResFindDTO> getBookMarksWithPageable(Long userId, Pageable pageable);
}
