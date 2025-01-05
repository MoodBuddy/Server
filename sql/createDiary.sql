INSERT INTO diary (
    title, date, content, weather, emotion,
    status, subject, summary, user_id,
    book_mark, font, font_size, thumbnail, mood_buddy_status, version, created_time, updated_time
) VALUES
      ('더미 제목 1', '2024-09-01', '더미 내용 1', 'CLEAR', 'HAPPINESS', 'PUBLISHED', 'DAILY', '더미 요약 1', 1, false, 'INTER', 'PX24', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 2', '2024-09-02', '더미 내용 2', 'CLOUDY', 'SADNESS', 'PUBLISHED', 'GROWTH', '더미 요약 2', 1, false, 'MEETME', 'PX28', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 3', '2024-09-03', '더미 내용 3', 'RAIN', 'ANGER', 'PUBLISHED', 'TRAVEL', '더미 요약 3', 1, false, 'INTER', 'PX30', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 4', '2024-09-04', '더미 내용 4', 'SNOW', 'SURPRISE', 'PUBLISHED', 'EMOTION', '더미 요약 4', 1, false, 'MEETME', 'PX24', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 5', '2024-09-05', '더미 내용 5', 'CLEAR', 'HAPPINESS', 'PUBLISHED', 'DAILY', '더미 요약 5', 1, false, 'INTER', 'PX28', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 6', '2024-09-06', '더미 내용 6', 'CLOUDY', 'SADNESS', 'PUBLISHED', 'GROWTH', '더미 요약 6', 1, false, 'MEETME', 'PX30', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 7', '2024-09-07', '더미 내용 7', 'RAIN', 'ANGER', 'PUBLISHED', 'TRAVEL', '더미 요약 7', 1, false, 'INTER', 'PX24', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 8', '2024-09-08', '더미 내용 8', 'SNOW', 'SURPRISE', 'PUBLISHED', 'EMOTION', '더미 요약 8', 1, false, 'MEETME', 'PX28', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 9', '2024-09-09', '더미 내용 9', 'CLEAR', 'HAPPINESS', 'PUBLISHED', 'DAILY', '더미 요약 9', 1, false, 'INTER', 'PX30', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 10', '2024-09-10', '더미 내용 10', 'CLOUDY', 'SADNESS', 'PUBLISHED', 'GROWTH', '더미 요약 10', 1, false, 'MEETME', 'PX24', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 11', '2024-09-11', '더미 내용 11', 'RAIN', 'ANGER', 'PUBLISHED', 'TRAVEL', '더미 요약 11', 1, false, 'INTER', 'PX28', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 12', '2024-09-12', '더미 내용 12', 'SNOW', 'SURPRISE', 'PUBLISHED', 'EMOTION', '더미 요약 12', 1, false, 'MEETME', 'PX30', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 13', '2024-09-13', '더미 내용 13', 'CLEAR', 'HAPPINESS', 'PUBLISHED', 'DAILY', '더미 요약 13', 1, false, 'INTER', 'PX24', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 14', '2024-09-14', '더미 내용 14', 'CLOUDY', 'SADNESS', 'PUBLISHED', 'GROWTH', '더미 요약 14', 1, false, 'MEETME', 'PX28', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 15', '2024-09-15', '더미 내용 15', 'RAIN', 'ANGER', 'PUBLISHED', 'TRAVEL', '더미 요약 15', 1, false, 'INTER', 'PX30', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 16', '2024-09-16', '더미 내용 16', 'SNOW', 'SURPRISE', 'PUBLISHED', 'EMOTION', '더미 요약 16', 1, false, 'MEETME', 'PX24', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 17', '2024-09-17', '더미 내용 17', 'CLEAR', 'HAPPINESS', 'PUBLISHED', 'DAILY', '더미 요약 17', 1, false, 'INTER', 'PX28', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 18', '2024-09-18', '더미 내용 18', 'CLOUDY', 'SADNESS', 'PUBLISHED', 'GROWTH', '더미 요약 18', 1, false, 'MEETME', 'PX30', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 19', '2024-09-19', '더미 내용 19', 'RAIN', 'ANGER', 'PUBLISHED', 'TRAVEL', '더미 요약 19', 1, false, 'INTER', 'PX24', 'thumbnail', 'ACTIVE', 1, NOW(), NOW()),
      ('더미 제목 20', '2024-09-20', '더미 내용 20', 'SNOW', 'SURPRISE', 'PUBLISHED', 'EMOTION', '더미 요약 20', 1, false, 'MEETME', 'PX28', 'thumbnail', 'ACTIVE', 1, NOW(), NOW());

-- diary_image 테이블에 이미지 데이터 삽입
INSERT INTO diary_image (
    diary_id, image_url, mood_buddy_status, created_time, updated_time
) VALUES
      (1, 'https://example.com/diary_image_1.jpg', 'ACTIVE', NOW(), NOW()),
      (2, 'https://example.com/diary_image_2.jpg', 'ACTIVE', NOW(), NOW()),
      (3, 'https://example.com/diary_image_3.jpg', 'ACTIVE', NOW(), NOW()),
      (4, 'https://example.com/diary_image_4.jpg', 'ACTIVE', NOW(), NOW()),
      (5, 'https://example.com/diary_image_5.jpg', 'ACTIVE', NOW(), NOW()),
      (6, 'https://example.com/diary_image_6.jpg', 'ACTIVE', NOW(), NOW()),
      (7, 'https://example.com/diary_image_7.jpg', 'ACTIVE', NOW(), NOW()),
      (8, 'https://example.com/diary_image_8.jpg', 'ACTIVE', NOW(), NOW()),
      (9, 'https://example.com/diary_image_9.jpg', 'ACTIVE', NOW(), NOW()),
      (10, 'https://example.com/diary_image_10.jpg', 'ACTIVE', NOW(), NOW()),
      (11, 'https://example.com/diary_image_11.jpg', 'ACTIVE', NOW(), NOW()),
      (12, 'https://example.com/diary_image_12.jpg', 'ACTIVE', NOW(), NOW()),
      (13, 'https://example.com/diary_image_13.jpg', 'ACTIVE', NOW(), NOW()),
      (14, 'https://example.com/diary_image_14.jpg', 'ACTIVE', NOW(), NOW()),
      (15, 'https://example.com/diary_image_15.jpg', 'ACTIVE', NOW(), NOW()),
      (16, 'https://example.com/diary_image_16.jpg', 'ACTIVE', NOW(), NOW()),
      (17, 'https://example.com/diary_image_17.jpg', 'ACTIVE', NOW(), NOW()),
      (18, 'https://example.com/diary_image_18.jpg', 'ACTIVE', NOW(), NOW()),
      (19, 'https://example.com/diary_image_19.jpg', 'ACTIVE', NOW(), NOW()),
      (20, 'https://example.com/diary_image_20.jpg', 'ACTIVE', NOW(), NOW());