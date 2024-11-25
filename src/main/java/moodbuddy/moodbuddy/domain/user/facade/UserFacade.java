package moodbuddy.moodbuddy.domain.user.facade;

import moodbuddy.moodbuddy.domain.user.dto.request.*;
import moodbuddy.moodbuddy.domain.user.dto.response.*;

import java.time.LocalDate;
import java.util.List;

public interface UserFacade {
    UserResMainPageDTO mainPage();
    UserResCalendarMonthListDTO monthlyCalendar(UserReqCalendarMonthDTO calendarMonthDTO);
    UserResCalendarSummaryDTO summary(UserReqCalendarSummaryDTO calendarSummaryDTO);
    UserResStatisticsMonthDTO getMonthStatic(LocalDate month);
    UserResMonthCommentDTO monthComment(UserReqMonthCommentDTO userReqMonthCommentDTO);
    UserResMonthCommentUpdateDTO monthCommentUpdate(UserReqMonthCommentUpdateDTO userReqMonthCommentUpdateDTO);
    List<UserDiaryNumsDTO> getDiaryNums(LocalDate year);
    List<UserEmotionStaticDTO> getEmotionNums(LocalDate month);
    UserResProfileDTO getUserProfile();
    void scheduleUserMessage(Long kakaoId);
    UserResProfileDTO updateProfile(UserReqProfileUpdateDto updateDto);
    UserResLoginDTO login(UserReqLoginDTO userReqLoginDTO);
    UserResCheckTodayDiaryDTO checkTodayDiary();
}
