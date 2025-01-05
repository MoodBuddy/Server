package moodbuddy.moodbuddy.domain.diary.controller.find;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import moodbuddy.moodbuddy.domain.diary.facade.find.DiaryFindFacade;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/member/diary")
@Tag(name = "Diary", description = "일기 관련 API")
@RequiredArgsConstructor
public class DiaryFindApiController {
    private final DiaryFindFacade diaryFindFacade;

    @GetMapping("")
    @Operation(summary = "일기 전체 조회", description = "일기를 모두 조회합니다.")
    public ResponseEntity<PageCustom<DiaryResFindDTO>> getDiaries(Pageable pageable) {
        return ResponseEntity.ok().body(diaryFindFacade.getDiaries(pageable));
    }

    @GetMapping("/emotion")
    @Operation(summary = "일기 감정으로 일기 전체 조회", description = "감정이 똑같은 일기를 모두 조회합니다.")
    public ResponseEntity<PageCustom<DiaryResFindDTO>> getDiariesByEmotion(
            @Parameter(description = "검색하고 싶은 감정(HAPPY, ANGRY, AVERSION, SURPRISED, CALMNESS, DEPRESSION, FEAR)", example = "HAPPY")
            @RequestParam("emotion") DiaryEmotion emotion, Pageable pageable) {
        return ResponseEntity.ok().body(diaryFindFacade.getDiariesByEmotion(emotion, pageable));
    }

    @GetMapping("/filter")
    @Operation(summary = "일기 필터링으로 전체 조회", description = "여러 필터링을 선택하여 일기를 모두 조회합니다.")
    public ResponseEntity<PageCustom<DiaryResFindDTO>> getDiariesByFilter(@Parameter(description = "필터링 데이터를 담고 있는 DTO")
                                                                   @RequestParam(value = "keyWord", required = false) String keyWord,
                                                                   @RequestParam(value = "year", required = false) Integer year,
                                                                   @RequestParam(value = "month", required = false) Integer month,
                                                                   @RequestParam(value = "emotion", required = false) DiaryEmotion emotion,
                                                                   @RequestParam(value = "subject", required = false) DiarySubject subject, Pageable pageable) {
        return ResponseEntity.ok().body(diaryFindFacade.getDiariesByFilter(DiaryReqFilterDTO.of(keyWord, year, month, emotion, subject), pageable));
    }
}
