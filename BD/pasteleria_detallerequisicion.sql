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
-- Table structure for table `detallerequisicion`
--

DROP TABLE IF EXISTS `detallerequisicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detallerequisicion` (
  `idDetalleRequisicion` int NOT NULL AUTO_INCREMENT,
  `idRequisicion` int NOT NULL,
  `idIngrediente` int NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `costoUnitario` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idDetalleRequisicion`),
  KEY `idRequisicion` (`idRequisicion`),
  KEY `idIngrediente` (`idIngrediente`),
  CONSTRAINT `detallerequisicion_ibfk_1` FOREIGN KEY (`idRequisicion`) REFERENCES `requisicion` (`idRequisicion`),
  CONSTRAINT `detallerequisicion_ibfk_2` FOREIGN KEY (`idIngrediente`) REFERENCES `ingredientes` (`idIngrediente`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallerequisicion`
--

LOCK TABLES `detallerequisicion` WRITE;
/*!40000 ALTER TABLE `detallerequisicion` DISABLE KEYS */;
INSERT INTO `detallerequisicion` VALUES (1,2,1,22.50,10.00,225.00),(2,2,2,100.00,4.00,400.00),(3,2,11,5.00,25.00,125.00),(4,2,12,5.00,25.00,125.00),(5,2,9,0.25,25.00,6.25),(6,2,6,8.75,24.00,210.00),(7,2,7,7.00,12.00,84.00),(8,2,8,3.50,30.00,105.00),(9,3,1,2.50,10.00,25.00),(10,3,2,14.00,4.00,56.00),(11,3,11,2.00,25.00,50.00),(12,3,12,2.00,25.00,50.00),(13,3,9,0.10,25.00,2.50),(14,3,6,0.25,24.00,6.00),(15,3,7,0.20,12.00,2.40),(16,3,8,0.10,30.00,3.00),(17,4,1,22.50,10.00,225.00),(18,4,2,100.00,4.00,400.00),(19,4,11,5.00,25.00,125.00),(20,4,12,5.00,25.00,125.00),(21,4,9,0.25,25.00,6.25),(22,4,6,8.75,24.00,210.00),(23,4,7,7.00,12.00,84.00),(24,4,8,3.50,30.00,105.00);
/*!40000 ALTER TABLE `detallerequisicion` ENABLE KEYS */;
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
