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
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `nombreProducto` varchar(100) NOT NULL,
  `idCategoria` int NOT NULL,
  `tamano` varchar(50) NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `precioVenta` decimal(10,2) NOT NULL,
  `idEstadoProducto` int NOT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `fk_estado_producto` (`idEstadoProducto`),
  KEY `fk_categoria_producto` (`idCategoria`),
  CONSTRAINT `fk_categoria_producto` FOREIGN KEY (`idCategoria`) REFERENCES `categoriaproducto` (`idCategoria`),
  CONSTRAINT `fk_estado_producto` FOREIGN KEY (`idEstadoProducto`) REFERENCES `estadoproducto` (`idEstadoProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Pastel Tres Leches',1,'Mediano','Pastel para 15 personas',455.00,1),(2,'Galletas de chocolate',4,'Chico','Bolsa de 4 Galletas con chispas de chocolates',30.00,1),(4,'Pastel de Chocolate',1,'Grande','Pastel pan de chocolate y decoración de chocolate',380.00,1),(5,'Pastel de Vainilla',1,'Grande','Pastel pan de vainilla, y decoración blanca',350.00,1),(6,'Pastel de Moka',1,'Chico','Pastel cubierto de chocolate',350.00,1),(7,'Pastel Red Velvet',1,'Mediano','Pastel para 12 personas',390.00,1),(8,'Cupcake de Chocolate',2,'Individual','Cubierto de chocolate',20.00,1),(9,'Cupcake de Vainilla',2,'Individual','Cubierto de chantilli',20.00,1),(10,'Galleta de Chispas de chocolate',4,'Individual','Galleta gigante con chispas de chocolate',26.00,1),(11,'Galletas de mantequilla',4,'Chico','Bolsa de 4 galletas de mantequilla',30.00,1),(13,'Flan Napolitano',7,'Individual','Vaso de Flan napolitano',25.00,1),(14,'Gelatina de Mosaico',7,'Individual','Vaso de Gelatina de leche con cubos de gelatina de agua de diferentes sabores',25.00,1),(15,'Brownie',7,'Individual','Rebanada rectangular de brownie',35.00,1),(16,'Panqué de Naranja',6,'Mediano','Barra de panqué sabor Naranja con trocitos de naranja de decoración',65.00,1),(17,'Panqué de chocolate',6,'Mediano','Barra de panqué sabor chocolate con chispas de chocolate como decoración',65.00,1);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-17 10:18:28
