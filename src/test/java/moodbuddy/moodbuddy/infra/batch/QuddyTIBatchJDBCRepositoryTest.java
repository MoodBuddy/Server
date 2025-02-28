package moodbuddy.moodbuddy.infra.batch;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.domain.quddyTI.repository.QuddyTIRepository;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class QuddyTIBatchJDBCRepositoryTest {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private QuddyTIRepository quddyTIRepository;
    @Autowired
    private QuddyTIBatchJDBCRepository quddyTIBatchJDBCRepository;

    @BeforeEach
    @DisplayName("setUp")
    public void setUp() {
        for (int i = 0; i < 10; i++) {
            diaryRepository.save(Diary.builder()
                    .userId(1L)
                    .date(LocalDate.now().minusDays(i))
                    .title("Diary " + i)
                    .content("Content of Diary " + i)
                    .weather(DiaryWeather.SNOW)
                    .emotion(DiaryEmotion.HAPPINESS)
                    .subject(DiarySubject.DAILY)
                    .font(DiaryFont.INTER)
                    .fontSize(DiaryFontSize.PX30)
                    .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
                    .build());
        }
//        quddyTIRepository.save(QuddyTI.builder()
//                .userId(1L)
//                .year("2025")
//                .month("01")
//                .diaryFrequency(2)
//                .dailyCount(1)
//                .growthCount(1)
//                .emotionCount(2)
//                .travelCount(0)
//                .happinessCount(1)
//                .angerCount(0)
//                .disgustCount(0)
//                .fearCount(0)
//                .neutralCount(0)
//                .sadnessCount(1)
//                .surpriseCount(0)
//                .quddyTI("TEST")
//                .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
//                .build());
    }

    @Test
    @DisplayName("QuddyTI 찾기 테스트")
    public void QuddyTI_찾기_테스트() {
        QuddyTI quddyTI = quddyTIBatchJDBCRepository.findQuddyTIByUserIdAndDate(1L, "2025", "01");
        assertNotNull(quddyTI);
        assertEquals(1L, quddyTI.getUserId());
        assertEquals("2025", quddyTI.getYear());
        assertEquals("01", quddyTI.getMonth());
        assertEquals("TEST", quddyTI.getQuddyTI());
    }

    @Test
    @DisplayName("한 달 기준 일기 감정 갯수 세기 테스트")
    public void 한_달_기준_일기_감정_갯수_세기_테스트() {
        LocalDate start = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);
        long happinessCount = quddyTIBatchJDBCRepository.findEmotionCountsByDate(DiaryEmotion.HAPPINESS, start, end);
        assertEquals(10L, happinessCount);
        long sadnessCount = quddyTIBatchJDBCRepository.findEmotionCountsByDate(DiaryEmotion.SADNESS, start, end);
        assertEquals(0L, sadnessCount);

    }

    @Test
    @DisplayName("한 달 기준 일기 주제 갯수 세기 테스트")
    public void 한_달_기준_일기_주제_갯수_세기_테스트() {
        LocalDate start = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);
        long dailyCount = quddyTIBatchJDBCRepository.findSubjectCountsByDate(DiarySubject.DAILY, start, end);
        assertEquals(10L, dailyCount);
        long travelCount = quddyTIBatchJDBCRepository.findSubjectCountsByDate(DiarySubject.TRAVEL, start, end);
        assertEquals(0L, travelCount);
    }
}