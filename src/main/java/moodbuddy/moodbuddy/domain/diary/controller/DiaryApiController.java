package moodbuddy.moodbuddy.domain.diary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;
import moodbuddy.moodbuddy.domain.diary.facade.DiaryFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/member/diary")
@Tag(name = "Diary", description = "일기 관련 API")
@RequiredArgsConstructor
public class DiaryApiController {
    private final DiaryFacade diaryFacade;

    /** 구현 완료 **/
    @PostMapping("/save")
    @Operation(summary = "일기 작성", description = "새로운 일기를 작성합니다.")
    public ResponseEntity<DiaryResSaveDTO> saveDiary(@Parameter(description = "일기 정보를 담고 있는 DTO")
                                                      @RequestBody DiaryReqSaveDTO requestDTO) {
        return ResponseEntity.ok().body(diaryFacade.saveDiary(requestDTO));
    }

    /** 구현 완료 **/
    @PostMapping("/update")
    @Operation(summary = "일기 수정", description = "기존 일기를 수정합니다.")
    public ResponseEntity<DiaryResSaveDTO> updateDiary(@Parameter(description = "수정된 일기 정보를 담고 있는 DTO")
                                                        @RequestBody DiaryReqUpdateDTO requestDTO) {
        return ResponseEntity.ok().body(diaryFacade.updateDiary(requestDTO));
    }

    /** 구현 완료 **/
    @PostMapping("/delete/{diaryId}")
    @Operation(summary = "일기 삭제", description = "기존 일기를 삭제합니다.")
    public ResponseEntity<?> deleteDiary(@Parameter(description = "일기 고유 식별자")
                                        @PathVariable("diaryId") Long diaryId) {
        diaryFacade.deleteDiary(diaryId);
        return ResponseEntity.ok().body("일기 삭제 완료.");
    }

    /** 구현 완료 **/
    @GetMapping("/{diaryId}")
    @Operation(summary = "일기 하나 조회", description = "일기 하나를 조회합니다.")
    public ResponseEntity<DiaryResDetailDTO> getDiary(@Parameter(description = "일기 고유 식별자")
                                                              @PathVariable("diaryId") Long diaryId) {
        return ResponseEntity.ok().body(diaryFacade.getDiary(diaryId));
    }
}
