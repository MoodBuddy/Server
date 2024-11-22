package moodbuddy.moodbuddy.domain.quddyTI.facade;

import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;

import java.util.List;

public interface QuddyTIFacade {
    List<QuddyTIResDetailDTO> findAll();
    void aggregateAndSaveDiaryData();

}
