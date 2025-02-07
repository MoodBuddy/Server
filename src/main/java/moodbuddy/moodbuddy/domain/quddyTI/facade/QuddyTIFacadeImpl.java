package moodbuddy.moodbuddy.domain.quddyTI.facade;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.mapper.QuddyTIMapper;
import moodbuddy.moodbuddy.domain.quddyTI.service.QuddyTIService;
import moodbuddy.moodbuddy.global.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuddyTIFacadeImpl implements QuddyTIFacade {
    private final QuddyTIService quddyTIService;
    private final QuddyTIMapper quddyTIMapper;

    @Override
    public QuddyTIResDetailDTO getQuddyTIByDate(String year, String month) {
        final Long userId = JwtUtil.getUserId();
        return quddyTIMapper.toResDetailDTO(
                quddyTIService.getQuddyTIByDate(userId, year, month)
        );
    }
}