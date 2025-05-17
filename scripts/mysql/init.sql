-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafytrip
-- ---------------------------------attractionsattractions--------------------

CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafytrip` ;

-- -----------------------------------------------------
-- Table `ssafytrip`.`sidos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`sidos` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`sidos` (
  `no` int NOT NULL AUTO_INCREMENT  comment '시도번호',
  `sido_code` int NOT NULL comment '시도코드',
  `sido_name` varchar(20) DEFAULT NULL comment '시도이름',
  PRIMARY KEY (`no`),
  UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci comment '시도정보테이블';


-- -----------------------------------------------------
-- Table `ssafytrip`.`guguns`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`guguns` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`guguns` (
  `no` int NOT NULL AUTO_INCREMENT comment '구군번호',
  `sido_code` int NOT NULL comment '시도코드',
  `gugun_code` int NOT NULL comment '구군코드',
  `gugun_name` varchar(20) DEFAULT NULL comment '구군이름',
  PRIMARY KEY (`no`),
  UNIQUE KEY `uq_gugun_code` (`gugun_code`),  -- ✅ 유니크 제약 추가
  INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `ssafytrip`.`sidos` (`sido_code`))
ENGINE = InnoDB
AUTO_INCREMENT = 235
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
comment '구군정보테이블';


-- -----------------------------------------------------
-- Table `ssafytrip`.`contenttypes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`contenttypes` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`contenttypes` (
  `content_type_id` int NOT NULL comment '콘텐츠타입번호',
  `content_type_name` varchar(45) DEFAULT NULL comment '콘텐츠타입이름',
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci comment '콘텐츠타입정보테이블';


-- -----------------------------------------------------
-- Table `ssafytrip`.`attractions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`attractions` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`attractions` (
  `no` int NOT NULL AUTO_INCREMENT  comment '명소코드',
  `content_id` int DEFAULT NULL comment '콘텐츠번호',
  `title` varchar(500) DEFAULT NULL comment '명소이름',
  `content_type_id` int DEFAULT NULL comment '콘텐츠타입',
  `area_code` int DEFAULT NULL comment '시도코드',
  `si_gun_gu_code` int DEFAULT NULL comment '구군코드',
  `first_image1` varchar(100) DEFAULT NULL comment '이미지경로1',
  `first_image2` varchar(100) DEFAULT NULL comment '이미지경로2',
  `map_level` int DEFAULT NULL comment '줌레벨',
  `latitude` decimal(20,17) DEFAULT NULL comment '위도',
  `longitude` decimal(20,17) DEFAULT NULL comment '경도',
  `tel` varchar(20) DEFAULT NULL comment '전화번호',
  `addr1` varchar(100) DEFAULT NULL comment '주소1',
  `addr2` varchar(100) DEFAULT NULL comment '주소2',
  `homepage` varchar(1000) DEFAULT NULL comment '홈페이지',
  `overview` varchar(10000) DEFAULT NULL comment '설명',
  PRIMARY KEY (`no`),
  INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
  INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `attractions_area_to_sidos_code_fk`
    FOREIGN KEY (`area_code`)
    REFERENCES `ssafytrip`.`sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `ssafytrip`.`guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `ssafytrip`.`contenttypes` (`content_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 56644
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
comment '명소정보테이블';

-- -----------------------------------------------------
-- Table `ssafytrip`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`users`;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`users` (
                                                   `no` BIGINT NOT NULL AUTO_INCREMENT COMMENT '사용자 번호',
                                                   `user_id` VARCHAR(50) NOT NULL COMMENT '로그인 아이디',
    `password` VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `user_name` VARCHAR(100) NOT NULL COMMENT '사용자 이름',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '이메일 주소',
    `mbti` VARCHAR(10) DEFAULT NULL COMMENT 'MBTI 성향',
    `job_class_code` VARCHAR(50) NOT NULL COMMENT '직업 코드',
    `join_date` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
    `role` VARCHAR(20) DEFAULT 'USER' COMMENT '권한 (USER, ADMIN)',
    PRIMARY KEY (`no`),
    UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
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
    REFERENCES users(no)
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

-- 사용자
CREATE TABLE IF NOT EXISTS user (
                                    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    mbti VARCHAR(10),
    job_class_code VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_job_class FOREIGN KEY (job_class_code) REFERENCES job_class(code)
    );

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

