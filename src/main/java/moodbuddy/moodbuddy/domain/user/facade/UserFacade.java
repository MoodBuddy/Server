package moodbuddy.moodbuddy.domain.user.facade;

import moodbuddy.moodbuddy.domain.profile.dto.request.ProfileReqUpdateDTO;
import moodbuddy.moodbuddy.domain.profile.dto.response.ProfileResDetailDTO;
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
    ProfileResDetailDTO getUserProfile();
    void scheduleUserMessage(Long kakaoId);
    ProfileResDetailDTO updateProfile(ProfileReqUpdateDTO updateDto);
    UserResLoginDTO login(UserReqLoginDTO userReqLoginDTO);
    UserResCheckTodayDiaryDTO checkTodayDiary();
}
