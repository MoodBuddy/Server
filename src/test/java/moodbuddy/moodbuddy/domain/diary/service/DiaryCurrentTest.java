package moodbuddy.moodbuddy.domain.diary.service;

import moodbuddy.moodbuddy.MoodBuddyApplication;
import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather;
import moodbuddy.moodbuddy.domain.diary.dto.request.update.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.exception.DiaryConcurrentUpdateException;
import moodbuddy.moodbuddy.domain.diary.exception.DiaryNotFoundException;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.domain.draftDiary.domain.DraftDiary;
import moodbuddy.moodbuddy.domain.draftDiary.repository.DraftDiaryRepository;
import moodbuddy.moodbuddy.domain.draftDiary.service.DraftDiaryService;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DiaryCurrentTest {
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private DraftDiaryService draftDiaryService;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private DraftDiaryRepository draftDiaryRepository;

    private Diary diary;
    private DraftDiary draftDiary;
    private Long diaryId;
    private Long draftDiaryId;
    private DiaryReqUpdateDTO requestDTO;

    @BeforeEach
    public void setUp() {
        diary = Diary.builder()
                .title("테스트 일기")
                .date(LocalDate.of(2023, 7, 2))
                .content("일기 내용입니다.")
                .weather(DiaryWeather.CLEAR)
                .userId(1L)
                .font(DiaryFont.INTER)
                .fontSize(DiaryFontSize.PX30)
                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                .build();
        diaryRepository.save(diary);
        diaryId = diary.getId();

        requestDTO = new DiaryReqUpdateDTO(
                diaryId,
                "수정된 제목",
                LocalDate.now(),
                "수정된 내용",
                DiaryWeather.CLOUDY,
                DiaryFont.INTER,
                DiaryFontSize.PX24,
                List.of("imageUrl3", "imageUrl4")
        );
    }

    @Test
    @DisplayName("일기 수정할 때 낙관적 락 동작 테스트")
    public void 일기_수정_낙관적_락_테스트() throws InterruptedException {
        final int threadCount = 5;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    diaryService.updateDiary(1L, requestDTO);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    e.printStackTrace();
                } finally{
                    latch.countDown();
                }
            });
        }

        executorService.shutdown();
        latch.await();

        Diary findDiary = diaryService.findDiaryById(1L, diaryId);
        assertThat(findDiary.getTitle()).isEqualTo(requestDTO.diaryTitle());
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failureCount.get()).isEqualTo(threadCount - 1);
    }

    @Test
    @DisplayName("일기 삭제할 때 동시성 테스트")
    public void 일기_삭제할_때_동시성_테스트 () throws InterruptedException {
        final int threadCount = 5;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    diaryService.deleteDiary(1L, diaryId);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    e.printStackTrace();
                } finally{
                    latch.countDown();
                }
            });
        }

        executorService.shutdown();
        latch.await();

        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        assertThat(findDiary.get().getMoodBuddyStatus()).isEqualTo(MoodBuddyStatus.DIS_ACTIVE);
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failureCount.get()).isEqualTo(threadCount - 1);
    }
}