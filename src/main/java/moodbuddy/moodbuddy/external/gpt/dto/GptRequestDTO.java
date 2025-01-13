package moodbuddy.moodbuddy.external.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
// swagger 작성 필요 x
public class GptRequestDTO {
    private String model;
    private List<GptMessageDTO> messages;

    public GptRequestDTO(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new GptMessageDTO("user", prompt));
    }
}
