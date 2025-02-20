package moodbuddy.moodbuddy.domain.bookMark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.bookMark.facade.BookMarkFacade;
import moodbuddy.moodbuddy.domain.diary.dto.response.query.DiaryResQueryDTO;
import moodbuddy.moodbuddy.global.common.base.PageCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/member/book-mark")
@Tag(name = "BookMark", description = "북마크 관련 API")
@RequiredArgsConstructor
public class BookMarkApiController {
    private final BookMarkFacade bookMarkFacade;

    @PostMapping("/toggle/{diaryId}")
    @Operation(summary = "북마크 토글", description = "북마크 토글 북마크 성공(true) / 북마크 취소(false)")
    public ResponseEntity<Boolean> toggle(@Parameter(description = "일기 고유 식별자")
                                  @PathVariable("diaryId") Long diaryId) {
        return ResponseEntity.ok().body(bookMarkFacade.toggle(diaryId));
    }

    @GetMapping("")
    @Operation(summary = "북마크 전체 조회", description = "북마크 전체 조회합니다.")
    public ResponseEntity<PageCustom<DiaryResQueryDTO>> getBookMarks(Pageable pageable) {
        return ResponseEntity.ok().body(bookMarkFacade.getBookMarks(pageable));
    }
}
