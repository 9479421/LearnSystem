-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: learnsys
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chapterId` int NOT NULL,
  `digest` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `courseId` int NOT NULL,
  `chapterName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `duration` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10006 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
INSERT INTO `chapter` VALUES (10002,1,'76a7f6be99b2852527b6ad0631c63f2e','/10001/76a7f6be99b2852527b6ad0631c63f2e.mp4',10001,'1.4 数列极限（一）','2858.027000'),(10003,2,'9bae8eef1d14a54f739f789c72b6322d','/10001/9bae8eef1d14a54f739f789c72b6322d.mp4',10001,'1.5 函数极限（一）','2639.616000'),(10004,3,'8b4cc14302ba7122abb1fa8ab7be0e8d','/10001/8b4cc14302ba7122abb1fa8ab7be0e8d.mp4',10001,'1.5 函数极限（二）','862.656000'),(10005,4,'b906b629c1203ad94c6db03d781a280a','/10001/b906b629c1203ad94c6db03d781a280a.mp4',10001,'1.6 无穷小和无穷大','1656.576000');
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberId` varchar(100) NOT NULL,
  `content` varchar(800) NOT NULL,
  `courseId` varchar(100) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (6,'1','3213213213\n321412445\n小小的测试一下我是王某人','10001','2022-11-04 20:28:24'),(7,'10','哈哈哈测试一下','10001','2022-11-04 20:40:57');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `courseId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `teacher` varchar(100) NOT NULL,
  `coverImg` varchar(1000) NOT NULL,
  `intro` varchar(1000) NOT NULL,
  `isPutAway` tinyint(1) NOT NULL,
  PRIMARY KEY (`courseId`)
) ENGINE=InnoDB AUTO_INCREMENT=10035 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (10000,'《线性代数》“惊叹号”系列','宋浩','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10000/cover.png','博士，副教授，网红数学老师。\\n新浪微博：宋浩老师_ice_mouse',1),(10001,'《微积分》《高等数学》全程教学视频','宋浩','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10001/cover.png','本专辑是《微积分》的全程教学视频，微积分看完本视频专辑就可以了。',1),(10002,'【狂神说Java】注解和反射','遇见狂神说','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10002/cover.png','课程主线：注解->自定义注解->Class类->类加载机制->反射的实际应用',0),(10003,'React核心技术与开发实战','孙志强','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10003/cover.png','傻逼孙志强的历史',1),(10004,'python高薪全能开发预科班','石博','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10004/cover.png','脑残孙志强是吃屎王',1),(10008,'Ps教程/平面设计/品牌logo/VI设计/海报设计/淘宝美工','石博','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10008/cover.png','222',0),(10009,'Python+Vue+Django前后端分离项目实战课程','奥沙利权','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10009/cover.png','222',1),(10010,'培训机构的坑人套路','111','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10010/cover.png','222',1),(10011,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10012,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10013,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10014,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10015,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10016,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10017,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10018,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10019,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10020,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10021,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10022,'1111','4455','https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/20220911203413.png','222',1),(10024,'32131','3213','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10024/cover.png','4324324',1),(10025,'3213','432432','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10025/cover.png','543534',1),(10026,'432432','5435','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10026/cover.png','321321',1),(10029,'1','2133213','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10029/cover.png','4',1),(10032,'3213','3213','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/defaultCover.png','3213',1),(10033,'1','3','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/defaultCover.png','4',1),(10034,'Python小白萌新入门到精通一图胜千言【马士兵教育】','haha','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10034/cover.png','print()函数与转义字符、原字符',1);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `english`
--

DROP TABLE IF EXISTS `english`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `english` (
  `id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(100) NOT NULL,
  `digest` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `english`
--

LOCK TABLES `english` WRITE;
/*!40000 ALTER TABLE `english` DISABLE KEYS */;
INSERT INTO `english` VALUES (1,'/english/5773ee676aab823f49b225d0dbd21a5d.mp4','2475f4beb2d5ebf050b07273aa773797'),(2,'/english/9a3053245edfde5921d9c45524679a6f.mp4','f5c10384a01c505961daf1bec4790a5d'),(3,'/english/54a33f949cd0c706d713607b294821c3.mp4','be8101636cc75c9907a45b43b639c855'),(4,'/english/34d1f74b062613c2d4d520f81c9cafc3.mp4','3d593591a7f20b381034385474b66484'),(5,'/english/b6fd9e4270bcc835eedb553912a42bcd.mp4','9579f5fa424b3d4a4c1cf288bcb6f995'),(6,'/english/090bae596b78ec210c029df315ac6f40.mp4','e4d118d518464246f60971180a575011'),(7,'/english/60c6e665bf91c6c3e5478b98cf0f78ab.mp4','6d64822fd75e09e069795ed02f3e6b91'),(8,'/english/de1615473a215b2ee2f383c2ee33dbd1.mp4','686d20832f2817115b82f04f51120d9c'),(9,'/english/fc196e98774fcedf164dd31e42d742c7.mp4','a46631d128d4f4e3dbe15233c2669cb3'),(10,'/english/44f2f157dd6fdb7096c500cb79b25d05.mp4','92e039a1d75ef79a4f922c00432fea8f'),(11,'/english/0825861b077ecb6d8acff78b78e032d6.mp4','17931923cef49088793683d842c18185'),(12,'/english/dc89fbb371ac9f1315362d987619e217.mp4','63e644f02f00684c9cb74e058fffd3a7'),(13,'/english/293c80f47db0e275cf298284cb9e981b.mp4','2fbdc3c44f80966690eb4213421ca1ec'),(14,'/english/62f6a8ce15a215fd71abf4138ec10be5.mp4','5fbed1ac0c15d24db92ccea3eaba38a4'),(15,'/english/8c5bea6b9a8a227d7ac13ec837100bd7.mp4','6a5d14029dd21e5cdfda05494b5231a9'),(16,'/english/757285500046ec3732c2327fba773043.mp4','2c4f059773efbb0448e3bd9241d67178'),(17,'/english/3aa8e28acb7d653db5299c6eedf11fa1.mp4','7e94ca1d9b445d677a7b38424045f50a'),(18,'/english/daa1c868c43c3c83d0259f64ae98530d.mp4','827aadbdf8ba01f355bafd93600c2dd1'),(19,'/english/b53d7a9337241cb8b09ba763931ac369.mp4','9c67bb020e7da6b34c24b4b5c98c1eed'),(20,'/english/fc5240c24f72f575ed07f47b4f52e105.mp4','1b2a64226bc7a2ec671116add6db49d3'),(21,'/english/c601c79bc6a74eefb67d37d53775ce73.mp4','74c77b440adae703454b2202dfb0fccc'),(22,'/english/fcecae8544c6a038145776fc21c8753d.mp4','b480050f2c48d194d94b9be12e949860'),(23,'/english/316fdba22769de842346b08a3306beb9.mp4','19a05b73c76bb21760b4696fcf7afb2d'),(24,'/english/2e4ff384d729cc3c54c0beb0d444a375.mp4','9491896cd0cc3a47ef838300cf9cd9d2'),(25,'/english/a808be1674567888c0f0d1b95ea8b9c0.mp4','503f256dce6b7a8b8816d5e91596670e'),(26,'/english/d9136123ec97be948523343f3ad05ba9.mp4','f52c3f4649dc239a4d20d868ae7c24b9'),(27,'/english/a13da6060ddbbc4d6a2f6ce04b1c9242.mp4','c498329e63bea9666664eb6e53e7900d'),(28,'/english/b4448d8679dad8c8abfb1d82b2657046.mp4','20c0e49c67ad8cfc9e029cb30e3a3efe'),(29,'/english/dd6c67f6fdd52a1c8f779cb0f95b0bf0.mp4','13d49261c4009763930fa513c92a58b6');
/*!40000 ALTER TABLE `english` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `nickname` varchar(100) NOT NULL,
  `avatar` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_un` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'admin','88888','17301491797','小白3','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/avatar/6473e466c6338068b68687df00686de8.png'),(2,'88888','333','18763701288','大傻逼','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/defaultAvatar.png'),(7,'admin1','123123','17301491797','未命名','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/defaultAvatar.png'),(8,'admin2','123123','17301491797','未命名','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/defaultAvatar.png'),(9,'test1111','BAye6666','17301491797','未命名','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/defaultAvatar.png'),(10,'test000','123123abc','17301491797','未命名','https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/defaultAvatar.png');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mycourse`
--

DROP TABLE IF EXISTS `mycourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mycourse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `courseId` varchar(100) DEFAULT NULL,
  `memberId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mycourse`
--

LOCK TABLES `mycourse` WRITE;
/*!40000 ALTER TABLE `mycourse` DISABLE KEYS */;
INSERT INTO `mycourse` VALUES (1,'10001','1'),(2,'10003','1'),(3,'10008','1'),(4,'10004','1'),(5,'10001','10'),(6,'10000','10'),(7,'10003','10'),(8,'10004','10');
/*!40000 ALTER TABLE `mycourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberId` varchar(100) NOT NULL,
  `courseId` varchar(100) NOT NULL,
  `chapterId` varchar(100) NOT NULL,
  `currentTime` varchar(100) NOT NULL,
  `allTime` varchar(100) NOT NULL,
  `isWatched` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES (19,'1','10001','1','2850','2858',1),(20,'1','10001','2','2630','2639',1),(21,'1','10001','3','862','862',1),(22,'1','10001','4','1656','1656',1),(23,'10','10001','1','770','2858',0),(24,'10','10001','2','635','2639',0);
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'learnsys'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-10 13:36:04
