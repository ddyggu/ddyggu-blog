CREATE PROCEDURE FILL_BOARD()
BEGIN
  DECLARE i INT DEFAULT 1;
  
  WHILE i <= 300 DO
    INSERT INTO board values(i, 'TEST테스트('+i+')', 'TEST테스트('+i+')', 'ddyggu30', now(), 0);
    set i = i + 1;
  END WHILE;
	
END;
