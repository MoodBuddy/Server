package moodbuddy.moodbuddy.domain.bookMark.facade;

import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface BookMarkFacade {
    boolean toggle(final Long diaryId);
    PageCustom<DiaryResQueryDTO> getBookMarks(Pageable pageable);
}
