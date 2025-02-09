package moodbuddy.moodbuddy.global.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    final static String JWT_SECRET_KEY = "9ba4137437fb8f8e5bfed3477e9efd6f51f12984ee965bd1eef70e344e91d51781391ed49f1e64a33a57df26a1c155e5824a62aeeae73ade60e79a9956ba65cc";

    @Test
    void generateAndPrintJwtFor100Users() {
        // 1부터 100까지의 사용자 ID에 대해 JWT 토큰을 생성하고 출력합니다.
        for (long userId = 1L; userId <= 100; userId++) {
            Date now = new Date();

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", userId);

            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                    .compact();

            System.out.println("User ID: " + userId + " -> JWT Token: " + token);
        }
    }
}