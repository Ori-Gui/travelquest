-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `ssafytrip`;

-- 시도 테이블
DROP TABLE IF EXISTS `sidos`;
CREATE TABLE `sidos` (
                         `no` INT NOT NULL AUTO_INCREMENT COMMENT '시도번호',
                         `sido_code` INT NOT NULL COMMENT '시도코드',
                         `sido_name` VARCHAR(20) DEFAULT NULL COMMENT '시도이름',
                         PRIMARY KEY (`no`),
                         UNIQUE INDEX `sido_code_UNIQUE` (`sido_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='시도정보테이블';

-- 구군 테이블
DROP TABLE IF EXISTS `guguns`;
CREATE TABLE `guguns` (
                          `no` INT NOT NULL AUTO_INCREMENT COMMENT '구군번호',
                          `sido_code` INT NOT NULL COMMENT '시도코드',
                          `gugun_code` INT NOT NULL COMMENT '구군코드',
                          `gugun_name` VARCHAR(20) DEFAULT NULL COMMENT '구군이름',
                          PRIMARY KEY (`no`),
                          UNIQUE KEY `uq_sido_gugun_code` (`sido_code`, `gugun_code`),
                          CONSTRAINT `guguns_sido_to_sidos_cdoe_fk` FOREIGN KEY (`sido_code`) REFERENCES `sidos` (`sido_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='구군정보테이블';

-- 콘텐츠 타입 테이블
DROP TABLE IF EXISTS `contenttypes`;
CREATE TABLE `contenttypes` (
                                `content_type_id` INT NOT NULL COMMENT '콘텐츠타입번호',
                                `content_type_name` VARCHAR(45) DEFAULT NULL COMMENT '콘텐츠타입이름',
                                PRIMARY KEY (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='콘텐츠타입정보테이블';

-- 명소 테이블
DROP TABLE IF EXISTS `attractions`;
CREATE TABLE `attractions` (
                               `no` INT NOT NULL AUTO_INCREMENT COMMENT '명소코드',
                               `content_id` INT DEFAULT NULL COMMENT '콘텐츠번호',
                               `title` VARCHAR(500) DEFAULT NULL COMMENT '명소이름',
                               `content_type_id` INT DEFAULT NULL COMMENT '콘텐츠타입',
                               `area_code` INT DEFAULT NULL COMMENT '시도코드',
                               `si_gun_gu_code` INT DEFAULT NULL COMMENT '구군코드',
                               `first_image1` VARCHAR(100) DEFAULT NULL COMMENT '이미지경로1',
                               `first_image2` VARCHAR(100) DEFAULT NULL COMMENT '이미지경로2',
                               `map_level` INT DEFAULT NULL COMMENT '줌레벨',
                               `latitude` DECIMAL(20,17) DEFAULT NULL COMMENT '위도',
                               `longitude` DECIMAL(20,17) DEFAULT NULL COMMENT '경도',
                               `tel` VARCHAR(20) DEFAULT NULL COMMENT '전화번호',
                               `addr1` VARCHAR(100) DEFAULT NULL COMMENT '주소1',
                               `addr2` VARCHAR(100) DEFAULT NULL COMMENT '주소2',
                               `homepage` VARCHAR(1000) DEFAULT NULL COMMENT '홈페이지',
                               `overview` VARCHAR(10000) DEFAULT NULL COMMENT '설명',
                               PRIMARY KEY (`no`),
                               INDEX `idx_typeid` (`content_type_id`),
                               INDEX `idx_area_code` (`area_code`),
                               INDEX `idx_sigungu_code` (`si_gun_gu_code`),
                               CONSTRAINT `fk_attractions_sido` FOREIGN KEY (`area_code`) REFERENCES `sidos` (`sido_code`),
                               CONSTRAINT `fk_attractions_sigungu` FOREIGN KEY (`area_code`, `si_gun_gu_code`) REFERENCES `guguns` (`sido_code`, `gugun_code`),
                               CONSTRAINT `fk_attractions_contenttype` FOREIGN KEY (`content_type_id`) REFERENCES `contenttypes` (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='명소정보테이블';

-- -----------------------------------------------------
-- Table `ssafytrip`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`users`;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`users` (
   `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '사용자 번호',
    `user_name` VARCHAR(100) NOT NULL COMMENT '사용자 이름',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '이메일 주소',
    `mbti` VARCHAR(10) DEFAULT NULL COMMENT 'MBTI 성향',
    `job_class_code` VARCHAR(50) NOT NULL COMMENT '직업 코드',
    `join_date` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
    `role` VARCHAR(20) DEFAULT 'USER' COMMENT '권한 (USER, ADMIN)',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC),
    CONSTRAINT `fk_users_job_class`
    FOREIGN KEY (`job_class_code`)
    REFERENCES `ssafytrip`.`job_class` (`code`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
    )
    ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci
    COMMENT = '통합 사용자 테이블';


CREATE TABLE IF NOT EXISTS oauth_identity (
  oauth_identity_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'OAuth 식별자',
  sub VARCHAR(255) NOT NULL COMMENT 'OAuth subject (고유 사용자 식별자)',
  provider VARCHAR(50) NOT NULL COMMENT 'OAuth 제공자 (kakao, naver, google 등)',
  user_id BIGINT NOT NULL COMMENT '내부 사용자 ID',
  PRIMARY KEY (oauth_identity_id),
  UNIQUE KEY uq_sub_provider (sub, provider),
  CONSTRAINT fk_oauth_identity_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci
  COMMENT='OAuth 로그인 연동 테이블';

-- 직업군
CREATE TABLE IF NOT EXISTS job_class (
                                         code VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    image_url VARCHAR(500),
    icon_url VARCHAR(500)
    );

INSERT INTO job_class (code, name, description)
VALUES
    ('WARRIOR', '전사', '파티의 선두에서 일정을 이끌고 추진하는 유형'),
    ('MAGE', '마법사', '여행지를 분석하고 최적의 루트를 계산하는 브레인'),
    ('ROGUE', '도적', '분위기를 따라가는 감성 여행자'),
    ('HEALER', '힐러', '분위기를 조율하는 감성형'),
    ('BARD', '바드', '시너지를 내는 흥 많은 캐릭터'),
    ('RANGER', '레인저', '자연을 탐험하고 솔로잉도 선호'),
    ('MECHANIC', '메카닉', '문제 상황에 강한 실전파'),
    ('TRICKSTER', '트릭스터', '사교성 최강자'),
    ('NONE', '없음', '직업 미지정');

-- 유저 던전 기록
CREATE TABLE IF NOT EXISTS user_dungeon_record (
                                                   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                   user_id INT NOT NULL,
                                                   dungeon_id INT NOT NULL,
                                                   party_id INT,
                                                   cleared_at TIMESTAMP NOT NULL,
                                                   result VARCHAR(20),
    score INT,
    CONSTRAINT fk_udr_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_udr_dungeon FOREIGN KEY (dungeon_id) REFERENCES dungeon(id),
    CONSTRAINT fk_udr_party FOREIGN KEY (party_id) REFERENCES party(id)
    );

-- 던전
CREATE TABLE IF NOT EXISTS dungeon (
                                       id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       user_id INT NOT NULL,
                                       sido_code INT NOT NULL,
                                       title VARCHAR(255),
    start_date DATE,
    end_date DATE,
    main_attraction INT,
    max_party_size INT NOT NULL,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_dungeon_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_dungeon_sido FOREIGN KEY (sido_code) REFERENCES sidos(sido_code),
    CONSTRAINT fk_dungeon_main_attraction FOREIGN KEY (main_attraction) REFERENCES attractions(no)
    );

-- 퀘스트
CREATE TABLE IF NOT EXISTS quest (
                                     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     dungeon_id INT NOT NULL,
                                     attraction_id INT NOT NULL,
                                     title VARCHAR(255),
    description TEXT,
    order_index INT,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_quest_dungeon FOREIGN KEY (dungeon_id) REFERENCES dungeon(id),
    CONSTRAINT fk_quest_attraction FOREIGN KEY (attraction_id) REFERENCES attractions(no)
    );

-- 퀘스트 인증
CREATE TABLE IF NOT EXISTS quest_verification (
                                                  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                  quest_id INT NOT NULL,
                                                  party_id INT NOT NULL,
                                                  verified_by_user_id INT,
                                                  photo_url VARCHAR(1000),
    status VARCHAR(20),
    verified_at TIMESTAMP,
    CONSTRAINT fk_verification_quest FOREIGN KEY (quest_id) REFERENCES quest(id),
    CONSTRAINT fk_verification_party FOREIGN KEY (party_id) REFERENCES party(id),
    CONSTRAINT fk_verification_user FOREIGN KEY (verified_by_user_id) REFERENCES user(id)
    );

-- 파티
CREATE TABLE IF NOT EXISTS party (
                                     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     dungeon_id INT NOT NULL,
                                     leader_id INT NOT NULL,
                                     status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_party_dungeon FOREIGN KEY (dungeon_id) REFERENCES dungeon(id),
    CONSTRAINT fk_party_leader FOREIGN KEY (leader_id) REFERENCES user(id)
    );

-- 파티원
CREATE TABLE IF NOT EXISTS party_member (
                                            id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                            party_id INT NOT NULL,
                                            user_id INT NOT NULL,
                                            joined_at TIMESTAMP,
                                            CONSTRAINT fk_member_party FOREIGN KEY (party_id) REFERENCES party(id),
    CONSTRAINT fk_member_user FOREIGN KEY (user_id) REFERENCES user(id)
    );

-- 리뷰
CREATE TABLE IF NOT EXISTS review (
                                      id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      dungeon_id INT NOT NULL,
                                      user_id INT NOT NULL,
                                      party_id INT NOT NULL,
                                      title VARCHAR(255),
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_dungeon FOREIGN KEY (dungeon_id) REFERENCES dungeon(id),
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_review_party FOREIGN KEY (party_id) REFERENCES party(id)
    );

-- 리뷰 이미지
CREATE TABLE IF NOT EXISTS review_image (
                                            id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                            review_id INT NOT NULL,
                                            image_url VARCHAR(1000),
    uploaded_at TIMESTAMP,
    CONSTRAINT fk_review_image_review FOREIGN KEY (review_id) REFERENCES review(id)
    );

-- 채팅방
CREATE TABLE IF NOT EXISTS chat_room (
                                         id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         party_id INT NOT NULL,
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         CONSTRAINT fk_chat_room_party FOREIGN KEY (party_id) REFERENCES party(id)
    );

-- 채팅 메시지
CREATE TABLE IF NOT EXISTS chat_message (
                                            id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                            chat_room_id INT NOT NULL,
                                            user_id INT NOT NULL,
                                            message TEXT,
                                            sent_at TIMESTAMP,
                                            CONSTRAINT fk_chat_message_room FOREIGN KEY (chat_room_id) REFERENCES chat_room(id),
    CONSTRAINT fk_chat_message_user FOREIGN KEY (user_id) REFERENCES user(id)
    );

-- SQL 설정 복원
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

