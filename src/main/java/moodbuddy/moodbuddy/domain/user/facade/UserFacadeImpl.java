package moodbuddy.moodbuddy.domain.user.facade;

import lombok.RequiredArgsConstructor;
import moodbuddy.moodbuddy.domain.profile.dto.request.ProfileReqUpdateDTO;
import moodbuddy.moodbuddy.domain.profile.dto.response.ProfileResDetailDTO;
import moodbuddy.moodbuddy.domain.user.dto.request.*;
import moodbuddy.moodbuddy.domain.user.dto.response.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFacadeImpl implements UserFacade {
    @Override
    public UserResMainPageDTO mainPage() {
        return null;
    }

    @Override
    public UserResCalendarMonthListDTO monthlyCalendar(UserReqCalendarMonthDTO calendarMonthDTO) {
        return null;
    }

    @Override
    public UserResCalendarSummaryDTO summary(UserReqCalendarSummaryDTO calendarSummaryDTO) {
        return null;
    }

    @Override
    public UserResStatisticsMonthDTO getMonthStatic(LocalDate month) {
        return null;
    }

    @Override
    public UserResMonthCommentDTO monthComment(UserReqMonthCommentDTO userReqMonthCommentDTO) {
        return null;
    }

    @Override
    public UserResMonthCommentUpdateDTO monthCommentUpdate(UserReqMonthCommentUpdateDTO userReqMonthCommentUpdateDTO) {
        return null;
    }

    @Override
    public List<UserDiaryNumsDTO> getDiaryNums(LocalDate year) {
        return List.of();
    }

    @Override
    public List<UserEmotionStaticDTO> getEmotionNums(LocalDate month) {
        return List.of();
    }

    @Override
    public ProfileResDetailDTO getUserProfile() {
        return null;
    }

    @Override
    public void scheduleUserMessage(Long kakaoId) {

    }

    @Override
    public ProfileResDetailDTO updateProfile(ProfileReqUpdateDTO updateDto) {
        return null;
    }

    @Override
    public UserResLoginDTO login(UserReqLoginDTO userReqLoginDTO) {
        return null;
    }

    @Override
    public UserResCheckTodayDiaryDTO checkTodayDiary() {
        return null;
    }
}
