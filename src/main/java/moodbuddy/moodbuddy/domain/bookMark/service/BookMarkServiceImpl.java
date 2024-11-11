package moodbuddy.moodbuddy.domain.bookMark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.dto.response.BookMarkResToggleDTO;
import moodbuddy.moodbuddy.domain.bookMark.domain.BookMark;
import moodbuddy.moodbuddy.domain.bookMark.repository.BookMarkRepository;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.service.DiaryFindService;
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
    private final UserService userService;
    private final DiaryFindService diaryFindService;

    @Override
    @Transactional
    public BookMarkResToggleDTO toggle(Long diaryId) {
        final Long userId = JwtUtil.getUserId();

        final User findUser = userService.getUser_Id(userId);
        final Diary findDiary = diaryFindService.findDiaryById(diaryId);

        diaryFindService.validateDiaryAccess(findDiary, userId);

        Optional<BookMark> optionalBookMark = bookMarkRepository.findByUserIdAndDiary(findUser.getUserId(), findDiary);

        if(optionalBookMark.isPresent()) { // 북마크가 존재한다면,
            // 북마크 취소
            bookMarkRepository.delete(optionalBookMark.get());
            findDiary.updateDiaryBookMarkCheck(false);
            return new BookMarkResToggleDTO(false);
        } else { // 북마크가 존재하지 않는다면,
            // 북마크 저장
            BookMark newBookMark = BookMark.builder()
                    .userId(findUser.getUserId())
                    .diary(findDiary)
                    .build();
            findDiary.updateDiaryBookMarkCheck(true);
            bookMarkRepository.save(newBookMark);
            return new BookMarkResToggleDTO(true);
        }
    }

    @Override
    public Page<DiaryResDetailDTO> bookMarkFindAllByWithPageable(Pageable pageable) {
        final Long userId = JwtUtil.getUserId();

        return bookMarkRepository.bookMarkFindAllWithPageable(userId, pageable);
    }

    @Override
    public void deleteByDiaryId(Long diaryId) {
        Optional<BookMark> optionalBookMark = bookMarkRepository.findByDiaryId(diaryId);
        if(optionalBookMark.isPresent()) {
            bookMarkRepository.deleteByDiaryId(diaryId);
        }
    }
}
