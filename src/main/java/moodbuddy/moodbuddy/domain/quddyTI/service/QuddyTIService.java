package moodbuddy.moodbuddy.domain.quddyTI.service;

import moodbuddy.moodbuddy.domain.quddyTI.dto.response.QuddyTIResDetailDTO;

import java.util.List;

public interface QuddyTIService {
    void aggregateAndSaveDiaryData();
    List<QuddyTIResDetailDTO> findAll();
}