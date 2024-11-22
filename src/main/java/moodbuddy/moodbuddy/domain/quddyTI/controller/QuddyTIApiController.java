package moodbuddy.moodbuddy.domain.quddyTI.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.facade.QuddyTIFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/member/quddyTI")
@Tag(name = "QuddyTI", description = "쿼디티아이 관련 API")
@RequiredArgsConstructor
@Slf4j
public class QuddyTIApiController {
    private final QuddyTIFacade quddyTIFacade;

    /** 구현 완료 **/
    @GetMapping("/findByDate")
    @Operation(summary = "쿼디티아이 날짜 별 조회", description = "쿼디티아이를 날짜 별로 조회합니다.")
    public ResponseEntity<QuddyTIResDetailDTO> findByDate(
            @RequestParam("year") String year,
            @RequestParam("month") String month
    ) {
        return ResponseEntity.ok().body(quddyTIFacade.findByDate(year, month));
    }
}
