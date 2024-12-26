package moodbuddy.moodbuddy.domain.diary.controller.draft;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.save.DiaryResSaveDTO;
import moodbuddy.moodbuddy.domain.diary.facade.draft.DraftDiaryFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/member/draft-diary")
@Tag(name = "Diary", description = "일기 관련 API")
@RequiredArgsConstructor
public class DraftDiaryApiController {
    private final DraftDiaryFacade draftDiaryFacade;

    /** 구현 완료 **/
    @PostMapping("/save")
    @Operation(summary = "일기 임시 저장", description = "일기를 임시 저장합니다.")
    public ResponseEntity<DiaryResSaveDTO> saveDraftDiary(@Parameter(description = "임시 저장 일기 정보를 담고 있는 DTO")
                                                       @RequestBody DiaryReqSaveDTO requestDTO) {
        return ResponseEntity.ok().body(draftDiaryFacade.saveDraftDiary(requestDTO));
    }

    /** 구현 완료 **/
    @PostMapping("/update")
    @Operation(summary = "임시 저장 일기 -> 일기 저장으로 변경", description = "임시 저장 일기 -> 일기 저장으로 변경합니다.")
    public ResponseEntity<DiaryResSaveDTO> updateDraftDiary(@Parameter(description = "변경할 일기 정보를 담고 있는 DTO")
                                                    @RequestBody DiaryReqUpdateDTO requestDTO) {
        return ResponseEntity.ok().body(draftDiaryFacade.updateDraftDiary(requestDTO));
    }

    /** 구현 완료 **/
    @GetMapping("/{diaryId}")
    @Operation(summary = "임시 저장 일기 하나 조회", description = "임시 저장 일기 하나를 조회합니다.")
    public ResponseEntity<DraftDiaryResDetailDTO> getDraftDiary(@Parameter(description = "일기 고유 식별자")
                                                              @PathVariable("diaryId") Long diaryId) {
        return ResponseEntity.ok().body(draftDiaryFacade.getDraftDiary(diaryId));
    }

    /** 구현 완료 **/
    @GetMapping("/")
    @Operation(summary = "임시 저장 일기 목록 조회", description = "임시 저장 일기를 모두 조회합니다.")
    public ResponseEntity<List<DraftDiaryResFindOneDTO>> getDraftDiaries() {
        return ResponseEntity.ok().body(draftDiaryFacade.getDraftDiaries());
    }

    /** 구현 완료 **/
    @PostMapping("/delete")
    @Operation(summary = "임시 저장 일기 선택 삭제", description = "임시 저장 일기를 선택해서 삭제합니다.")
    public ResponseEntity<?> deleteDraftDiaries(@Parameter(description = "삭제할 임시 저장 일기 고유 식별자를 담고 있는 DTO")
                                               @RequestBody DraftDiaryReqSelectDeleteDTO requestDTO) {
        draftDiaryFacade.deleteDraftDiaries(requestDTO);
        return ResponseEntity.ok().body("임시 저장 일기 선택 삭제 완료.");
    }
}
