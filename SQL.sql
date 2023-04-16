-- Usuario: root;
-- Contrasenna: restuser


-- MySQL Script generated by MySQL Workbench
-- mié 12 abr 2023 14:26:52 CEST
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Soscorro
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Soscorro` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `Soscorro` ;

-- -----------------------------------------------------
-- Table `Soscorro`.`Usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Soscorro`.`Users` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(45) NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Soscorro`.`Users_has_friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Soscorro`.`Users_has_friends` (
  `userId` INT NOT NULL,
  `friendId` INT NOT NULL,
  PRIMARY KEY (`userId`, `friendId`),
  INDEX `fk_Usuarios_has_Usuarios_Usuarios1_idx` (`friendId` ASC),
  INDEX `fk_Usuarios_has_Usuarios_Usuarios_idx` (`userId` ASC),
  CONSTRAINT `fk_Usuarios_has_Usuarios_Usuarios`
    FOREIGN KEY (`userId`)
    REFERENCES `Soscorro`.`Users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Usuarios_has_Usuarios_Usuarios1`
    FOREIGN KEY (`friendId`)
    REFERENCES `Soscorro`.`Users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Soscorro`.`mensajes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Soscorro`.`Messages` (
  `messageId` INT NOT NULL AUTO_INCREMENT,
  `creatorId` INT NOT NULL,
  `forumId` INT NOT NULL,
  `lastModDate` DATE NOT NULL,
  `creationDate` DATE NOT NULL,
  `content` TEXT,
  PRIMARY KEY (`messageID`),
  INDEX `fk_mensajes_Usuarios1_idx` (`creatorId` ASC),
  INDEX `fk_mensajes_Usuarios2_idx` (`forumId` ASC),
  CONSTRAINT `fk_mensajes_Usuarios1`
    FOREIGN KEY (`creatorId`)
    REFERENCES `Soscorro`.`Users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_mensajes_Usuarios2`
    FOREIGN KEY (`forumId`)
    REFERENCES `Soscorro`.`Users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

INSERT INTO Users (userName)
VALUES 
("Javi"),
("Victor"),
("Andres");

INSERT INTO Users_has_friends (userId, friendId)
VALUES 
(2,1),
(1,3),
(2,3);

INSERT INTO Messages(creatorId,forumId,LastModDate,creationDate,content) 
VALUES 
(1,1,'1999-12-10','1999-12-10','Hola que tal'),
(2,1,'2000-12-11','2000-12-11','Hola buenas tardes'),
(3,1,'2013-11-11','2013-11-11','Buenas tardes');

SELECT * FROM Users;
	

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
