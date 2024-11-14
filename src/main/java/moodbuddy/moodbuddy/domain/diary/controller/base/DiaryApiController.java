package moodbuddy.moodbuddy.domain.diary.controller.base;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDraftFindAllDTO;
import moodbuddy.moodbuddy.domain.diary.facade.base.DiaryFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/member/diary")
@Tag(name = "Diary", description = "일기 관련 API")
@RequiredArgsConstructor
@Slf4j
public class DiaryApiController {
    private final DiaryFacade diaryFacade;

    /** 구현 완료 **/
    @PostMapping("/save-diary")
    @Operation(summary = "일기 작성", description = "새로운 일기를 작성합니다.")
    public ResponseEntity<DiaryResDetailDTO> save(@Parameter(description = "일기 정보를 담고 있는 DTO")
                                                      @RequestBody DiaryReqSaveDTO requestDTO) {
        DiaryResDetailDTO res = diaryFacade.saveDiary(requestDTO);
        return ResponseEntity.ok().body(res);
    }

    /** 구현 완료 **/
    @PostMapping("/update-diary")
    @Operation(summary = "일기 수정", description = "기존 일기를 수정합니다.")
    public ResponseEntity<DiaryResDetailDTO> update(@Parameter(description = "수정된 일기 정보를 담고 있는 DTO")
                                                        @RequestBody DiaryReqUpdateDTO requestDTO) {
        return ResponseEntity.ok().body(diaryFacade.updateDiary(requestDTO));
    }

    /** 구현 완료 **/
    @PostMapping("/delete/{diaryId}")
    @Operation(summary = "일기 삭제", description = "기존 일기를 삭제합니다.")
    public ResponseEntity<?> delete(@Parameter(description = "일기 고유 식별자")
                                        @PathVariable("diaryId") Long diaryId) {
        diaryFacade.deleteDiary(diaryId);
        return ResponseEntity.ok().body("일기 삭제 완료.");
    }

    /** 구현 완료 **/
    @PostMapping("/save-draft-diary")
    @Operation(summary = "일기 임시 저장", description = "일기를 임시 저장합니다.")
    public ResponseEntity<DiaryResDetailDTO> draftSave(@Parameter(description = "임시 저장 일기 정보를 담고 있는 DTO")
                                           @RequestBody DiaryReqSaveDTO requestDTO) {
        return ResponseEntity.ok().body(diaryFacade.saveDraftDiary(requestDTO));
    }

    /** 구현 완료 **/
    @GetMapping("/draftFindAll")
    @Operation(summary = "임시 저장 일기 목록 조회", description = "임시 저장 일기를 모두 조회합니다.")
    public ResponseEntity<DiaryResDraftFindAllDTO> draftFindAll() {
        return ResponseEntity.ok().body(diaryFacade.draftFindAll());
    }

    /** 구현 완료 **/
    @PostMapping("/draftSelectDelete")
    @Operation(summary = "임시 저장 일기 선택 삭제", description = "임시 저장 일기를 선택해서 삭제합니다.")
    public ResponseEntity<?> draftSelectDelete(@Parameter(description = "삭제할 임시 저장 일기 고유 식별자를 담고 있는 DTO")
                                                   @RequestBody DiaryReqDraftSelectDeleteDTO requestDTO) {
        diaryFacade.draftSelectDelete(requestDTO);
        return ResponseEntity.ok().body("임시 저장 일기 선택 삭제 완료.");
    }
}
