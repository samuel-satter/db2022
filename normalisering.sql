USE iths;

/* UNF */

DROP TABLE IF EXISTS UNF;

CREATE TABLE `UNF` (
    `Id` DECIMAL(38, 0) NOT NULL,
    `Name` VARCHAR(26) NOT NULL,
    `Grade` VARCHAR(11) NOT NULL,
    `Hobbies` VARCHAR(25),
    `City` VARCHAR(10) NOT NULL,
    `School` VARCHAR(30) NOT NULL,
    `HomePhone` VARCHAR(15),
    `JobPhone` VARCHAR(15),
    `MobilePhone1` VARCHAR(15),
    `MobilePhone2` VARCHAR(15)
)  ENGINE=INNODB;

LOAD DATA INFILE '/var/lib/mysql-files/denormalized-data.csv'
INTO TABLE UNF
CHARACTER SET latin1
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

/* Student */
DROP TABLE IF EXISTS Student;

CREATE TABLE Student (
    StudentId INT NOT NULL AUTO_INCREMENT,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY (StudentId)
)  ENGINE=INNODB;

INSERT INTO Student (StudentID, FirstName, LastName) 
SELECT DISTINCT Id, SUBSTRING_INDEX(Name, ' ', 1) AS FirstName, SUBSTRING_INDEX(Name, ' ', -1) AS LastName 
FROM UNF;

/* Phone Numbers */
DROP TABLE IF EXISTS Phone;

CREATE TABLE Phone (
        PhoneId INT NOT NULL AUTO_INCREMENT,
        StudentId INT NOT NULL,
        Type VARCHAR(32),
        Number VARCHAR(32) NOT NULL,
        CONSTRAINT PRIMARY KEY (PhoneId)
) ENGINE=INNODB;

INSERT INTO Phone(StudentId, Type, Number)
SELECT ID As StudentId, "Home" AS Type, HomePhone as Number FROM UNF
WHERE HomePhone IS NOT NULL AND HomePhone != '';

INSERT INTO Phone(StudentId, Type, Number)
SELECT ID As StudentId, "Job" AS Type, JobPhone as Number FROM UNF
WHERE JobPhone IS NOT NULL AND JobPhone != '';
INSERT INTO Phone(StudentId, Type, Number)
SELECT ID As StudentId, "Mobile" AS Type, MobilePhone1 as Number FROM UNF
WHERE MobilePhone1 IS NOT NULL AND MobilePhone1 != '';

INSERT INTO Phone(StudentId, Type, Number)
SELECT ID As StudentId, "Mobile" AS Type, MobilePhone2 as Number FROM UNF
WHERE MobilePhone2 IS NOT NULL AND MobilePhone2 != '';

/* PhoneList view */
DROP VIEW IF EXISTS PhoneList;
CREATE VIEW PhoneList AS SELECT StudentId, group_concat(Number) AS Numbers FROM Phone GROUP BY StudentId;
SELECT FirstName, LastName, Numbers From Student JOIN PhoneList USING (StudentId);


/* School */
DROP TABLE IF EXISTS School;

CREATE TABLE School (
	SchoolId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	School VARCHAR(255),
	City VARCHAR(255)
) ENGINE=INNODB;

INSERT INTO School (School, City) SELECT DISTINCT School, City FROM UNF;

/* StudentSchool */
DROP TABLE IF EXISTS StudentSchool;

CREATE TABLE StudentSchool AS SELECT DISTINCT UNF.Id AS StudentId, School.SchoolId FROM UNF INNER JOIN School USING(School);
ALTER TABLE StudentSchool MODIFY COLUMN StudentId int;
ALTER TABLE StudentSchool MODIFY COLUMN SchoolId int;
ALTER TABLE StudentSchool ADD PRIMARY KEY (StudentId, SchoolId);

/* Hobby */
DROP TABLE IF EXISTS Hobby;

CREATE TABLE Hobby(
	HobbyId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Hobby VARCHAR(255)
) ENGINE=INNODB;

INSERT INTO Hobby(Hobby) 
SELECT DISTINCT SUBSTRING_INDEX(Hobbies, ",", 1) AS Hobby FROM UNF WHERE Hobbies IS NOT NULL AND Hobbies != '' AND Hobbies !="Nothing"
UNION SELECT DISTINCT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(Hobbies, ",", -2), ",", -1)) AS Hobby FROM UNF WHERE Hobbies IS NOT NULL AND Hobbies != '' AND Hobbies NOT LIKE "%Nothing%"
UNION SELECT DISTINCT TRIM(SUBSTRING_INDEX(Hobbies, ",", -1)) AS Hobby FROM UNF WHERE Hobbies IS NOT NULL AND Hobbies != '' AND Hobbies NOT LIKE "%Nothing%";

/* StudentHobby */

DROP TABLE IF EXISTS StudentHobby;

CREATE TABLE StudentHobby(
	HobbyId INT NOT NULL, 
	StudentId INT NOT NULL
)ENGINE=INNODB;

INSERT INTO StudentHobby SELECT DISTINCT Id AS StudentId, HobbyId FROM UNF, Hobby WHERE Hobbies LIKE CONCAT("%",Hobby.Hobby,"%")
