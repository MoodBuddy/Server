package moodbuddy.moodbuddy.domain.bookMark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.dto.response.BookMarkResToggleDTO;
import moodbuddy.moodbuddy.domain.bookMark.domain.BookMark;
import moodbuddy.moodbuddy.domain.bookMark.mapper.BookMarkMapper;
import moodbuddy.moodbuddy.domain.bookMark.repository.BookMarkRepository;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BookMarkServiceImpl implements BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final BookMarkMapper bookMarkMapper;

    @Transactional
    public BookMarkResToggleDTO toggle(Diary diary, final Long userId) {
        Optional<BookMark> optionalBookMark = bookMarkRepository.findByUserIdAndDiary(userId, diary);
        if(optionalBookMark.isPresent()) {
            return cancelToggle(diary, optionalBookMark.get());
        }
        return saveToggle(diary, userId);
    }

    private BookMarkResToggleDTO saveToggle(Diary diary, Long userId) {
        diary.updateDiaryBookMarkCheck(true);
        BookMark newBookmark = BookMark.of(userId, diary);
        bookMarkRepository.save(newBookmark);
        return bookMarkMapper.toResToggleDTO(true);
    }

    private BookMarkResToggleDTO cancelToggle(Diary diary, BookMark bookMark) {
        bookMarkRepository.delete(bookMark);
        diary.updateDiaryBookMarkCheck(false);
        return bookMarkMapper.toResToggleDTO(false);
    }

    @Override
    public Page<DiaryResDetailDTO> bookMarkFindAllByWithPageable(Pageable pageable, final Long userId) {
        return bookMarkRepository.bookMarkFindAllWithPageable(userId, pageable);
    }

    @Override
    public void deleteByDiaryId(Long diaryId) {
        Optional<BookMark> optionalBookMark = bookMarkRepository.findByDiaryDiaryId(diaryId);
        if(optionalBookMark.isPresent()) {
            bookMarkRepository.deleteByDiaryDiaryId(diaryId);
        }
    }
}
