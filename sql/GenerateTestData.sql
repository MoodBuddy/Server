
DELIMITER //
CREATE PROCEDURE GenerateTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE j INT;
    DECLARE k INT;
    DECLARE user_id INT;
    DECLARE diary_id INT;  -- 일기 ID 추적을 위한 변수

    -- 1000명의 사용자 생성
    WHILE i <= 1000 DO
        -- user 테이블에 데이터 삽입
        INSERT INTO user (
            user_role, nickname, kakao_id, alarm, alarm_time, letter_alarm, phone_number, birthday, gender, deleted,
            user_cur_diary_nums, user_last_diary_nums, user_letter_nums, check_today_diary, created_time, updated_time
        ) VALUES (
            'USER',
            CONCAT('user_', i), -- 닉네임: user_1, user_2, ...
            1234567890 + i, -- 고유 kakao_id
            true,
            '08:00',
            false,
            CONCAT('010-1234-', LPAD(i, 4, '0')), -- 고유 전화번호
            '1990-01-01',
            true,
            false,
            0,
            0,
            0,
            true,
            NOW(),
            NOW()
        );

        SET user_id = LAST_INSERT_ID();  -- 생성된 사용자 ID를 추적

        -- profile 테이블에 데이터 삽입
INSERT INTO profile (
    profile_comment, user_id, created_time, updated_time
) VALUES (
             CONCAT('This is a sample profile comment for user_', i),
             user_id,
             NOW(),
             NOW()
         );

-- profile_image 테이블에 데이터 삽입
INSERT INTO profile_image (
    user_id, profile_img_url, created_time, updated_time
) VALUES (
             user_id,
             CONCAT('https://example.com/profile_image_', i, '.jpg'),
             NOW(),
             NOW()
         );

-- 30개의 일기 생성
SET j = 1;
        WHILE j <= 50 DO
            -- 일기 데이터 삽입 (일기마다 고유한 diary_id를 추적)
            INSERT INTO diary (
                title, date, content, weather, emotion,
				subject, summary, user_id,
                book_mark, font, font_size, thumbnail, mood_buddy_status, version, created_time, updated_time
            ) VALUES (
                CONCAT('더미 제목_', j),
                DATE_ADD('2024-09-01', INTERVAL j DAY), -- 날짜는 순차적으로
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
                false,
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

            SET diary_id = LAST_INSERT_ID();  -- 방금 삽입된 일기의 ID를 추적

            SET k = 1;
            -- 각 일기에 3개의 이미지 생성
            WHILE k <= 10 DO
                -- 일기 이미지 삽입
                INSERT INTO diary_image (
                    diary_id, image_url, mood_buddy_status, created_time, updated_time
                ) VALUES (
                    diary_id,  -- 현재 일기의 ID
                    CONCAT('https://example.com/diary_image_', i, '_', j, '_', k, '.jpg'),
                    'ACTIVE',
                    NOW(),
                    NOW()
                );
                SET k = k + 1;
END WHILE;
            SET j = j + 1;
END WHILE;
        SET i = i + 1;
END WHILE;
END //
DELIMITER ;

-- 프로시저 실행
CALL GenerateTestData();