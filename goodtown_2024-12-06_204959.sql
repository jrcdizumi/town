-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: goodtown
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accept_info`
--

DROP TABLE IF EXISTS `accept_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accept_info` (
  `id` int NOT NULL COMMENT '助力成功记录标识',
  `pid` int NOT NULL COMMENT '好乡镇宣传标识',
  `sid` int NOT NULL COMMENT '好乡镇助力标识',
  `createdate` datetime NOT NULL COMMENT '达成日期',
  `desc` int NOT NULL COMMENT '备注描述',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `pid` (`pid`) USING BTREE,
  KEY `rid` (`sid`) USING BTREE,
  CONSTRAINT `accept_info_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `town_promotional` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `accept_info_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `town_support` (`sid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accept_info`
--

/*!40000 ALTER TABLE `accept_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `accept_info` ENABLE KEYS */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

--
-- Table structure for table `city_info`
--

DROP TABLE IF EXISTS `city_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city_info` (
  `cityID` int NOT NULL COMMENT '城市标识',
  `cityName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '城市名称',
  `provinceID` int DEFAULT NULL COMMENT '省标识',
  `provinceName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '省名称',
  PRIMARY KEY (`cityID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city_info`
--

/*!40000 ALTER TABLE `city_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `city_info` ENABLE KEYS */;

--
-- Table structure for table `promotional_type`
--

DROP TABLE IF EXISTS `promotional_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotional_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '好乡镇宣传类型标识',
  `typename` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '好乡镇宣传类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotional_type`
--

/*!40000 ALTER TABLE `promotional_type` DISABLE KEYS */;
INSERT INTO `promotional_type` VALUES (1,'农家院'),(2,'自然风光秀丽'),(3,'古建筑'),(4,'土特产'),(5,'特色小吃'),(6,'民俗活动');
/*!40000 ALTER TABLE `promotional_type` ENABLE KEYS */;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `monthID` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '统计月份',
  `ptype_id` int NOT NULL COMMENT '好乡镇宣传类型标识',
  `townID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '好乡镇标识',
  `puser_num` int NOT NULL COMMENT '月累计宣传用户数',
  `suser_num` int NOT NULL COMMENT '月累计助力用户数',
  PRIMARY KEY (`monthID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;

--
-- Table structure for table `town_info`
--

DROP TABLE IF EXISTS `town_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `town_info` (
  `townID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '乡镇标识',
  `townName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '乡镇名称',
  `cityID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '城市标识',
  `cityName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '城市名称',
  `provinceID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '省标识',
  `provinceName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '省名称',
  PRIMARY KEY (`townID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `town_info`
--

/*!40000 ALTER TABLE `town_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `town_info` ENABLE KEYS */;

--
-- Table structure for table `town_promotional`
--

DROP TABLE IF EXISTS `town_promotional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `town_promotional` (
  `pid` int NOT NULL AUTO_INCREMENT COMMENT '好乡镇宣传标识',
  `ptitle` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '好乡镇宣传标题',
  `ptype_id` int NOT NULL COMMENT '好乡镇宣传类型标识',
  `puserid` int NOT NULL COMMENT '发布该好乡镇宣传的用户标识',
  `townID` int NOT NULL COMMENT '被宣传的好乡镇标识',
  `pdesc` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '好乡镇宣传描述',
  `pfile_list` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '上传的图片等资源文件名称列表',
  `pbegindate` datetime NOT NULL COMMENT '开始宣传日期，默认提交日期',
  `pstate` int NOT NULL COMMENT '状态，0：已发布；-1：已取消',
  `pupdatedate` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`pid`) USING BTREE,
  KEY `f1` (`puserid`) USING BTREE,
  KEY `f2` (`ptype_id`) USING BTREE,
  CONSTRAINT `town_promotional_ibfk_1` FOREIGN KEY (`puserid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `town_promotional_ibfk_2` FOREIGN KEY (`ptype_id`) REFERENCES `promotional_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `town_promotional`
--

/*!40000 ALTER TABLE `town_promotional` DISABLE KEYS */;
/*!40000 ALTER TABLE `town_promotional` ENABLE KEYS */;

--
-- Table structure for table `town_support`
--

DROP TABLE IF EXISTS `town_support`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `town_support` (
  `sid` int NOT NULL AUTO_INCREMENT COMMENT '好乡镇宣传助力标识',
  `suser_id` int NOT NULL COMMENT '助力用户标识',
  `pid` int NOT NULL COMMENT '对应的好乡镇宣传标识',
  `stitle` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '助力标题',
  `sdesc` tinyint NOT NULL COMMENT '助力描述',
  `support_date` datetime NOT NULL COMMENT '创建日期',
  `support_state` int NOT NULL COMMENT '状态，0：待接受；1：已接受；2：拒绝；3：取消',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  `sfile_list` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '上传的介绍图片等文件名称列表',
  PRIMARY KEY (`sid`) USING BTREE,
  KEY `bid2` (`suser_id`) USING BTREE,
  CONSTRAINT `town_support_ibfk_1` FOREIGN KEY (`suser_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `town_support`
--

/*!40000 ALTER TABLE `town_support` DISABLE KEYS */;
/*!40000 ALTER TABLE `town_support` ENABLE KEYS */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户标识',
  `uname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '用户注册名称',
  `ctype` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '证件类型，默认身份证',
  `idno` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '证件号码',
  `bname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '用户姓名',
  `bpwd` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '密码',
  `phoneNo` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '联系电话',
  `rdate` datetime NOT NULL COMMENT '注册时间',
  `udate` datetime NOT NULL COMMENT '修改时间',
  `userlvl` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户级别，默认普通用户，可扩展设计对应业务功能',
  `desc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户简介',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uname` (`uname`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (0,'jrcd','IDCard','123456789012345678','jrcd','787ac6a6e095632819008fa9623c0e7a','13800138000','2024-12-03 18:14:43','2024-12-03 18:14:43','Normal','随机生成的用户');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

--
-- Dumping routines for database 'goodtown'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-06 20:50:06
