-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: tienda_electronica1
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
  `estado` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'pendiente',
  PRIMARY KEY (`id`),
  KEY `idUsuario` (`idUsuario`),
  KEY `idProducto` (`idProducto`),
  CONSTRAINT `carrito_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `carrito_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrito`
--

LOCK TABLES `carrito` WRITE;
/*!40000 ALTER TABLE `carrito` DISABLE KEYS */;
INSERT INTO `carrito` VALUES (7,5,2,1,'2025-07-18 01:08:29','pendiente');
/*!40000 ALTER TABLE `carrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idUsuario` bigint NOT NULL,
  `nombreProducto` varchar(255) DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `estado` varchar(50) DEFAULT 'Pendiente',
  `fecha_pedido` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `imagen_producto` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,4,'Galaxy tab S9 FE',1,'pagado','2025-07-16 19:53:34','https://rymportatiles.com.pe/cdn/shop/files/S9FE-1.png?v=1726871963'),(2,4,'IdeaPad Slim 5i 14\" 8va Gen',1,'pagado','2025-07-16 20:02:16','https://p4-ofp.static.pub/fes/cms/2022/12/05/ahy23s1an7k1aq9irqmlytrunv3280999171.png'),(3,4,'ROG Phone 8 Pro',1,'PAGADO','2025-07-16 20:31:15','https://m.media-amazon.com/images/I/51TypH9fk1L._AC_.jpg'),(4,4,'AirPods Pro 2nd G',1,'pagado','2025-07-16 20:31:40','https://pe.tiendasishop.com/cdn/shop/files/IMG-14912680.jpg?v=1727286009&width=1000'),(5,4,'Galaxy tab S9 FE',1,'PAGADO','2025-07-17 14:25:06','https://rymportatiles.com.pe/cdn/shop/files/S9FE-1.png?v=1726871963'),(6,4,'IdeaPad Slim 5i 14\" 8va Gen',1,'Pagado','2025-07-18 14:09:29','https://p4-ofp.static.pub/fes/cms/2022/12/05/ahy23s1an7k1aq9irqmlytrunv3280999171.png'),(7,4,'Galaxy tab S9 FE',1,'Pagado','2025-07-18 14:10:44','https://rymportatiles.com.pe/cdn/shop/files/S9FE-1.png?v=1726871963'),(8,4,'IdeaPad Slim 5i 14\" 8va Gen',2,'Pagado','2025-07-18 14:10:44','https://p4-ofp.static.pub/fes/cms/2022/12/05/ahy23s1an7k1aq9irqmlytrunv3280999171.png'),(9,4,'AirPods Pro 2nd G',1,'Pagado','2025-07-18 14:10:44','https://pe.tiendasishop.com/cdn/shop/files/IMG-14912680.jpg?v=1727286009&width=1000'),(10,4,'Galaxy tab S9 FE',1,'Pagado','2025-07-18 14:13:20','https://rymportatiles.com.pe/cdn/shop/files/S9FE-1.png?v=1726871963');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `categoria` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `descripcion` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `stock` int NOT NULL,
  `imagen` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `condicion` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Nuevo',
  `envio_gratis` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'IdeaPad Slim 5i 14\" 8va Gen','Laptops','Una Laptop de calidad precio y para mostrar que tienes plata :v',5000.00,40,'https://p4-ofp.static.pub/fes/cms/2022/12/05/ahy23s1an7k1aq9irqmlytrunv3280999171.png','2025-05-15 20:30:00','Nuevo',0),(2,'Galaxy tab S9 FE','Tablets','Un smartphone con excelente cámara y batería de larga duración.',1299.99,10,'https://rymportatiles.com.pe/cdn/shop/files/S9FE-1.png?v=1726871963','2025-05-16 05:58:10','Nuevo',0),(3,'AirPods Pro 2nd G','Audífonos','Unos audifonos de alta calidad para toda mi gente',600.99,25,'https://pe.tiendasishop.com/cdn/shop/files/IMG-14912680.jpg?v=1727286009&width=1000','2025-05-16 06:00:01','Usado',1),(4,'Galaxy S24','Celular','Celular de alta gama para poder hacer lo que quieras',2500.99,25,'https://http2.mlstatic.com/D_NQ_NP_731135-MLA79227832577_092024-O.webp','2025-05-16 06:00:57','Nuevo',0),(5,'ROG Phone 8 Pro','Celular','Celular de alta gama para jugar cualquier videojuego',3500.99,25,'https://m.media-amazon.com/images/I/51TypH9fk1L._AC_.jpg','2025-05-16 06:05:32','Nuevo',0),(7,'Cargador de Iphone Completo ','Accesorios','Cargador de Iphone rapido y funcional',200.00,456,'https://mercury.vtexassets.com/arquivos/ids/5730910/image-e475b07451d24ad68e9a4ac01117aaca.jpg?v=637806579676870000','2025-06-15 00:16:33','Nuevo',0),(8,'Mouse Gamer G502 RGB Hero 25K USB Negro','Accesorios','Para gamers los cuales quieren dar lo mejor de si mismos, el mejor producto que puede haber',289.00,25,'https://www.pcfactory.com.pe/public/foto/1297/1_1000.jpg?t=1729699018150','2025-07-18 13:39:18',NULL,0),(9,'Laptop Samsung Galaxy Book3 Pro de 14 Beige','Laptops','Bueno para hacer trabajos',8079.00,25,'https://oechsle.vteximg.com.br/arquivos/ids/15312583-1000-1000/image-74d0e2a70d6346caa71cbded82fba397.jpg?v=638281822845370000','2025-07-18 13:40:33',NULL,0),(10,'Audífonos Over Ear bluetooth Vincha P9 Azul','Audífonos','Audifonos de calidad para las personas que les gusta la musica',49.00,26,'https://www.lacuracao.pe/media/catalog/product/p/z/pzc951021_1.jpg?quality=80&bg-color=255,255,255&fit=bounds&height=700&width=700&canvas=700:700','2025-07-18 13:43:25',NULL,0);
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
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
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
  `rol_id` bigint NOT NULL,
  PRIMARY KEY (`usuario_id`,`rol_id`),
  KEY `rol_id` (`rol_id`),
  CONSTRAINT `usuario_roles_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  CONSTRAINT `usuario_roles_ibfk_2` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_roles`
--

LOCK TABLES `usuario_roles` WRITE;
/*!40000 ALTER TABLE `usuario_roles` DISABLE KEYS */;
INSERT INTO `usuario_roles` VALUES (4,1),(5,1),(1,2),(4,2);
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
  `recovery_token` varchar(255) DEFAULT NULL,
  `recovery_token_expires_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin','$2a$10$ImEnPWGFrjCYcnE8rleKROMNLbHUDVMATxESZALnFCXZ10Th6EQaO','956531880','jrlobito055@gmail.com',NULL,NULL),(2,'gerardo','$2a$10$kYkOM872gmRJOR6/4tIG4OCGOUBuuJ01scy3x7e3v8jCl6xNAcNDm','923423455','saaul@gmail.com',NULL,NULL),(3,'luis','$2a$10$Tqc.NKGnU8fL1HS7EmMicOQZtb8BKlE5yb9TwPx8jfXBOESiLFtHK','908542689','luisito@gmail.com',NULL,NULL),(4,'anthony','$2a$10$OkOkBVdDhV43rf9mdfWkeu7RiJ0bceKDe70iLo1GAEqJz2Uaphqmu','940267412','subileteanthony@gmail.com',NULL,NULL),(5,'rubi','$2a$10$IbcG/oF3tk37aYEhBsKwWuYJrkpqjlJ/oTIZbR4.kt.kR.DZMsBKS','948513456','anthonysubilete420@gmail.com',NULL,NULL);
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

-- Dump completed on 2025-07-18 10:59:26
