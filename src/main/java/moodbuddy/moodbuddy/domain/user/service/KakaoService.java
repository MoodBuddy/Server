package moodbuddy.moodbuddy.domain.user.service;

import moodbuddy.moodbuddy.domain.user.dto.KakaoProfile;
import moodbuddy.moodbuddy.domain.user.dto.KakaoTokenDto;
import moodbuddy.moodbuddy.domain.user.dto.response.UserResLoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface KakaoService {
    public KakaoTokenDto getKakaoAccessToken(String code);

    public KakaoProfile getUserInfo(String kakaoAccessToken);

    public UserResLoginDTO login(String kakaoAccessToken);
}

