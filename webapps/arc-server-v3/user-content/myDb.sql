-- MySQL dump 10.13  Distrib 5.1.63, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: BaseDeDatos
-- ------------------------------------------------------
-- Server version	5.1.63-0ubuntu0.10.04.1

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
-- Table structure for table `Message`
--

DROP TABLE IF EXISTS `Message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `tablon_id` int(11) NOT NULL,
  `message` varchar(250) DEFAULT NULL,
  `format` binary(1) DEFAULT NULL,
  `visibility` binary(1) DEFAULT NULL,
  `dateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `tablon_id` (`tablon_id`),
  CONSTRAINT `Message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Message_ibfk_2` FOREIGN KEY (`tablon_id`) REFERENCES `Tablon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=543 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Message`
--

LOCK TABLES `Message` WRITE;
/*!40000 ALTER TABLE `Message` DISABLE KEYS */;
INSERT INTO `Message` VALUES (470,89,4,'Bienvenidos al tablón de opiniones de la install party JRO. Puntúa las jornada,vota tu actividad favorita y escribe tus comentarios. iDisfrutad el evento!','0','0','2013-01-25 23:40:23'),(471,92,4,'Impartir el Taller de Blender. ','0','0','2013-01-26 09:49:38'),(472,90,4,'Me gusta la ponencia: Mitos y realidades del uso del software libre.','0','0','2013-01-26 09:56:42'),(473,94,4,'es interesante','0','0','2013-01-26 10:33:16'),(474,94,4,'muy buen trabajo','0','0','2013-01-26 10:34:35'),(475,94,4,'muy bien trabajado ','0','0','2013-01-26 10:35:39'),(476,95,4,'esta muy bien','0','0','2013-01-26 10:56:04'),(477,96,4,'Me gusta el taller: Fotografía libre. muy interesantes y chulas las fotos hdr','0','0','2013-01-26 10:57:26'),(478,95,4,'Mi actividad favorita es: Fotomontajes con GIMP.','0','0','2013-01-26 10:58:09'),(479,97,4,'Me gusta la ponencia: Mitos y realidades del uso del software libre.','0','0','2013-01-26 11:00:36'),(480,95,4,'esta todo muy bien , pasaros al ropero para ayudarnos con lo de italia','0','0','2013-01-26 11:01:25'),(481,98,4,'Me gusta la zona infantil.','0','0','2013-01-26 11:10:45'),(482,100,4,'Me gusta mucho la zona infantil.','0','0','2013-01-26 11:14:55'),(483,101,4,'esta muy bien la zona infantil. apta para que se lo pasen bien los mas pequeños','0','0','2013-01-26 11:15:22'),(484,103,4,'Me gusta la Zona de juegos.','0','0','2013-01-26 11:21:07'),(485,101,4,'esta muy bien todo','0','0','2013-01-26 11:23:17'),(486,100,4,'esta genial mola mazoooooooo','0','0','2013-01-26 11:23:47'),(487,102,4,'Toda actividad de este tipo es interesante no solo porque se ve el colegio con otros ojos sino ademas por la promocion del software libre','0','0','2013-01-26 11:26:12'),(488,105,4,'me ha gustado mucho','0','0','2013-01-26 11:34:25'),(489,106,4,'Me gusta la Exposición.','0','0','2013-01-26 11:34:56'),(490,96,4,'Me gusta la Exposición. muy interesante la domotica, pronto la veremos en muchas casas...','0','0','2013-01-26 11:35:50'),(491,107,4,'Me gusta la Exposición.','0','0','2013-01-26 11:44:18'),(492,108,4,'me enterado de muchas cosas q no sabía','0','0','2013-01-26 11:47:10'),(493,106,4,'Mi actividad favorita es: Zona Android.','0','0','2013-01-26 11:47:26'),(494,108,4,'bien explicado y nos hemos enterado de mx cosas ya q somos 1 eso','0','0','2013-01-26 11:49:33'),(495,95,4,'esta genial no lo hemos pasado muy bien era muy interesante ','0','0','2013-01-26 11:50:06'),(496,95,4,'Me gusta la ponencia: Internet sin miedo.','0','0','2013-01-26 11:50:14'),(497,109,4,'Me gusta la ponencia: AIDIA Robótica social programable para todos.','0','0','2013-01-26 11:51:34'),(498,109,4,'','0','0','2013-01-26 11:52:10'),(499,110,4,'Mi actividad favorita es la ponencia: Internet sin miedo.','0','0','2013-01-26 11:52:22'),(500,110,4,'ha estado muy interesante','0','0','2013-01-26 11:52:50'),(501,109,4,'estupendo montaje. los chicos muy profesionales','0','0','2013-01-26 11:53:07'),(502,111,4,'mola','0','0','2013-01-26 11:53:09'),(503,112,4,'vamos a empezar','0','0','2013-01-26 11:53:50'),(504,112,4,'vamos a empezar','0','0','2013-01-26 11:54:06'),(505,93,4,'interesante sobre todo para cobtrolr el acceso de los niñosa internet','0','0','2013-01-26 11:54:18'),(506,111,4,'muy interesante','0','0','2013-01-26 11:57:48'),(507,112,4,'Esta muy bien','0','0','2013-01-26 11:58:11'),(508,113,4,'mol','0','0','2013-01-26 11:59:40'),(509,113,4,'i like so much !!!! wowwww :D','0','0','2013-01-26 12:01:22'),(510,112,4,'no he probado la ','0','0','2013-01-26 12:01:32'),(511,114,4,'Mi actividad favorita es: Zona Android. me han solucionado los problemas q tenía','0','0','2013-01-26 12:02:08'),(512,113,4,'esta asta arriba de enanos viciadillos','0','0','2013-01-26 12:02:48'),(513,114,4,'Mi actividad favorita es: Fotomontajes con GIMP. han enviado por correo a las niñas el montaje','0','0','2013-01-26 12:03:01'),(514,112,4,'ahora la he probado y esta muy bien','0','0','2013-01-26 12:03:13'),(515,114,4,'Mi actividad favorita es: Taller de Fotografía Libre. muy interesante','0','0','2013-01-26 12:03:51'),(516,112,4,'me gustan mucho los grafitis de  esta sala  y los contenidos','0','0','2013-01-26 12:06:46'),(517,90,4,'..','0','0','2013-01-26 12:09:49'),(518,96,4,'Mi actividad favorita es: Taller de Fotografía Libre. a ver si aprendo a hacer fotos asi, al menos ahora sé que sin tripode no es facil','0','0','2013-01-26 12:10:55'),(519,116,4,'muy interesante la jornada y muy practica','0','0','2013-01-26 12:15:47'),(520,117,4,'Mi actividad favorita es: Exposición','0','0','2013-01-26 12:16:28'),(521,120,4,'Mi actividad favorita es: Exposición','0','0','2013-01-26 12:18:28'),(522,117,4,'Mi actividad favorita es: Exposición','0','0','2013-01-26 12:18:57'),(523,119,4,'Mi actividad favorita es: Zona Android.porque me han arregldo el movil.','0','0','2013-01-26 12:19:55'),(524,119,4,'Mi actividad favorita es: Zona Android. Porque me han arreglado el movil.','0','0','2013-01-26 12:21:32'),(525,117,4,'prueba','0','0','2013-01-26 12:28:00'),(526,112,4,'hola','0','0','2013-01-26 12:28:41'),(527,115,4,'genial!!muy entretenido','0','0','2013-01-26 12:28:49'),(528,96,4,'muy chulo todo','0','0','2013-01-26 12:29:08'),(529,112,4,'Mi actividad favorita es: Zona de Juegos.','0','0','2013-01-26 12:29:33'),(530,122,4,'Mi actividad favorita es: Taller de Fotografía Libre','0','0','2013-01-26 12:33:18'),(531,115,4,'si nos ha gustado','0','0','2013-01-26 12:42:00'),(532,115,4,'super útil la zona infantil. padres relajados disfrutan mejor de la fiesta','0','0','2013-01-26 12:55:35'),(533,123,4,'Mi actividad favorita es: Zona de Juegos.','0','0','2013-01-26 12:56:31'),(534,115,4,'3muy entretenidos','0','0','2013-01-26 12:59:21'),(535,124,4,'Mi actividad favorita es la ponencia: Internet sin miedo.','0','0','2013-01-26 13:01:06'),(536,115,4,'Mi actividad favorita es: Fotomontajes con GIMP.','0','0','2013-01-26 13:01:29'),(537,115,4,'Mi actividad favorita es: Zona de Juegos.','0','0','2013-01-26 13:02:04'),(538,125,4,'Mi actividad favorita es: Instalación y Diagnóstico. Habría que pensar en el soporte posterior a la instalación','0','0','2013-01-26 13:04:11'),(539,127,4,'Mi actividad favorita es: Instalación y Diagnóstico.','0','0','2013-01-26 13:21:11'),(540,112,4,'Me gusta la ponencia: Como empezar a usar y entender Android y no morir en el intento.','0','0','2013-01-26 13:22:57'),(541,93,4,'Mi actividad favorita es: Zona Android.','0','0','2013-01-26 13:24:00'),(542,93,4,'m0la','0','0','2013-01-26 13:24:28');
/*!40000 ALTER TABLE `Message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rate`
--

DROP TABLE IF EXISTS `Rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `space_id` varchar(50) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Rate_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rate`
--

LOCK TABLES `Rate` WRITE;
/*!40000 ALTER TABLE `Rate` DISABLE KEYS */;
INSERT INTO `Rate` VALUES (78,90,'tablonjornadas','3.0'),(79,94,'tablonjornadas','4.0'),(80,94,'tablonjornadas','4.0'),(81,95,'tablonjornadas','5.0'),(82,96,'tablonjornadas','5.0'),(83,98,'tablonjornadas','4.0'),(84,99,'tablonjornadas','1.0'),(85,99,'tablonjornadas','3.5'),(86,99,'tablonjornadas','4.0'),(87,101,'tablonjornadas','4.0'),(88,101,'tablonjornadas','5.0'),(89,101,'tablonjornadas','5.0'),(90,101,'tablonjornadas','5.0'),(91,102,'tablonjornadas','2.5'),(92,102,'tablonjornadas','4.0'),(93,102,'tablonjornadas','5.0'),(94,102,'tablonjornadas','5.0'),(95,104,'tablonjornadas','5.0'),(96,105,'tablonjornadas','3.0'),(97,105,'tablonjornadas','3.5'),(98,106,'tablonjornadas','5.0'),(99,106,'tablonjornadas','5.0'),(100,96,'tablonjornadas','4.0'),(101,107,'tablonjornadas','4.0'),(102,106,'tablonjornadas','4.0'),(103,108,'tablonjornadas','3.0'),(104,109,'tablonjornadas','5.0'),(105,110,'tablonjornadas','5.0'),(106,110,'tablonjornadas','5.0'),(107,111,'tablonjornadas','4.5'),(108,111,'tablonjornadas','5.0'),(109,113,'tablonjornadas','3.0'),(110,113,'tablonjornadas','3.5'),(111,113,'tablonjornadas','4.0'),(112,113,'tablonjornadas','3.5'),(113,112,'tablonjornadas','5.0'),(114,112,'tablonjornadas','5.0'),(115,115,'tablonjornadas','5.0'),(116,112,'tablonjornadas','5.0'),(117,115,'tablonjornadas','4.0'),(118,115,'tablonjornadas','4.0'),(119,115,'tablonjornadas','3.5'),(120,115,'tablonjornadas','3.5'),(121,115,'tablonjornadas','5.0'),(122,124,'tablonjornadas','4.0'),(123,124,'tablonjornadas','5.0'),(124,96,'tablonjornadas','5.0'),(125,93,'tablonjornadas','1.0'),(126,93,'tablonjornadas','1.0'),(127,93,'tablonjornadas','1.5'),(128,93,'tablonjornadas','3.0'),(129,93,'tablonjornadas','3.5');
/*!40000 ALTER TABLE `Rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tablon`
--

DROP TABLE IF EXISTS `Tablon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tablon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `visibility` binary(1) DEFAULT NULL,
  `space` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `rate` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `subtitle` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tablon`
--

LOCK TABLES `Tablon` WRITE;
/*!40000 ALTER TABLE `Tablon` DISABLE KEYS */;
INSERT INTO `Tablon` VALUES (4,'Install Party JRO','1','tablonjornadas','2.9310203','Opina sobre las actividades');
/*!40000 ALTER TABLE `Tablon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TablonTargetUser`
--

DROP TABLE IF EXISTS `TablonTargetUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TablonTargetUser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tablon_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tablon_id` (`tablon_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `TablonTargetUser_ibfk_1` FOREIGN KEY (`tablon_id`) REFERENCES `Tablon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `TablonTargetUser_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TablonTargetUser`
--

LOCK TABLES `TablonTargetUser` WRITE;
/*!40000 ALTER TABLE `TablonTargetUser` DISABLE KEYS */;
/*!40000 ALTER TABLE `TablonTargetUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TablonUserModerates`
--

DROP TABLE IF EXISTS `TablonUserModerates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TablonUserModerates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tablon_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tablon_id` (`tablon_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `TablonUserModerates_ibfk_1` FOREIGN KEY (`tablon_id`) REFERENCES `Tablon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `TablonUserModerates_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TablonUserModerates`
--

LOCK TABLES `TablonUserModerates` WRITE;
/*!40000 ALTER TABLE `TablonUserModerates` DISABLE KEYS */;
/*!40000 ALTER TABLE `TablonUserModerates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `surname1` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `surname2` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `nick` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `genre` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `work` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `age` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (89,NULL,NULL,NULL,'arc',NULL,'',''),(90,NULL,NULL,NULL,'uc3m',NULL,'',''),(91,NULL,NULL,NULL,'test',NULL,'',''),(92,NULL,NULL,NULL,'n3storm','Masculino','programador','35-50'),(93,NULL,NULL,NULL,'charla',NULL,'',''),(94,NULL,NULL,NULL,'gema','Femenino','estudiante','<18'),(95,NULL,NULL,NULL,'nubeal','Femenino','estudiante','<18'),(96,NULL,NULL,NULL,'juanpedro','Masculino','','35-50'),(97,NULL,NULL,NULL,'evaluci','Femenino','','<18'),(98,NULL,NULL,NULL,'candela','Femenino','',''),(99,NULL,NULL,NULL,'azu',NULL,'',''),(100,NULL,NULL,NULL,'patri','Femenino','guardaropas','<18'),(101,NULL,NULL,NULL,'lucia','Femenino','guardarropa\n','<18'),(102,NULL,NULL,NULL,'Escridela','Masculino','Consultor','35-50'),(103,NULL,NULL,NULL,'peter el panda','Masculino','estudiante','<18'),(104,NULL,NULL,NULL,'sergio\n','Masculino','estudiante','<18'),(105,NULL,NULL,NULL,'ruper 1407','Masculino','estudiante','<18'),(106,NULL,NULL,NULL,'sergio','Masculino','','<18'),(107,NULL,NULL,NULL,'shernanh','Femenino','',''),(108,NULL,NULL,NULL,'charla\n','Femenino','','<18'),(109,NULL,NULL,NULL,'merce','Femenino','profesor','35-50'),(110,NULL,NULL,NULL,'lordofchaosxl','Masculino','estudiante','<18'),(111,NULL,NULL,NULL,'diego','Masculino','','<18'),(112,NULL,NULL,NULL,'kimi','Femenino','administrativo','35-50'),(113,NULL,NULL,NULL,'albuxi','Femenino','bailarina','<18'),(114,NULL,NULL,NULL,'favoritos',NULL,'',''),(115,NULL,NULL,NULL,'Irene','Femenino','','35-50'),(116,NULL,NULL,NULL,'rjc\n','Masculino','estudiante','<18'),(117,NULL,NULL,NULL,'Freddkim','Masculino','seguridad','35-50'),(118,NULL,NULL,NULL,'donanfer\n','Masculino','profesor','35-50'),(119,NULL,NULL,NULL,'rjc','Masculino','estudinte','<18'),(120,NULL,NULL,NULL,'poli',NULL,'',''),(121,NULL,NULL,NULL,'donanfer','Masculino','profesor','35-50'),(122,NULL,NULL,NULL,'Martín','Masculino','profe\n','35-50'),(123,NULL,NULL,NULL,'Alvaro','Masculino','',''),(124,NULL,NULL,NULL,'kryptos','Masculino','consultor','25-35'),(125,NULL,NULL,NULL,'david','Masculino','','25-35'),(126,NULL,NULL,NULL,'Estivi','Masculino','estudiante','<18'),(127,NULL,NULL,NULL,'Paula\n\n','Femenino','estudiante','<18'),(128,NULL,NULL,NULL,'Paula','Femenino','estudiante','<18');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserPermission`
--

DROP TABLE IF EXISTS `UserPermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserPermission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `tablon_id` int(11) NOT NULL,
  `permission` binary(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `tablon_id` (`tablon_id`),
  CONSTRAINT `UserPermission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `UserPermission_ibfk_2` FOREIGN KEY (`tablon_id`) REFERENCES `Tablon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserPermission`
--

LOCK TABLES `UserPermission` WRITE;
/*!40000 ALTER TABLE `UserPermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserPermission` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-01-29  9:44:02
