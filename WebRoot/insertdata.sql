DELIMITER $$

USE `gis`$$

DROP PROCEDURE IF EXISTS `insertdata`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertdata`(IN loop_times INT)
BEGIN
	DECLARE var INT DEFAULT 0;  
	DECLARE points DOUBLE DEFAULT 0.000501; 
	DECLARE lat DOUBLE DEFAULT 39.915;
	DECLARE lat1 DOUBLE DEFAULT 39.914105;
	DECLARE lon DOUBLE DEFAULT 116.404;
	DECLARE lon2 DOUBLE DEFAULT 116.28059;
	DECLARE times  DOUBLE DEFAULT 20170321154946;
	WHILE var<loop_times DO  
		SET var=var+1; 
		SET lon=lon+points;  
		SET lon2=lon2+points;
		SET times=times+3; 
		INSERT  INTO `base_info` (`lon`, `lat`, `time`, `car_id`) VALUES(lon,'39.915',times,'京A123454');
		INSERT  INTO `base_info` (`lon`, `lat`, `time`, `car_id`) VALUES(lon2,'39.914105',times,'京A123453');
		SELECT SLEEP(3);
		COMMIT;
	END WHILE;  
    END$$

DELIMITER ;


delete  from base_info where car_id = '京A123454' ;
DELETE  FROM base_info WHERE car_id = '京A123453';
call insertdata(2000); 