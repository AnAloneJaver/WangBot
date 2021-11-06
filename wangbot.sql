/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.32 : Database - wangbot
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wangbot` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `wangbot`;

/*Table structure for table `integral_record` */

DROP TABLE IF EXISTS `integral_record`;

CREATE TABLE `integral_record` (
  `id` varchar(50) NOT NULL,
  `accountcode` varchar(50) NOT NULL,
  `integral` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `integral_record` */

insert  into `integral_record`(`id`,`accountcode`,`integral`) values ('868168204776636416','3558454896',10),('868168802104246272','1773805744',10),('868168906462724096','1903685551',10),('868169164110430208','2543689426',10),('869155608182390784','971366405',10),('906497456869474304','847526310',10);

/*Table structure for table `pics` */

DROP TABLE IF EXISTS `pics`;

CREATE TABLE `pics` (
  `id` varchar(50) NOT NULL,
  `fromcode` varchar(50) DEFAULT NULL,
  `groupcode` varchar(50) DEFAULT NULL,
  `md5` varchar(100) DEFAULT NULL,
  `keyword` varchar(500) DEFAULT NULL,
  `filename` varchar(60) DEFAULT NULL,
  `identification` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `pics` */

insert  into `pics`(`id`,`fromcode`,`groupcode`,`md5`,`keyword`,`filename`,`identification`) values ('869165512024653824','971366405','383102240','d2053ca3371e4a9ee129c5809b2b6240','冰冰是我的','1ab8629dca914ac6b7cfb810fd065682.png','{D2053CA3-371E-4A9E-E129-C5809B2B6240}.png'),('906498283654873088','971366405','935698693','33b86d86650a63d922aeb7dbebbbb344','王冰冰是我的','9292578faad4428193066ae81aecbcf5.png','{33B86D86-650A-63D9-22AE-B7DBEBBBB344}.png');

/*Table structure for table `sign_record` */

DROP TABLE IF EXISTS `sign_record`;

CREATE TABLE `sign_record` (
  `id` varchar(50) NOT NULL,
  `accountcode` varchar(20) DEFAULT NULL,
  `signtime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sign_record` */

insert  into `sign_record`(`id`,`accountcode`,`signtime`) values ('868168198627786752','3558454896','2021-07-23'),('868168799927402496','1773805744','2021-07-23'),('868168906332700672','1903685551','2021-07-23'),('868169164055904256','2543689426','2021-07-23'),('869155606760521728','971366405','2021-07-26'),('869159824770727936','3558454896','2021-07-26'),('869510932605173760','971366405','2021-07-27'),('870713054591975424','971366405','2021-07-30'),('872033006376714240','971366405','2021-08-03'),('875765356218548224','971366405','2021-08-13'),('876757894597443584','971366405','2021-08-16'),('879325783745626112','971366405','2021-08-23'),('887614214359744512','971366405','2021-09-15'),('902971562565566464','971366405','2021-10-27'),('906192263246249984','971366405','2021-11-05'),('906497128702935040','971366405','2021-11-06'),('906497456827531264','847526310','2021-11-06');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
