package moodbuddy.moodbuddy.external.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// swagger 작성 필요 x
public class GptMessageDTO {
    private String role;
    private String content;
}
