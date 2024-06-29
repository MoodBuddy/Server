package moodbuddy.moodbuddy.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private String apiKey;
    private String redirectUrl;
    private String clientSecret;
}
