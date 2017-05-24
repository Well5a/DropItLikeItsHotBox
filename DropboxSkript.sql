-- MySQL Script generated by MySQL Workbench
-- Wed May 17 11:01:45 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema dropbox
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dropbox
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dropbox` DEFAULT CHARACTER SET utf8 ;
USE `dropbox` ;

-- -----------------------------------------------------
-- Table `dropbox`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dropbox`.`user` (
  `oId` INT(11) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `passwd` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`oId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `dropbox`.`file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dropbox`.`file` (
  `oId` INT(11) NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  `ownerId` INT NOT NULL,
  PRIMARY KEY (`oId`),
  INDEX `fk_file_user1_idx` (`ownerID` ASC),
  CONSTRAINT `fk_file_user1`
    FOREIGN KEY (`ownerID`)
    REFERENCES `dropbox`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `dropbox`.`filepermission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dropbox`.`filepermission` (
  `oid` INT(11) NOT NULL,
  `allowRead` TINYINT(1) NOT NULL,
  `allowWrite` TINYINT(1) NOT NULL,
  `fileId` INT NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`oid`),
  INDEX `fk_filepermission_file1_idx` (`fileID` ASC),
  INDEX `fk_filepermission_user1_idx` (`userID` ASC),
  CONSTRAINT `fk_filepermission_file1`
    FOREIGN KEY (`fileID`)
    REFERENCES `dropbox`.`file` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_filepermission_user1`
    FOREIGN KEY (`userID`)
    REFERENCES `dropbox`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `dropbox`.`groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dropbox`.`groups` (
  `oId` INT(11) NOT NULL,
  `groupsName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`oId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `dropbox`.`usergroups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dropbox`.`usergroups` (
  `oId` INT(11) NOT NULL,
  `userId` INT(11) NOT NULL,
  `groupsId` INT(11) NOT NULL,
  PRIMARY KEY (`oId`),
  INDEX `fk_Usergroups_user_idx` (`userId` ASC),
  INDEX `fk_Usergroups_groups1_idx` (`groupsId` ASC),
  CONSTRAINT `fk_Usergroups_groups1`
    FOREIGN KEY (`groupsId`)
    REFERENCES `dropbox`.`groups` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usergroups_user`
    FOREIGN KEY (`userId`)
    REFERENCES `dropbox`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
