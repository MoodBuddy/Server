-- User 테이블에 '무드버디' 회원 추가
INSERT INTO user (user_role, nickname, kakao_id, alarm, alarm_time, letter_alarm, phone_number, birthday, gender, deleted, user_cur_diary_nums, user_last_diary_nums, user_letter_nums, check_today_diary, created_time, updated_time)
VALUES ('USER', '무드버디', 123456789, 1, '08:00', 1, '010-1234-5678', '1990-01-01', 1, 0, 0, 0, 0, 0, NOW(), NOW());

-- Profile 테이블에 무드버디 프로필 추가
INSERT INTO profile (profile_comment, user_id, created_time, updated_time)
VALUES ('안녕하세요, 무드버디입니다.', (SELECT user_id FROM user WHERE nickname = '무드버디'), NOW(), NOW());

-- ProfileImage 테이블에 무드버디 프로필 이미지 추가
INSERT INTO profile_image (user_id, profile_img_url, created_time, updated_time)
VALUES ((SELECT user_id FROM user WHERE nickname = '무드버디'), 'https://example.com/moodbuddy.jpg', NOW(), NOW());
