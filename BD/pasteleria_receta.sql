CREATE DATABASE  IF NOT EXISTS `pasteleria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pasteleria`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pasteleria
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `receta`
--

DROP TABLE IF EXISTS `receta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receta` (
  `idReceta` int NOT NULL AUTO_INCREMENT,
  `idProducto` int NOT NULL,
  `idIngrediente` int NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idReceta`),
  KEY `idProducto` (`idProducto`),
  KEY `idIngrediente` (`idIngrediente`),
  CONSTRAINT `receta_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `receta_ibfk_2` FOREIGN KEY (`idIngrediente`) REFERENCES `ingredientes` (`idIngrediente`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receta`
--

LOCK TABLES `receta` WRITE;
/*!40000 ALTER TABLE `receta` DISABLE KEYS */;
INSERT INTO `receta` VALUES (1,1,1,1.00),(2,1,2,6.00),(3,1,11,1.00),(4,1,12,1.00),(5,1,9,0.05),(6,2,1,0.50),(7,2,6,0.25),(8,2,7,0.20),(9,2,2,2.00),(10,2,8,0.10),(11,4,1,1.00),(12,4,6,0.50),(13,4,2,6.00),(14,4,8,0.30),(15,4,7,0.20),(16,9,1,0.10),(17,9,6,0.05),(18,9,2,1.00),(19,9,7,0.03),(20,9,9,0.01),(21,15,1,0.30),(22,15,6,0.20),(23,15,2,3.00),(24,15,8,0.25),(25,15,7,0.15),(26,5,1,1.00),(27,5,6,0.40),(28,5,2,6.00),(29,5,9,0.05),(30,5,7,0.20),(31,6,1,0.50),(32,6,6,0.25),(33,6,2,4.00),(34,6,7,0.20),(35,6,8,0.10),(36,7,1,1.00),(37,7,6,0.40),(38,7,2,5.00),(39,7,4,0.50),(40,7,10,0.30),(41,8,1,0.10),(42,8,6,0.05),(43,8,2,1.00),(44,8,8,0.05),(45,10,1,0.40),(46,10,6,0.20),(47,10,7,0.15),(48,10,14,0.10),(49,11,1,0.40),(50,11,7,0.30),(51,11,6,0.15),(52,11,2,2.00),(53,13,2,6.00),(54,13,11,1.00),(55,13,12,1.00),(56,13,9,0.05),(57,14,13,3.00),(58,14,11,1.00),(59,14,12,1.00),(60,16,1,0.60),(61,16,6,0.25),(62,16,2,4.00),(63,16,7,0.15),(64,16,9,0.03),(65,17,1,0.60),(66,17,6,0.25),(67,17,2,4.00),(68,17,8,0.15),(69,17,7,0.15);
/*!40000 ALTER TABLE `receta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-17 10:18:27
