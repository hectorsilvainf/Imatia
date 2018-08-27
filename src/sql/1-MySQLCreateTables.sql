

DROP TABLE Task;
DROP TABLE UserProfile;
-- ------------------------------ UserProfile ----------------------------------

CREATE TABLE UserProfile(
	usrId BIGINT NOT NULL AUTO_INCREMENT,
	userName VARCHAR(30) COLLATE latin1_bin NOT NULL,
	enPassword VARCHAR(13) NOT NULL, 
    	CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    	CONSTRAINT UserNameUniqueKey UNIQUE (userName)
)ENGINE = InnoDB;
---------------------------------Task-------------------------------------------


CREATE TABLE Task(
	taskId BIGINT NOT NULL AUTO_INCREMENT,
	usrId BIGINT NOT NULL,
	taskName VARCHAR(100) COLLATE latin1_bin NOT NULL,
	done BIT NOT NULL DEFAULT 0 ,
	
	CONSTRAINT TaskPK PRIMARY KEY(taskId),
	CONSTRAINT UserTaskFK FOREIGN KEY(usrId) REFERENCES UserProfile(usrId)
	
)ENGINE = InnoDB;






