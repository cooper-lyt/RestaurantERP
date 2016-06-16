-- MySQL dump 10.13  Distrib 5.6.22, for osx10.8 (x86_64)
--
-- Host: localhost    Database: PLAT_SYSTEM
-- ------------------------------------------------------
-- Server version	5.6.22

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
-- Table structure for table `AccountTypeEntity`
--

DROP TABLE IF EXISTS `AccountTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountTypeEntity` (
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_7iu5vl4yukkrqnr7cawu5br46` FOREIGN KEY (`id`) REFERENCES `IdentityTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AttributeTypeEntity`
--

DROP TABLE IF EXISTS `AttributeTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AttributeTypeEntity` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `typeName` varchar(255) DEFAULT NULL,
  `value` longtext,
  `owner_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ehu3pv5hm09wme4vkmwwct7x5` (`owner_id`),
  CONSTRAINT `FK_ehu3pv5hm09wme4vkmwwct7x5` FOREIGN KEY (`owner_id`) REFERENCES `AttributedTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AttributedTypeEntity`
--

DROP TABLE IF EXISTS `AttributedTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AttributedTypeEntity` (
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GroupTypeEntity`
--

DROP TABLE IF EXISTS `GroupTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GroupTypeEntity` (
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jd61h1pmevgfdkjvxr3vyhdml` (`parent_id`),
  CONSTRAINT `FK_dcnn37f1v104dnxarqx2dikw8` FOREIGN KEY (`id`) REFERENCES `IdentityTypeEntity` (`id`),
  CONSTRAINT `FK_jd61h1pmevgfdkjvxr3vyhdml` FOREIGN KEY (`parent_id`) REFERENCES `GroupTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IdentityTypeEntity`
--

DROP TABLE IF EXISTS `IdentityTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IdentityTypeEntity` (
  `createdDate` datetime DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `typeName` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  `partition_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_g7e4b2ks8kb6tpjb1tbsfp42g` (`partition_id`),
  CONSTRAINT `FK_f3d4bi3xn9kph5uj8p4rufay8` FOREIGN KEY (`id`) REFERENCES `AttributedTypeEntity` (`id`),
  CONSTRAINT `FK_g7e4b2ks8kb6tpjb1tbsfp42g` FOREIGN KEY (`partition_id`) REFERENCES `PartitionTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PartitionTypeEntity`
--

DROP TABLE IF EXISTS `PartitionTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PartitionTypeEntity` (
  `configurationName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `typeName` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_2la0ajikmqq2oylb9b3txlakb` FOREIGN KEY (`id`) REFERENCES `AttributedTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PasswordCredentialTypeEntity`
--

DROP TABLE IF EXISTS `PasswordCredentialTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PasswordCredentialTypeEntity` (
  `id` bigint(20) NOT NULL,
  `effectiveDate` datetime DEFAULT NULL,
  `expiryDate` datetime DEFAULT NULL,
  `typeName` varchar(255) DEFAULT NULL,
  `passwordEncodedHash` varchar(255) DEFAULT NULL,
  `passwordSalt` varchar(255) DEFAULT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8g31qsmla607xjwnamfqn1nf5` (`owner_id`),
  CONSTRAINT `FK_8g31qsmla607xjwnamfqn1nf5` FOREIGN KEY (`owner_id`) REFERENCES `AttributedTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RelationshipIdentityTypeEntity`
--

DROP TABLE IF EXISTS `RelationshipIdentityTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RelationshipIdentityTypeEntity` (
  `identifier` bigint(20) NOT NULL,
  `descriptor` varchar(255) DEFAULT NULL,
  `identityType_id` varchar(255) DEFAULT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`identifier`),
  KEY `FK_di27wg0grlx8bmlyr4jxffw49` (`identityType_id`),
  KEY `FK_rlk0qy8e3g13ud2rqonjmd9rt` (`owner_id`),
  CONSTRAINT `FK_di27wg0grlx8bmlyr4jxffw49` FOREIGN KEY (`identityType_id`) REFERENCES `IdentityTypeEntity` (`id`),
  CONSTRAINT `FK_rlk0qy8e3g13ud2rqonjmd9rt` FOREIGN KEY (`owner_id`) REFERENCES `RelationshipTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RelationshipTypeEntity`
--

DROP TABLE IF EXISTS `RelationshipTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RelationshipTypeEntity` (
  `typeName` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_ay6h7h4e7t0my7p7jsp6obvqs` FOREIGN KEY (`id`) REFERENCES `AttributedTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RoleTypeEntity`
--

DROP TABLE IF EXISTS `RoleTypeEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RoleTypeEntity` (
  `name` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_awlh3ujrqy6rqsud7p204o3d4` FOREIGN KEY (`id`) REFERENCES `IdentityTypeEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-16 23:24:12
