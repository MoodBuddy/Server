-- User 테이블에 '무드버디' 회원 추가
INSERT INTO user (user_role, nickname, kakao_id, alarm, alarm_time, letter_alarm, phone_number, birthday, gender, deleted, user_cur_diary_nums, user_last_diary_nums, user_letter_nums, check_today_diary, created_time, updated_time)
VALUES ('USER', '무드버디', 123456789, 1, '08:00', 1, '010-1234-5678', '1990-01-01', 1, 0, 0, 0, 0, 0, NOW(), NOW());

-- Profile 테이블에 무드버디 프로필 추가
INSERT INTO profile (profile_comment, user_id, created_time, updated_time)
VALUES ('안녕하세요, 무드버디입니다.', (SELECT user_id FROM user WHERE nickname = '무드버디'), NOW(), NOW());

-- ProfileImage 테이블에 무드버디 프로필 이미지 추가
INSERT INTO profile_image (user_id, profile_img_url, created_time, updated_time)
VALUES ((SELECT user_id FROM user WHERE nickname = '무드버디'), 'https://example.com/moodbuddy.jpg', NOW(), NOW());

-- Quddy_ti 테이블에 무드버디의 감정 카운트 추가
INSERT INTO quddy_ti (user_id, happiness_count, anger_count, disgust_count, fear_count, neutral_count, sadness_count, surprise_count, daily_count, growth_count, emotion_count, travel_count, quddy_ti_type, created_time, updated_time)
VALUES ((SELECT user_id FROM user WHERE nickname = '무드버디'), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NOW(), NOW());

-- Month_comment 테이블에 무드버디의 월간 코멘트 추가
INSERT INTO month_comment (user_id, comment_date, comment_content, created_time, updated_time)
VALUES ((SELECT user_id FROM user WHERE nickname = '무드버디'), '2024-09', '이달은 좋은 일이 많았습니다.', NOW(), NOW());
