SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

-- Create Schema
CREATE DATABASE IF NOT EXISTS wine_bar_schema DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE wine_bar_schema;

DROP TABLE IF EXISTS `wine_bar_schema`.`wines` ;


-- Create wines table
CREATE  TABLE IF NOT EXISTS `wine_bar_schema`.`wines` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(45) NOT NULL ,
  `image` VARCHAR(45) NOT NULL,
   PRIMARY KEY (`id`)

 )
 ENGINE = InnoDB;

-- Insert Data
INSERT INTO `wines` (`id`,`name`,`type`,`description`,`image` ) VALUES
 (1,'Kthma Papathanasiou','Rose', 'Excellent, soft and fruity', 'C:\images\papath.jpg');