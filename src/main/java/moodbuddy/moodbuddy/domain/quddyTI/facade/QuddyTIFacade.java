package moodbuddy.moodbuddy.domain.quddyTI.facade;

import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;

public interface QuddyTIFacade {
    QuddyTIResDetailDTO getQuddyTI(String year, String month);
    void createAndUpadteQuddyTI();

}
