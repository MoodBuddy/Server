DELIMITER //

CREATE PROCEDURE GenerateTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE j INT;
    DECLARE k INT;
    DECLARE user_id INT;
    DECLARE diary_id INT;

    WHILE i <= 1000 DO
        -- 사용자 데이터 생성
        INSERT INTO user (
            user_role, nickname, kakao_id, alarm, alarm_time, letter_alarm, phone_number, birthday, gender, deleted,
            user_cur_diary_nums, user_last_diary_nums, user_letter_nums, check_today_diary, created_time, updated_time
        ) VALUES (
            'USER',
            CONCAT('user_', i),
            1234567890 + i,
            TRUE,
            '08:00',
            FALSE,
            CONCAT('010-1234-', LPAD(i, 4, '0')),
            '1990-01-01',
            TRUE,
            FALSE,
            0,
            0,
            0,
            TRUE,
            NOW(),
            NOW()
        );
        SET user_id = LAST_INSERT_ID();

        -- 프로필 데이터 생성
INSERT INTO profile (
    profile_comment, user_id, created_time, updated_time
) VALUES (
             CONCAT('This is a sample profile comment for user_', i),
             user_id,
             NOW(),
             NOW()
         );

-- 프로필 이미지 데이터 생성
INSERT INTO profile_image (
    user_id, profile_img_url, created_time, updated_time
) VALUES (
             user_id,
             CONCAT('https://example.com/profile_image_', i, '.jpg'),
             NOW(),
             NOW()
         );

-- 다이어리 데이터 생성
SET j = 1;
        WHILE j <= 50 DO
            INSERT INTO diary (
                title, date, content, weather, emotion,
                subject, summary, user_id,
                book_mark, font, font_size, thumbnail, mood_buddy_status, version, created_time, updated_time
            ) VALUES (
                CONCAT('더미 제목_', j),
                DATE_ADD('2025-01-01', INTERVAL j DAY),
                CONCAT('더미 내용_', j),
                CASE j % 4
                    WHEN 0 THEN 'CLEAR'
                    WHEN 1 THEN 'CLOUDY'
                    WHEN 2 THEN 'RAIN'
                    ELSE 'SNOW'
                END,
                CASE j % 4
                    WHEN 0 THEN 'HAPPINESS'
                    WHEN 1 THEN 'SADNESS'
                    WHEN 2 THEN 'ANGER'
                    ELSE 'SURPRISE'
                END,
                CASE j % 4
                    WHEN 0 THEN 'DAILY'
                    WHEN 1 THEN 'GROWTH'
                    WHEN 2 THEN 'TRAVEL'
                    ELSE 'EMOTION'
                END,
                CONCAT('더미 요약_', j),
                user_id,
                FALSE,
                CASE j % 2
                    WHEN 0 THEN 'INTER'
                    ELSE 'MEETME'
                END,
                CASE j % 3
                    WHEN 0 THEN 'PX24'
                    WHEN 1 THEN 'PX28'
                    ELSE 'PX30'
                END,
                'thumbnail',
                'ACTIVE',
                0,
                NOW(),
                NOW()
            );
            SET diary_id = LAST_INSERT_ID();

            -- 다이어리 이미지 데이터 생성
            SET k = 1;
            WHILE k <= 10 DO
                INSERT INTO diary_image (
                    diary_id, image_url, mood_buddy_status, created_time, updated_time
                ) VALUES (
                    diary_id,
                    CONCAT('https://example.com/diary_image_', i, '_', j, '_', k, '.jpg'),
                    'ACTIVE',
                    NOW(),
                    NOW()
                );
                SET k = k + 1;
END WHILE;

            SET j = j + 1;
END WHILE;

        -- quddy_ti 데이터 생성
INSERT INTO quddy_ti (
    user_id,
    quddy_ti_year,
    quddy_ti_month,
    diary_frequency,
    daily_count,
    growth_count,
    emotion_count,
    travel_count,
    happiness_count,
    anger_count,
    disgust_count,
    fear_count,
    neutral_count,
    sadness_count,
    surprise_count,
    quddy_ti_type,
    mood_buddy_status,
    created_time,
    updated_time
)
VALUES (
           user_id,
           '2025' AS quddy_ti_year,
           '02' AS quddy_ti_month,
           0 AS diary_frequency,
           0 AS daily_count,
           0 AS growth_count,
           0 AS emotion_count,
           0 AS travel_count,
           0 AS happiness_count,
           0 AS anger_count,
           0 AS disgust_count,
           0 AS fear_count,
           0 AS neutral_count,
           0 AS sadness_count,
           0 AS surprise_count,
           NULL AS quddy_ti_type,
           'DIS_ACTIVE' AS mood_buddy_status,
           NOW() AS created_time,
           NOW() AS updated_time
       );

SET i = i + 1;
END WHILE;
END //

DELIMITER ;

-- 호출
CALL GenerateTestData();