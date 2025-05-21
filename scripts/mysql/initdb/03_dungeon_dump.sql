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
END;
//
DELIMITER ;

  -- duration=0 → difficulty=1, 1~2개 명소
CALL create_dungeon_with_attractions('던전 테스트 1', '2025-07-01', '2025-07-01', 4, 'OPEN'); 
  -- duration=2 → difficulty=2, 3~4개 명소
CALL create_dungeon_with_attractions('던전 테스트 2', '2025-07-10', '2025-07-12', 3, 'OPEN');
  -- duration=4 → difficulty=3, 5~6개 명소
CALL create_dungeon_with_attractions('던전 테스트 3', '2025-08-01', '2025-08-05', 5, 'OPEN');
  -- duration=7 → difficulty=4, 6~7개 명소
CALL create_dungeon_with_attractions('던전 테스트 4', '2025-06-20', '2025-06-27', 6, 'OPEN');
  -- duration=8 → difficulty=5, 8개 명소
CALL create_dungeon_with_attractions('던전 테스트 5', '2025-09-01', '2025-09-09', 8, 'OPEN'); 

CALL create_dungeon_with_attractions('던전 테스트 6',  '2025-06-01', '2025-06-01', 4, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 7',  '2025-06-03', '2025-06-04', 5, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 8',  '2025-06-05', '2025-06-07', 3, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 9',  '2025-06-08', '2025-06-11', 6, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 10', '2025-06-12', '2025-06-16', 2, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 11', '2025-06-17', '2025-06-22', 7, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 12', '2025-06-23', '2025-06-29', 8, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 13', '2025-06-25', '2025-07-02', 5, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 14', '2025-07-05', '2025-07-13', 4, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 15', '2025-07-15', '2025-07-16', 3, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 16', '2025-07-18', '2025-07-20', 6, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 17', '2025-07-22', '2025-07-25', 7, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 18', '2025-07-26', '2025-07-30', 2, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 19', '2025-07-31', '2025-08-05', 4, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 20', '2025-08-06', '2025-08-12', 8, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 21', '2025-08-10', '2025-08-17', 5, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 22', '2025-08-18', '2025-08-26', 7, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 23', '2025-08-28', '2025-08-28', 3, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 24', '2025-08-29', '2025-08-31', 6, 'OPEN');
CALL create_dungeon_with_attractions('던전 테스트 25', '2025-09-01', '2025-09-04', 4, 'OPEN');