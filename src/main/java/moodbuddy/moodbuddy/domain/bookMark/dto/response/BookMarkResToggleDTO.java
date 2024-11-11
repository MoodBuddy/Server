package moodbuddy.moodbuddy.domain.bookMark.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookMarkResToggleDTO (
        @Schema(description = "북마크 성공(true) / 북마크 취소(false)", example = "true")
        boolean bookmarked

) {
}
