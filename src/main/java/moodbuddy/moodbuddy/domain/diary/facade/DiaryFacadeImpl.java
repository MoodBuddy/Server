package moodbuddy.moodbuddy.domain.diary.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.service.BookMarkService;
import moodbuddy.moodbuddy.domain.diary.service.DiaryFindService;
import moodbuddy.moodbuddy.domain.diary.service.DiaryImageService;
import moodbuddy.moodbuddy.domain.diary.service.DiaryService;
import moodbuddy.moodbuddy.domain.user.service.UserService;
import moodbuddy.moodbuddy.global.common.gpt.service.GptService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiaryFacadeImpl implements DiaryFacade {
    private final DiaryService diaryService;
    private final DiaryImageService diaryImageService;
    private final DiaryFindService diaryFindService;
    private final BookMarkService bookMarkService;
    private final UserService userService;
    private final GptService gptService;
}
