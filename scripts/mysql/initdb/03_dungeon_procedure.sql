SET NAMES utf8mb4;

-- 1) 프로시저 생성
DROP PROCEDURE IF EXISTS create_dungeon_with_attractions;
DELIMITER //
CREATE PROCEDURE create_dungeon_with_attractions(
    IN p_title           VARCHAR(255),
    IN p_start_date      DATE,
    IN p_end_date        DATE,
    IN p_max_party_size  INT,
    IN p_status          VARCHAR(20)
)
BEGIN
    DECLARE v_duration      INT;
    DECLARE v_difficulty    INT;
    DECLARE v_area_code     INT;
    DECLARE v_required_cnt  INT;
    DECLARE v_dungeon_id    INT;

    -- 1. 날짜 차이 계산
    SET v_duration = DATEDIFF(p_end_date, p_start_date);
    -- 2. 난이도 결정
    IF v_duration IN (0,1) THEN
      SET v_difficulty = 1;
      SET v_required_cnt = FLOOR(1 + RAND()*2);          -- 1~2
    ELSEIF v_duration IN (2,3) THEN
      SET v_difficulty = 2;
      SET v_required_cnt = FLOOR(3 + RAND()*2);          -- 3~4
    ELSEIF v_duration IN (4,5) THEN
      SET v_difficulty = 3;
      SET v_required_cnt = FLOOR(5 + RAND()*2);          -- 5~6
    ELSEIF v_duration IN (6,7) THEN
      SET v_difficulty = 4;
      SET v_required_cnt = FLOOR(6 + RAND()*2);          -- 6~7
    ELSEIF v_duration = 8 THEN
      SET v_difficulty = 5;
      SET v_required_cnt = 8;
    ELSE
      SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Duration must be between 0 and 8 days';
    END IF;

    -- 3. 같은 area_code 에서 충분한 개수의 명소가 있는 지역 하나 랜덤 선택
    SELECT area_code
      FROM attractions
     GROUP BY area_code
    HAVING COUNT(*) >= v_required_cnt
     ORDER BY RAND()
     LIMIT 1
    INTO v_area_code;

    IF v_area_code IS NULL THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Not enough attractions for required count';
    END IF;

    -- 4. dungeon 삽입
    INSERT INTO dungeon
      (title, start_date, end_date, difficulty, max_party_size, status, created_at)
    VALUES
      (p_title, p_start_date, p_end_date, v_difficulty, p_max_party_size, p_status, NOW());
    SET v_dungeon_id = LAST_INSERT_ID();

    -- 5. dungeon_attraction 에 랜덤 링크
    INSERT INTO dungeon_attraction (dungeon_id, attraction_id)
    SELECT v_dungeon_id, no
      FROM attractions
     WHERE area_code = v_area_code
     ORDER BY RAND()
     LIMIT v_required_cnt;
    SELECT v_dungeon_id AS dungeonId;
END;
//
DELIMITER ;