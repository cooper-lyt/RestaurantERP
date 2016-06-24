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
-- Dumping data for table `AccountTypeEntity`
--

LOCK TABLES `AccountTypeEntity` WRITE;
/*!40000 ALTER TABLE `AccountTypeEntity` DISABLE KEYS */;
INSERT INTO `AccountTypeEntity` (`email`, `firstName`, `lastName`, `loginName`, `id`) VALUES ('hr@acme.com','人力资源','管理','hr','07db2e70-8fe5-4ca6-b3cb-8b8a70cd0e9c');
/*!40000 ALTER TABLE `AccountTypeEntity` ENABLE KEYS */;
UNLOCK TABLES;



LOCK TABLES `AttributedTypeEntity` WRITE;
/*!40000 ALTER TABLE `AttributedTypeEntity` DISABLE KEYS */;
INSERT INTO `AttributedTypeEntity` (`id`) VALUES ('07db2e70-8fe5-4ca6-b3cb-8b8a70cd0e9c'),('1');
/*!40000 ALTER TABLE `AttributedTypeEntity` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `IdentityTypeEntity`
--

LOCK TABLES `IdentityTypeEntity` WRITE;
/*!40000 ALTER TABLE `IdentityTypeEntity` DISABLE KEYS */;
INSERT INTO `IdentityTypeEntity` (`createdDate`, `enabled`, `expirationDate`, `typeName`, `id`, `partition_id`) VALUES ('2016-06-05 22:32:54','',NULL,'org.picketlink.idm.model.basic.User','07db2e70-8fe5-4ca6-b3cb-8b8a70cd0e9c','1');
/*!40000 ALTER TABLE `IdentityTypeEntity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PartitionTypeEntity`
--

LOCK TABLES `PartitionTypeEntity` WRITE;
/*!40000 ALTER TABLE `PartitionTypeEntity` DISABLE KEYS */;
INSERT INTO `PartitionTypeEntity` (`configurationName`, `name`, `typeName`, `id`) VALUES ('default','default','org.picketlink.idm.model.basic.Realm','1');
/*!40000 ALTER TABLE `PartitionTypeEntity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PasswordCredentialTypeEntity`
--

LOCK TABLES `PasswordCredentialTypeEntity` WRITE;
/*!40000 ALTER TABLE `PasswordCredentialTypeEntity` DISABLE KEYS */;
INSERT INTO `PasswordCredentialTypeEntity` (`id`, `effectiveDate`, `expiryDate`, `typeName`, `passwordEncodedHash`, `passwordSalt`, `owner_id`) VALUES (1,'2016-06-05 22:32:55',NULL,'org.picketlink.idm.credential.storage.EncodedPasswordStorage','fSajuGCMZI4SfRnKC6KTe8pVv4hlyU+cw/nanF0hfXazyL5zCxYx9micoPtHI7u3vdh4yJ62M17u\njOrdpyCBcA==','4543687533349965657','07db2e70-8fe5-4ca6-b3cb-8b8a70cd0e9c');
/*!40000 ALTER TABLE `PasswordCredentialTypeEntity` ENABLE KEYS */;
UNLOCK TABLES;


INSERT INTO DICTIONARY_CATEGORY(ID, NAME, SYSTEM, ENABLE, BOTIME) VALUES('hr.level','员工级别',true,true,'2016-06-1');
INSERT INTO DICTIONARY(ID, CATEGORY, NAME, PRI, ENABLE) VALUES('hr.level1','hr.level','初级',4,true);

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES (2);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-16 23:29:18
