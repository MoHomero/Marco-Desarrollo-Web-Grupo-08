-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: tienda_electronica
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `carrito`
--

DROP TABLE IF EXISTS `carrito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrito` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idUsuario` bigint NOT NULL,
  `idProducto` int NOT NULL,
  `cantidad` int NOT NULL DEFAULT '1',
  `fecha_agregado` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `estado` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idUsuario` (`idUsuario`),
  KEY `idProducto` (`idProducto`),
  CONSTRAINT `carrito_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `carrito_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrito`
--

LOCK TABLES `carrito` WRITE;
/*!40000 ALTER TABLE `carrito` DISABLE KEYS */;
INSERT INTO `carrito` VALUES (11,1,1,1,'2025-06-14 23:50:02','pendiente'),(12,1,4,1,'2025-06-15 00:21:36','pendiente'),(13,2,2,3,'2025-07-12 21:06:06','pendiente'),(14,2,1,1,'2025-07-13 23:56:17','pendiente'),(15,2,5,1,'2025-07-14 14:35:50','pendiente'),(16,2,3,3,'2025-07-14 14:36:02','pendiente');
/*!40000 ALTER TABLE `carrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `categoria` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `descripcion` text COLLATE utf8mb4_general_ci NOT NULL,
  `precio` decimal(38,2) DEFAULT NULL,
  `stock` int NOT NULL,
  `imagen` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `condicion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `envio_gratis` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'IdeaPad Slim 5i 14\" 8va Gen','Laptops','Una Laptop de calidad precio y para mostrar que tienes plata :v',5000.00,40,'https://p4-ofp.static.pub/fes/cms/2022/12/05/ahy23s1an7k1aq9irqmlytrunv3280999171.png','2025-05-15 20:30:00','Nuevo',0),(2,'Galaxy tab S9 FE','Tablets','Un smartphone con excelente cámara y batería de larga duración.',1299.99,10,'https://rymportatiles.com.pe/cdn/shop/files/S9FE-1.png?v=1726871963','2025-05-16 05:58:10','Nuevo',0),(3,'AirPods Pro 2nd G','Audífonos','Unos audifonos de alta calidad para toda mi gente',600.99,25,'https://pe.tiendasishop.com/cdn/shop/files/IMG-14912680.jpg?v=1727286009&width=1000','2025-05-16 06:00:01','Usado',1),(4,'Galaxy S24','Celular','Celular de alta gama para poder hacer lo que quieras',2500.99,25,'https://http2.mlstatic.com/D_NQ_NP_731135-MLA79227832577_092024-O.webp','2025-05-16 06:00:57','Nuevo',0),(5,'ROG Phone 8 Pro','Gaming Phones','Celular de alta gama para jugar cualquier videojuego',3500.99,25,'https://m.media-amazon.com/images/I/51TypH9fk1L._AC_.jpg','2025-05-16 06:05:32','Nuevo',0),(7,'Cargador de Iphone Completo ','Accesorios','Cargador de Iphone rapido y funcional',200.00,456,'https://mercury.vtexassets.com/arquivos/ids/5730910/image-e475b07451d24ad68e9a4ac01117aaca.jpg?v=637806579676870000','2025-06-15 00:16:33','Nuevo',0);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'ROLE_ADMIN'),(1,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_roles`
--

DROP TABLE IF EXISTS `usuario_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_roles` (
  `usuario_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`usuario_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `usuario_roles_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  CONSTRAINT `usuario_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_roles`
--

LOCK TABLES `usuario_roles` WRITE;
/*!40000 ALTER TABLE `usuario_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin','81dc9bdb52d04dc20036dbd8313ed055','987654321','usuario1@gmail.com'),(2,'anthony','65fbef05e01fac390cb3fa073fb3e8cf','940267412','anthonysubilete@gmail.com'),(3,'samir','202cb962ac59075b964b07152d234b70','946157842','samir@gmail.com');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-18 10:51:25
