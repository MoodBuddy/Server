package moodbuddy.moodbuddy.domain.diary.controller.find;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.facade.find.DiaryFindFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/member/diary")
@Tag(name = "Diary", description = "일기 관련 API")
@RequiredArgsConstructor
public class DiaryFindApiController {
    private final DiaryFindFacade diaryFindFacade;

    /** 구현 완료 **/
    @GetMapping("")
    @Operation(summary = "일기 전체 조회", description = "일기를 모두 조회합니다.")
    public ResponseEntity<Page<DiaryResDetailDTO>> getDiaries(Pageable pageable) {
        return ResponseEntity.ok().body(diaryFindFacade.getDiaries(pageable));
    }

    /** 구현 완료 **/
    @GetMapping("/emotion")
    @Operation(summary = "일기 감정으로 일기 전체 조회", description = "감정이 똑같은 일기를 모두 조회합니다.")
    public ResponseEntity<Page<DiaryResDetailDTO>> getDiariesByEmotion(
            @Parameter(description = "검색하고 싶은 감정(HAPPY, ANGRY, AVERSION, SURPRISED, CALMNESS, DEPRESSION, FEAR)", example = "HAPPY")
            @RequestParam("diaryEmotion") DiaryEmotion diaryEmotion, Pageable pageable) {
        return ResponseEntity.ok().body(diaryFindFacade.getDiariesByEmotion(diaryEmotion, pageable));
    }

    /** 구현 완료 **/
    @GetMapping("/filter")
    @Operation(summary = "일기 필터링으로 전체 조회", description = "여러 필터링을 선택하여 일기를 모두 조회합니다.")
    public ResponseEntity<Page<DiaryResDetailDTO>> getDiariesByFilter(@Parameter(description = "필터링 데이터를 담고 있는 DTO")
                                                                   @RequestParam(value = "keyWord", required = false) String keyWord,
                                                                   @RequestParam(value = "year", required = false) Integer year,
                                                                   @RequestParam(value = "month", required = false) Integer month,
                                                                   @RequestParam(value = "diaryEmotion", required = false) DiaryEmotion diaryEmotion,
                                                                   @RequestParam(value = "diarySubject", required = false) DiarySubject diarySubject, Pageable pageable) {
        DiaryReqFilterDTO diaryReqFilterDTO = getDiaryReqFilterDTO(keyWord, year, month, diaryEmotion, diarySubject);
        return ResponseEntity.ok().body(diaryFindFacade.getDiariesByFilter(diaryReqFilterDTO, pageable));
    }

    private static DiaryReqFilterDTO getDiaryReqFilterDTO(String keyWord, Integer year, Integer month, DiaryEmotion diaryEmotion, DiarySubject diarySubject) {
        DiaryReqFilterDTO diaryReqFilterDTO = DiaryReqFilterDTO.builder()
                .keyWord(keyWord)
                .year(year)
                .month(month)
                .diaryEmotion(diaryEmotion)
                .diarySubject(diarySubject)
                .build();
        return diaryReqFilterDTO;
    }
}
