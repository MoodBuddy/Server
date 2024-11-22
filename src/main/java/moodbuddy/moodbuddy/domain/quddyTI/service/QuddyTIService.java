package moodbuddy.moodbuddy.domain.quddyTI.service;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;

import java.time.LocalDate;
import java.util.Map;

public interface QuddyTIService {
    QuddyTI findByDate(final Long userId, String year, String month);
    void createNewMonthQuddyTI(Long userId, LocalDate currentMonth);
    void updateLastMonthQuddyTI(Long userId, LocalDate start,
                                Map<DiaryEmotion, Long> emotionCounts,
                                Map<DiarySubject, Long> subjectCounts,
                                String quddyTIType);
}