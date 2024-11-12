package moodbuddy.moodbuddy.domain.diary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.diary.dto.request.*;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDetailDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.DiaryResDraftFindAllDTO;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.DiarySubject;
import moodbuddy.moodbuddy.domain.diary.facade.DiaryFacade;
import moodbuddy.moodbuddy.domain.diary.service.diary.DiaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/member/diary")
@Tag(name = "Diary", description = "일기 관련 API")
@RequiredArgsConstructor
@Slf4j
public class DiaryApiController {
    private final DiaryFacade diaryFacade;
    private final DiaryService diaryService;

    /** 구현 완료 **/
    @PostMapping("/save-diary")
    @Operation(summary = "일기 작성", description = "새로운 일기를 작성합니다.")
    public ResponseEntity<DiaryResDetailDTO> save(@Parameter(description = "일기 정보를 담고 있는 DTO")
                                                      @RequestBody DiaryReqSaveDTO requestDTO) {
        return ResponseEntity.ok().body(diaryFacade.saveDiary(requestDTO));
    }

    /** 구현 완료 **/
    @PostMapping("/update-diary")
    @Operation(summary = "일기 수정", description = "기존 일기를 수정합니다.")
    public ResponseEntity<DiaryResDetailDTO> update(@Parameter(description = "수정된 일기 정보를 담고 있는 DTO")
                                                        @RequestBody DiaryReqUpdateDTO requestDTO) {
        return ResponseEntity.ok().body(diaryFacade.updateDiary(requestDTO));
    }

    /** 구현 완료 **/
    @DeleteMapping("/delete/{diaryId}")
    @Operation(summary = "일기 삭제", description = "기존 일기를 삭제합니다.")
    public ResponseEntity<?> delete(@Parameter(description = "일기 고유 식별자")
                                        @PathVariable("diaryId") Long diaryId) {
        diaryFacade.deleteDiary(diaryId);
        return ResponseEntity.ok().body("일기 삭제 완료.");
    }

    /** 구현 완료 **/
    @PostMapping("/draftSave")
    @Operation(summary = "일기 임시 저장", description = "일기를 임시 저장합니다.")
    public ResponseEntity<DiaryResDetailDTO> draftSave(@Parameter(description = "임시 저장 일기 정보를 담고 있는 DTO")
                                           @ModelAttribute DiaryReqSaveDTO requestDTO) {
        return ResponseEntity.ok().body(diaryFacade.saveDraftDiary(requestDTO));
    }

    /** 구현 완료 **/
    @GetMapping("/draftFindAll")
    @Operation(summary = "임시 저장 일기 목록 조회", description = "임시 저장 일기를 모두 조회합니다.")
    public ResponseEntity<DiaryResDraftFindAllDTO> draftFindAll() {
        return ResponseEntity.ok().body(diaryFacade.draftFindAll());
    }

    /** 구현 완료 **/
    @DeleteMapping("/draftSelectDelete")
    @Operation(summary = "임시 저장 일기 선택 삭제", description = "임시 저장 일기를 선택해서 삭제합니다.")
    public ResponseEntity<?> draftSelectDelete(@Parameter(description = "삭제할 임시 저장 일기 고유 식별자를 담고 있는 DTO")
                                                   @RequestBody DiaryReqDraftSelectDeleteDTO requestDTO) {
        diaryFacade.draftSelectDelete(requestDTO);
        return ResponseEntity.ok().body("임시 저장 일기 선택 삭제 완료.");
    }

    /** 구현 완료 **/
    @GetMapping("/findOne/{diaryId}")
    @Operation(summary = "일기 하나 조회", description = "일기 하나를 조회합니다.")
    public ResponseEntity<DiaryResDetailDTO> findOneByDiaryId(@Parameter(description = "일기 고유 식별자")
                                                  @PathVariable("diaryId") Long diaryId) {
        return ResponseEntity.ok().body(diaryFacade.findOneByDiaryId(diaryId));
    }

    /** 구현 완료 **/
    @GetMapping("/findAllPageable")
    @Operation(summary = "일기 전체 조회", description = "일기를 모두 조회합니다.")
    public ResponseEntity<Page<DiaryResDetailDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(diaryFacade.findAll(pageable));
    }

    /** 구현 완료 **/
    @GetMapping("/findAllByEmotionWithPageable")
    @Operation(summary = "일기 감정으로 일기 전체 조회", description = "감정이 똑같은 일기를 모두 조회합니다.")
    public ResponseEntity<Page<DiaryResDetailDTO>> findAllByEmotion(
            @Parameter(description = "검색하고 싶은 감정(HAPPY, ANGRY, AVERSION, SURPRISED, CALMNESS, DEPRESSION, FEAR)", example = "HAPPY")
            @RequestParam("diaryEmotion") DiaryEmotion diaryEmotion, Pageable pageable) {
        return ResponseEntity.ok().body(diaryFacade.findAllByEmotion(diaryEmotion, pageable));
    }

    /** 구현 완료 **/
    @GetMapping("/findAllByFilter")
    @Operation(summary = "일기 필터링으로 전체 조회", description = "여러 필터링을 선택하여 일기를 모두 조회합니다.")
    public ResponseEntity<Page<DiaryResDetailDTO>> findAllByFilter(@Parameter(description = "필터링 데이터를 담고 있는 DTO")
                                                 @RequestParam(value = "keyWord", required = false) String keyWord,
                                                 @RequestParam(value = "year", required = false) Integer year,
                                                 @RequestParam(value = "month", required = false) Integer month,
                                                 @RequestParam(value = "diaryEmotion", required = false) DiaryEmotion diaryEmotion,
                                                 @RequestParam(value = "diarySubject", required = false) DiarySubject diarySubject, Pageable pageable) {
        DiaryReqFilterDTO diaryReqFilterDTO = getDiaryReqFilterDTO(keyWord, year, month, diaryEmotion, diarySubject);
        return ResponseEntity.ok().body(diaryFacade.findAllByFilter(diaryReqFilterDTO, pageable));
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
