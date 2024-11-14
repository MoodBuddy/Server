package moodbuddy.moodbuddy.domain.user.service;

import moodbuddy.moodbuddy.domain.user.dto.request.*;
import moodbuddy.moodbuddy.domain.user.dto.response.UserResCalendarMonthListDTO;
import moodbuddy.moodbuddy.domain.user.dto.response.UserResCalendarSummaryDTO;
import moodbuddy.moodbuddy.domain.user.dto.response.UserResMainPageDTO;
import moodbuddy.moodbuddy.domain.user.dto.response.*;
import moodbuddy.moodbuddy.domain.user.domain.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    // 메인 화면 이동
    UserResMainPageDTO mainPage();

    // 캘린더 달 이동 (캘린더의 < , > 버튼)
    UserResCalendarMonthListDTO monthlyCalendar(UserReqCalendarMonthDTO calendarMonthDTO);

    // 일기 한 줄 요약 보여주기
    UserResCalendarSummaryDTO summary(UserReqCalendarSummaryDTO calendarSummaryDTO);

    //월별 통계 보기
    UserResStatisticsMonthDTO getMonthStatic(LocalDate month);

    //내 활동 _ 일기 횟수 조회 , 년 + 해당하는 월
    List<UserDiaryNumsDTO> getDiaryNums(LocalDate year);

    //연별 감정 횟수 조회
    List<UserEmotionStaticDTO> getEmotionNums(LocalDate month);

    //프로필 조회
    UserResProfileDTO getUserProfile();

    //프로필 수정
    UserResProfileDTO updateProfile(UserReqProfileUpdateDto dto) throws IOException;

    // 사용자가 설정한 알림 시간에 문자 보내기
    void scheduleUserMessage(Long userId);

    // 다음 달 나에게 짧은 한 마디
    UserResMonthCommentDTO monthComment(UserReqMonthCommentDTO userReqMonthCommentDTO);

    // 다음 달 나에게 짧은 한 마디 수정
    UserResMonthCommentUpdateDTO monthCommentUpdate(UserReqMonthCommentUpdateDTO userReqMonthCommentUpdateDTO);

    // 매월 1이 자정에 자동으로 curDiaryNums 0으로 초기화
    void changeDiaryNums();

    // 이번 달 일기 개수와 편지지 개수 변경
    void changeCount(Long userId, boolean increment);

     void setUserCheckTodayDairy(Long userId, Boolean check);

    /** 오늘 일기 작성한 지 가능 여부 **/
    UserResCheckTodayDiaryDTO checkTodayDiary();

    /** 테스트 (로그인 / 회원가입) **/
    UserResLoginDTO login(UserReqLoginDTO userReqLoginDTO);
    UserResSaveDTO save(UserReqSaveDTO userReqSaveDTO);
    User getUser_Id(Long userId);
}
