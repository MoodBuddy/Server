package moodbuddy.moodbuddy.infra.batch;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryWeather;
import moodbuddy.moodbuddy.domain.diary.repository.DiaryRepository;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFont;
import moodbuddy.moodbuddy.global.common.base.type.DiaryFontSize;
import moodbuddy.moodbuddy.global.common.base.type.MoodBuddyStatus;
import moodbuddy.moodbuddy.global.util.DateUtil;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuddyTIBatchTest {
//    @Autowired
//    private DiaryRepository diaryRepository;
//    @Autowired
//    private QuddyTIBatchJDBCRepository quddyTIBatchJDBCRepository;
//    @Autowired
//    private QuddyTIBatchConfig quddyTIBatchConfig;
//    @Autowired
//    private JobLauncher jobLauncher;
//    @Autowired
//    private Job job;
//
//    @BeforeAll
//    public void setUp() {
//        LocalDate startDate = LocalDate.of(2025, 1, 1);
//        for (int i = 0; i < 20; i++) {
//            diaryRepository.save(Diary.builder()
//                    .userId(1L)
//                    .date(startDate.plusDays(i))
//                    .title("Diary " + i)
//                    .content("Content of Diary " + i)
//                    .weather(DiaryWeather.values()[i % DiaryWeather.values().length])
//                    .emotion(DiaryEmotion.values()[i % DiaryEmotion.values().length])
//                    .subject(DiarySubject.values()[i % DiarySubject.values().length])
//                    .font(DiaryFont.INTER)
//                    .fontSize(DiaryFontSize.PX30)
//                    .moodBuddyStatus(MoodBuddyStatus.ACTIVE)
//                    .build());
//        }
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("QuddyTI 생성 및 업데이트 배치 작업 테스트")
//    public void QuddyTI_생성_및_업데이트_배치_작업_테스트() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("date", LocalDate.now().toString())
//                .toJobParameters();
//        JobExecution execution = jobLauncher.run(job, jobParameters);
//
//        // 배치 작업이 성공적으로 종료되었는지 확인
//        assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
//
//        // QuddyTI 데이터 확인 (생성 및 업데이트 여부)
//        List<QuddyTI> quddyTIs = quddyTIBatchJDBCRepository.findAll();  // 저장된 QuddyTI들 조회
//
//        assertFalse(quddyTIs.isEmpty(), "QuddyTI가 생성되지 않았습니다.");
//
//        // QuddyTI에 대한 검증
//        QuddyTI quddyTI = quddyTIs.get(0); // 첫 번째 QuddyTI에 대해서 검증
//        assertEquals(1L, quddyTI.getUserId());
//        assertEquals(DateUtil.formatYear(LocalDate.now()), quddyTI.getQuddyTIYear());
//        assertEquals(DateUtil.formatMonth(LocalDate.now()), quddyTI.getQuddyTIMonth());
//        assertTrue(quddyTI.getDiaryFrequency() > 0, "Diary frequency가 0입니다.");
//    }
}
