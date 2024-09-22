package moodbuddy.moodbuddy.domain.quddyTI.service;

import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;
import moodbuddy.moodbuddy.domain.quddyTI.entity.QuddyTI;

import java.util.List;

public interface QuddyTIService {
    void aggregateAndSaveDiaryData();
    void saveQuddyTI(QuddyTI quddyTI);
    List<QuddyTIResDetailDTO> findAll();
}