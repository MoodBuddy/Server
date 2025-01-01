package moodbuddy.moodbuddy.domain.bookMark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.domain.BookMark;
import moodbuddy.moodbuddy.domain.bookMark.repository.BookMarkRepository;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import org.springframework.data.domain.Page;
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
    public Page<DiaryResDetailDTO> getBookMarks(Pageable pageable, final Long userId) {
        return bookMarkRepository.findAllWithPageable(userId, pageable);
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
