-- MariaDB dump 10.19  Distrib 10.4.27-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: java_school_app
-- ------------------------------------------------------
-- Server version	10.4.27-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `gender` enum('male','female') DEFAULT NULL,
  `class` enum('1','2','3','4','5','6') DEFAULT NULL,
  `stage` enum('basic','middle','secondray') DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (3,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(4,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(5,'ooooooooooOOSs','male','3','secondray','kassala - kassala','098872473'),(6,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(7,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(8,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(9,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(10,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(11,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(12,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(13,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(15,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(16,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(17,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(19,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(20,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(21,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(22,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(23,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(24,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(25,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(26,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(27,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(28,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(29,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(31,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(32,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(33,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(34,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(35,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(36,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(37,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(38,'ADSFASDFASDFA','male','3','secondray','kassala - kassala','098872473'),(39,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(40,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(41,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(42,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(43,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(44,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(45,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(46,'Adam Ali Osman','male','4','middle','KSA - Reyad','0972434321'),(47,'Aya ahmed','female','3','basic','ABS','0123456789'),(48,'mohamed anwer lyman','male','3','secondray','kassala - kassala','098872473'),(49,'mozuffer anwer','male','3','secondray','Omdorman','0122792818'),(50,'Ali mohamed ahmed ali','male','1','basic','kassala','0123847734'),(52,'Mesho','female','3','secondray','KSA','014957234');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `subjects_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (1,'math',NULL),(2,'en',NULL),(3,'ar',NULL);
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teachers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `salary` int(11) DEFAULT 0,
  `subject` varchar(255) NOT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`),
  KEY `subject_id` (`subject_id`),
  CONSTRAINT `teachers_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (2,'Khalid aboabuda',15000,'programming methods (2)',NULL,'09816312','K.S.A'),(4,'Mohamed anwer',40000,'Math',NULL,'0188848745','kassala'),(5,'Ali adam',20000,'Database 1',NULL,'0918357345','Omdorman');
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `role` enum('teacher','admin') DEFAULT 'teacher',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (12,'mohamed anwer ali','MoAnwer','','mohamed@gmail.com','teacher'),(20,'ali','ali','1','mohamed@mail.com','teacher'),(21,'ali adam mohamed ahmed','AliAMA','123','ali@mail.com','teacher'),(25,'admin','admin','123456','admin@mail.com','teacher'),(26,'ali','mohamed','1','m#mi','teacher'),(27,'test','test','test','test','teacher'),(28,'testing','testing','123','testing','teacher'),(34,'1','1','1','1','teacher'),(40,'moh','testing1','123456','mail','teacher');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-02 22:41:43
