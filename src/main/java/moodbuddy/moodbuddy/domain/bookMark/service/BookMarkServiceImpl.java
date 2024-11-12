package moodbuddy.moodbuddy.domain.bookMark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.dto.response.BookMarkResToggleDTO;
import moodbuddy.moodbuddy.domain.bookMark.domain.BookMark;
import moodbuddy.moodbuddy.domain.bookMark.mapper.BookMarkMapper;
import moodbuddy.moodbuddy.domain.bookMark.repository.BookMarkRepository;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.domain.base.Diary;
import moodbuddy.moodbuddy.domain.diary.service.base.DiaryService;
import moodbuddy.moodbuddy.domain.user.domain.User;
import moodbuddy.moodbuddy.domain.user.service.UserService;
import moodbuddy.moodbuddy.global.common.util.JwtUtil;
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
        if (optionalBookMark.isPresent()) { // 북마크가 존재한다면, 북마크 취소
            bookMarkRepository.delete(optionalBookMark.get());
            diary.updateDiaryBookMarkCheck(false);
            return bookMarkMapper.toResToggleDTO(optionalBookMark.get());
        } else { // 북마크가 존재하지 않는다면, 북마크 저장
            diary.updateDiaryBookMarkCheck(true);
            BookMark newBookmark = BookMark.of(userId, diary);
            bookMarkRepository.save(newBookmark);
            return bookMarkMapper.toResToggleDTO(newBookmark);
        }
    }

    @Override
    public Page<DiaryResDetailDTO> bookMarkFindAllByWithPageable(Pageable pageable, final Long userId) {
        return bookMarkRepository.bookMarkFindAllWithPageable(userId, pageable);
    }
}
