SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS PLAT_SYSTEM.USER;




/* Create Tables */

CREATE TABLE PLAT_SYSTEM.USER
(
	ID varchar(50) NOT NULL,
	NAME varchar(50) NOT NULL,
	PASSWORD varchar(50) NOT NULL,
	PHONE varchar(50),
	EMAIL varchar(50),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


