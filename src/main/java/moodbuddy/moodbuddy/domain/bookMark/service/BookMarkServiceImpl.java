package moodbuddy.moodbuddy.domain.bookMark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.domain.BookMark;
import moodbuddy.moodbuddy.domain.bookMark.repository.BookMarkRepository;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BookMarkServiceImpl implements BookMarkService {
    private final BookMarkRepository bookMarkRepository;

    @Transactional
    @Override
    public boolean toggle(Diary diary, final Long userId) {
        var optionalBookMark = bookMarkRepository.findByUserIdAndDiaryId(userId, diary.getId());
        if(optionalBookMark.isPresent()) {
            return cancelToggle(diary, optionalBookMark.get());
        }
        return saveToggle(diary, userId);
    }

    private boolean saveToggle(Diary diary, Long userId) {
        diary.updateDiaryBookMarkCheck(true);
        var newBookmark = BookMark.of(userId, diary.getId());
        bookMarkRepository.save(newBookmark);
        return true;
    }

    private boolean cancelToggle(Diary diary, BookMark bookMark) {
        bookMarkRepository.delete(bookMark);
        diary.updateDiaryBookMarkCheck(false);
        return false;
    }

    @Override
    @Cacheable(cacheNames = "getBookMarks", key = "'userId:'+#userId+'_'+'pageable.offset:'+#pageable.offset+'_'+'pageable.pageSize:'+#pageable.pageSize", unless = "#result == null")
    public PageCustom<DiaryResQueryDTO> getBookMarks(Pageable pageable, final Long userId) {
        return bookMarkRepository.getBookMarksWithPageable(userId, pageable);
    }

    @Override
    @Transactional
    public void deleteByDiaryId(Long diaryId) {
        var optionalBookMark = bookMarkRepository.findByDiaryId(diaryId);
        if(optionalBookMark.isPresent()) {
            bookMarkRepository.deleteByDiaryId(diaryId);
        }
    }
}
