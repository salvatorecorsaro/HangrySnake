-- MySQL Script generated by MySQL Workbench
-- mié 26 feb 2020 20:24:21 CET
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema hangry_snake
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hangry_snake
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hangry_snake` DEFAULT CHARACTER SET latin1 ;
USE `hangry_snake` ;

-- -----------------------------------------------------
-- Table `hangry_snake`.`games`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hangry_snake`.`games` ;

CREATE TABLE IF NOT EXISTS `hangry_snake`.`games` (
  `id_games` INT(11) NOT NULL AUTO_INCREMENT,
  `score` INT(11) NOT NULL DEFAULT '0',
  `usr_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_games`),
  INDEX `usr_id_idx` (`usr_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `hangry_snake`.`settings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hangry_snake`.`settings` ;

CREATE TABLE IF NOT EXISTS `hangry_snake`.`settings` (
  `id_settings` INT(11) NOT NULL AUTO_INCREMENT,
  `background_color` VARCHAR(45) NOT NULL DEFAULT 'black',
  PRIMARY KEY (`id_settings`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `hangry_snake`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hangry_snake`.`users` ;

CREATE TABLE IF NOT EXISTS `hangry_snake`.`users` (
  `usr_id` INT(11) NOT NULL AUTO_INCREMENT,
  `usr` VARCHAR(50) NOT NULL,
  `pwd` VARCHAR(50) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `color` VARCHAR(50) NOT NULL,
  `role` INT(11) NOT NULL,
  PRIMARY KEY (`usr_id`),
  UNIQUE INDEX `users_usr_uindex` (`usr` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;