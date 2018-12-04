CREATE DATABASE  IF NOT EXISTS `pols` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `pols`;
-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: pols
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Assignments`
--

DROP TABLE IF EXISTS `Assignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Assignments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` int(11) NOT NULL,
  `ass_name` varchar(255) NOT NULL,
  `path` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `due_date` date NOT NULL,
  `publish` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_assignment_class` (`c_id`),
  CONSTRAINT `fk_assignment_class` FOREIGN KEY (`c_id`) REFERENCES `Classes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Assignments`
--

LOCK TABLES `Assignments` WRITE;
/*!40000 ALTER TABLE `Assignments` DISABLE KEYS */;
/*!40000 ALTER TABLE `Assignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Classes`
--

DROP TABLE IF EXISTS `Classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `instructor_id` int(11) NOT NULL,
  `class_name` varchar(45) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Classes`
--

LOCK TABLES `Classes` WRITE;
/*!40000 ALTER TABLE `Classes` DISABLE KEYS */;
INSERT INTO `Classes` VALUES (1,1,'software engineering','2017-02-04','2018-02-03','Created by Frank'),(3,1,'testClass2','2018-01-11','2019-01-01','test2'),(5,1,'testClass','2019-01-01','2011-10-01','test');
/*!40000 ALTER TABLE `Classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Documents`
--

DROP TABLE IF EXISTS `Documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` int(11) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `type` varchar(10) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `publish` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `fileAndClass_unique` (`c_id`,`filename`),
  CONSTRAINT `fk_document_class` FOREIGN KEY (`c_id`) REFERENCES `Classes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Documents`
--

LOCK TABLES `Documents` WRITE;
/*!40000 ALTER TABLE `Documents` DISABLE KEYS */;
INSERT INTO `Documents` VALUES (9,1,'Musics','video','http://wj98127.iteye.com/blog/360644','2018-11-20',1),(13,1,'video','video','https://docs.google.com/document/d/12Q-htIRhtxGYtRnjLwVoP5UyWq1T9y4vzfd6yv_zPZg/edit#','2018-11-21',0),(18,1,'syllabus.html','syllabus','/home/frank/ENPM613/ClassProject/BackEnd/target/POLS/WEB-INF/upload/1/syllabus.html','2018-12-03',1),(31,1,'nodesource-setup.sh','file','/home/frank/ENPM613/ClassProject/BackEnd/target/POLS/WEB-INF/upload/1/nodesource-setup.sh','2018-12-03',1);
/*!40000 ALTER TABLE `Documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Grades`
--

DROP TABLE IF EXISTS `Grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Grades` (
  `u_id` int(11) NOT NULL,
  `a_id` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  PRIMARY KEY (`u_id`,`a_id`),
  KEY `fk_grade_assignment` (`a_id`),
  CONSTRAINT `fk_grade_assignment` FOREIGN KEY (`a_id`) REFERENCES `Assignments` (`id`),
  CONSTRAINT `fk_grade_user` FOREIGN KEY (`u_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Grades`
--

LOCK TABLES `Grades` WRITE;
/*!40000 ALTER TABLE `Grades` DISABLE KEYS */;
/*!40000 ALTER TABLE `Grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User_Class`
--

DROP TABLE IF EXISTS `User_Class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User_Class` (
  `u_id` int(11) NOT NULL,
  `c_id` int(11) NOT NULL,
  PRIMARY KEY (`u_id`,`c_id`),
  KEY `fk_class` (`c_id`),
  CONSTRAINT `fk_class` FOREIGN KEY (`c_id`) REFERENCES `Classes` (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`u_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User_Class`
--

LOCK TABLES `User_Class` WRITE;
/*!40000 ALTER TABLE `User_Class` DISABLE KEYS */;
INSERT INTO `User_Class` VALUES (1,1),(2,1),(12,1),(13,1),(15,1),(20,1);
/*!40000 ALTER TABLE `User_Class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `gender` varchar(6) DEFAULT 'male',
  `birth` date DEFAULT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'student',
  `addr` varchar(45) DEFAULT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `color_blind` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (1,'frank','1111','frank.yu@gmail.com','male',NULL,'instructor','5304 Smiths Cove Ln',100,1),(2,'aaa','122',NULL,'female',NULL,'student',NULL,10,0),(12,'Haonan Yu','940205',NULL,'male',NULL,'student',NULL,0,0),(13,'Haonan','940205',NULL,'male',NULL,'instuctor',NULL,0,0),(14,'Hao','940205',NULL,'male',NULL,'instuctor',NULL,0,0),(15,'MQ','940205',NULL,'male',NULL,'student',NULL,100,0),(20,'Allen','asdas','asda@fas.com','male',NULL,'student','',100,0),(21,'Who is This','dsadas','aksd@aas.com','female',NULL,'student','',100,1);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-03 18:45:54
