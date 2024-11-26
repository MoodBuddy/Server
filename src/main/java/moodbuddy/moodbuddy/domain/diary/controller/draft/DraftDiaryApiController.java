package moodbuddy.moodbuddy.domain.diary.controller.draft;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqUpdateDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.draft.DraftDiaryReqSelectDeleteDTO;
import moodbuddy.moodbuddy.domain.diary.dto.request.DiaryReqSaveDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.draft.DraftDiaryResFindOneDTO;
import moodbuddy.moodbuddy.domain.diary.facade.draft.DraftDiaryFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/member/draftDiary")
@Tag(name = "Diary", description = "일기 관련 API")
@RequiredArgsConstructor
public class DraftDiaryApiController {
    private final DraftDiaryFacade draftDiaryFacade;

    /** 구현 완료 **/
    @PostMapping("/save")
    @Operation(summary = "일기 임시 저장", description = "일기를 임시 저장합니다.")
    public ResponseEntity<DiaryResDetailDTO> save(@Parameter(description = "임시 저장 일기 정보를 담고 있는 DTO")
                                                       @RequestBody DiaryReqSaveDTO requestDTO) {
        return ResponseEntity.ok().body(draftDiaryFacade.save(requestDTO));
    }

    /** 구현 완료 **/
    @PostMapping("/update")
    @Operation(summary = "임시 저장 일기 -> 일기 저장으로 변경", description = "임시 저장 일기 -> 일기 저장으로 변경합니다.")
    public ResponseEntity<DiaryResDetailDTO> update(@Parameter(description = "변경할 일기 정보를 담고 있는 DTO")
                                                    @RequestBody DiaryReqUpdateDTO requestDTO) {
        return ResponseEntity.ok().body(draftDiaryFacade.update(requestDTO));
    }

    /** 구현 완료 **/
    @GetMapping("/findOne/{diaryId}")
    @Operation(summary = "임시 저장 일기 하나 조회", description = "임시 저장 일기 하나를 조회합니다.")
    public ResponseEntity<DraftDiaryResDetailDTO> findOneByDiaryId(@Parameter(description = "일기 고유 식별자")
                                                              @PathVariable("diaryId") Long diaryId) {
        return ResponseEntity.ok().body(draftDiaryFacade.findOneByDiaryId(diaryId));
    }

    /** 구현 완료 **/
    @GetMapping("/findAll")
    @Operation(summary = "임시 저장 일기 목록 조회", description = "임시 저장 일기를 모두 조회합니다.")
    public ResponseEntity<List<DraftDiaryResFindOneDTO>> findAll() {
        return ResponseEntity.ok().body(draftDiaryFacade.findAll());
    }

    /** 구현 완료 **/
    @PostMapping("/selectDelete")
    @Operation(summary = "임시 저장 일기 선택 삭제", description = "임시 저장 일기를 선택해서 삭제합니다.")
    public ResponseEntity<?> selectDelete(@Parameter(description = "삭제할 임시 저장 일기 고유 식별자를 담고 있는 DTO")
                                               @RequestBody DraftDiaryReqSelectDeleteDTO requestDTO) {
        draftDiaryFacade.selectDelete(requestDTO);
        return ResponseEntity.ok().body("임시 저장 일기 선택 삭제 완료.");
    }
}
