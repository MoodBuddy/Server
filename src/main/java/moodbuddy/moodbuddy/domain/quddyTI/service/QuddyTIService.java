package moodbuddy.moodbuddy.domain.quddyTI.service;

import moodbuddy.moodbuddy.domain.quddyTI.domain.QuddyTI;

public interface QuddyTIService {
    QuddyTI getQuddyTIByDate(final Long userId, String year, String month);
}