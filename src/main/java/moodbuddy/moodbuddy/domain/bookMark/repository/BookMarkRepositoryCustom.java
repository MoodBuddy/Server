package moodbuddy.moodbuddy.domain.bookMark.repository;

import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookMarkRepositoryCustom {
    Page<DiaryResDetailDTO> findAllWithPageable(Long userId, Pageable pageable);
}
