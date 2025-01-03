package moodbuddy.moodbuddy.domain.bookMark.facade;

import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;

public interface BookMarkFacade {
    boolean toggle(final Long diaryId);
    PageCustom<DiaryResFindDTO> getBookMarks(Pageable pageable);
}
