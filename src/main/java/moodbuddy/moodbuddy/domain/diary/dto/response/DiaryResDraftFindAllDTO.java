package moodbuddy.moodbuddy.domain.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record DiaryResDraftFindAllDTO (
        @Schema(description = "임시 저장 일기 DTO를 담고 있는 List")
        List<DiaryResDraftFindOneDTO> diaryResDraftFindOneList
){
}
