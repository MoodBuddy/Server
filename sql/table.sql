-- user 테이블 생성
CREATE TABLE user (
                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_role VARCHAR(50) DEFAULT NULL,
                      nickname VARCHAR(50) DEFAULT NULL,
                      kakao_id BIGINT DEFAULT NULL,
                      alarm TINYINT(1) DEFAULT NULL,
                      alarm_time VARCHAR(10) DEFAULT NULL,
                      letter_alarm TINYINT(1) DEFAULT NULL,
                      phone_number VARCHAR(20) DEFAULT NULL,
                      birthday VARCHAR(10) DEFAULT NULL,
                      gender TINYINT(1) DEFAULT NULL,
                      deleted TINYINT(1) DEFAULT NULL,
                      user_cur_diary_nums INT DEFAULT NULL,
                      user_last_diary_nums INT DEFAULT NULL,
                      user_letter_nums INT DEFAULT NULL,
                      check_today_diary TINYINT(1) DEFAULT NULL,
                      created_time DATETIME NOT NULL,
                      updated_time DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- diary 테이블 생성
CREATE TABLE diary (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       diary_title VARCHAR(255) NOT NULL,
                       diary_date DATE NOT NULL,
                       diary_content TEXT NOT NULL,
                       diary_weather ENUM('CLEAR', 'CLOUDY', 'RAIN', 'SNOW') NOT NULL,
                       diary_emotion ENUM('HAPPINESS', 'ANGER', 'DISGUST', 'FEAR', 'NEUTRAL', 'SADNESS', 'SURPRISE') DEFAULT NULL,
                       diary_status ENUM('DRAFT', 'PUBLISHED') NOT NULL,
                       diary_subject ENUM('DAILY', 'GROWTH', 'EMOTION', 'TRAVEL') DEFAULT NULL,
                       diary_summary VARCHAR(255) DEFAULT NULL,
                       user_id BIGINT NOT NULL,
                       diary_book_mark_check TINYINT(1) DEFAULT NULL,
                       diary_font ENUM('INTER', 'MEETME') DEFAULT NULL,
                       diary_font_size ENUM('PX24', 'PX28', 'PX30') DEFAULT NULL,
                       created_time DATETIME NOT NULL,
                       updated_time DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- book_mark 테이블 생성
CREATE TABLE book_mark (
                           book_mark_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT NOT NULL,
                           diary_id BIGINT NOT NULL,
                           created_time DATETIME NOT NULL,
                           updated_time DATETIME NOT NULL,
                           FOREIGN KEY (diary_id) REFERENCES diary(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- diary_image 테이블 생성
CREATE TABLE diary_image (
                             diary_image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             diary_id BIGINT NOT NULL,
                             diary_img_url VARCHAR(255) DEFAULT NULL,
                             created_time DATETIME NOT NULL,
                             updated_time DATETIME NOT NULL,
                             FOREIGN KEY (diary_id) REFERENCES diary(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- letter 테이블 생성
CREATE TABLE letter (
                        letter_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        letter_format INT NOT NULL,
                        worry_content TEXT DEFAULT NULL,
                        answer_content TEXT DEFAULT NULL,
                        letter_date DATETIME NOT NULL,
                        created_time DATETIME NOT NULL,
                        updated_time DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- month_comment 테이블 생성
CREATE TABLE month_comment (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               comment_date VARCHAR(255) DEFAULT NULL,
                               comment_content TEXT DEFAULT NULL,
                               created_time DATETIME NOT NULL,
                               updated_time DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- profile 테이블 생성
CREATE TABLE profile (
                         profile_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         profile_comment VARCHAR(255) DEFAULT NULL,
                         user_id BIGINT DEFAULT NULL,
                         created_time DATETIME NOT NULL,
                         updated_time DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- profile_image 테이블 생성
CREATE TABLE profile_image (
                               profile_image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT DEFAULT NULL,
                               profile_img_url VARCHAR(255) DEFAULT NULL,
                               created_time DATETIME NOT NULL,
                               updated_time DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- quddy_ti 테이블 생성
CREATE TABLE quddy_ti (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          happiness_count INT DEFAULT NULL,
                          anger_count INT DEFAULT NULL,
                          disgust_count INT DEFAULT NULL,
                          fear_count INT DEFAULT NULL,
                          neutral_count INT DEFAULT NULL,
                          sadness_count INT DEFAULT NULL,
                          surprise_count INT DEFAULT NULL,
                          daily_count INT DEFAULT NULL,
                          growth_count INT DEFAULT NULL,
                          emotion_count INT DEFAULT NULL,
                          travel_count INT DEFAULT NULL,
                          quddy_ti_type VARCHAR(10) DEFAULT NULL,
                          created_time DATETIME NOT NULL,
                          updated_time DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE diary_image_seq (
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);