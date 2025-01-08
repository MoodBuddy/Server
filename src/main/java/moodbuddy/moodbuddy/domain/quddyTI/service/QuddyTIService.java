package moodbuddy.moodbuddy.domain.quddyTI.service;

import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiarySubject;
import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;

import java.time.LocalDate;
import java.util.Map;

public interface QuddyTIService {
    QuddyTI getQuddyTIByDate(final Long userId, String year, String month);
    void createNewMonth(final Long userId, LocalDate currentMonth);
    void processLastMonth(final Long userId, LocalDate[] dateRange, Map<DiaryEmotion, Long> emotionCounts, Map<DiarySubject, Long> subjectCounts);
}