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
  `monthID` varchar(8) NOT NULL COMMENT '统计月份',
  `ptype_id` int NOT NULL COMMENT '好乡镇宣传类型标识',
  `townID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '好乡镇标识',
  `puser_num` int NOT NULL DEFAULT '0' COMMENT '月累计宣传用户数',
  `suser_num` int NOT NULL DEFAULT '0' COMMENT '月累计助力用户数',
  PRIMARY KEY (`monthID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES ('2024-10',2,'0',1,0),('2024-12',2,'0',7,1);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;

--
-- Table structure for table `town_info`
--

DROP TABLE IF EXISTS `town_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `town_info` (
  `townID` bigint NOT NULL AUTO_INCREMENT COMMENT '乡镇标识',
  `townName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '乡镇名称',
  `cityName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '城市名称',
  `provinceName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '省名称',
  PRIMARY KEY (`townID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `town_info`
--

/*!40000 ALTER TABLE `town_info` DISABLE KEYS */;
INSERT INTO `town_info` VALUES (0,'天河区','广州市','广东省'),(2,'白云区','广州市','广东省'),(3,'增城区','广州市','广东省'),(4,'从化区','广州市','广东省'),(5,'花都区','广州市','广东省'),(6,'余杭区','杭州市','浙江省'),(7,'萧山区','杭州市','浙江省'),(8,'临安区','杭州市','浙江省'),(9,'富阳区','杭州市','浙江省'),(10,'塘栖镇','杭州市','浙江省'),(11,'建邺区','南京市','江苏省'),(12,'秦淮区','南京市','江苏省'),(13,'玄武区','南京市','江苏省'),(14,'栖霞区','南京市','江苏省'),(15,'谷里镇','南京市','江苏省'),(16,'沙坪坝区','重庆市','重庆市'),(17,'南开区','天津市','天津市'),(18,'河北区','哈尔滨市','黑龙江省'),(19,'江北区','哈尔滨市','黑龙江省'),(20,'金水区','郑州市','河南省'),(21,'管城回族区','郑州市','河南省'),(22,'莲湖区','西安市','陕西省'),(23,'未央区','西安市','陕西省'),(24,'江东区','南京市','江苏省'),(25,'秦淮区','南京市','江苏省'),(26,'锦江区','成都市','四川省'),(27,'龙泉驿区','成都市','四川省'),(28,'姑苏区','苏州市','江苏省'),(29,'吴中区','苏州市','江苏省'),(30,'渝北区','重庆市','重庆市'),(31,'九龙坡区','重庆市','重庆市'),(32,'汉阳区','武汉市','湖北省'),(33,'青山区','武汉市','湖北省'),(34,'大东区','沈阳市','辽宁省'),(35,'西岗区','大连市','辽宁省'),(36,'中原区','郑州市','河南省'),(37,'金明区','郑州市','河南省'),(38,'兴华乡','遂宁市','四川省'),(39,'和平镇','昭通市','云南省'),(40,'南山镇','宜春市','江西省'),(41,'西溪乡','梅州市','广东省'),(42,'大石镇','贺州市','广西壮族自治区'),(43,'中兴乡','恩施土家族苗族自治州','湖北省'),(44,'柳林镇','商洛市','陕西省'),(45,'东河乡','吉安市','江西省'),(46,'五湖镇','清远市','广东省'),(47,'红星乡','黔南布依族苗族自治州','贵州省'),(48,'紫云镇','保山市','云南省'),(49,'蓝田乡','渭南市','陕西省'),(50,'凤凰镇','丽江市','云南省'),(51,'松源乡','黔东南苗族侗族自治州','贵州省'),(52,'龙门镇','张家界市','湖南省'),(53,'茶山乡','延边朝鲜族自治州','吉林省'),(54,'水乡镇','泰州市','江苏省'),(55,'金岭乡','桂林市','广西壮族自治区'),(56,'碧水镇','九江市','江西省'),(57,'双江乡','大理白族自治州','云南省'),(58,'柳江镇','玉林市','广西壮族自治区'),(59,'古镇乡','盘锦市','辽宁省'),(60,'银海镇','连云港市','江苏省'),(61,'丰收乡','德阳市','四川省'),(62,'锦绣镇','泰州市','江苏省'),(63,'幸福乡','黄石市','湖北省'),(64,'瑞丰镇','驻马店市','河南省'),(65,'春光乡','六安市','安徽省'),(66,'锦程镇','承德市','河北省');
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
  `videourl` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '视频链接',
  PRIMARY KEY (`pid`) USING BTREE,
  KEY `f1` (`puserid`) USING BTREE,
  KEY `f2` (`ptype_id`) USING BTREE,
  CONSTRAINT `town_promotional_ibfk_1` FOREIGN KEY (`puserid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `town_promotional_ibfk_2` FOREIGN KEY (`ptype_id`) REFERENCES `promotional_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `town_promotional`
--

/*!40000 ALTER TABLE `town_promotional` DISABLE KEYS */;
INSERT INTO `town_promotional` VALUES (1,'主题',5,0,0,'描述','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/50cc41b6-c76f-4e6c-b77b-be2f2f390dce.png,https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/b989aba8-5ce5-4f26-82d8-3822200742ab.png','2024-12-08 15:51:40',-1,'2024-12-15 12:50:42',''),(2,'`',5,0,0,'1','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/50cc41b6-c76f-4e6c-b77b-be2f2f390dce.png','2024-12-08 17:18:09',-1,'2024-12-15 12:51:41',''),(3,'测试测试测试测试',3,0,13,'测试修改','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/50cc41b6-c76f-4e6c-b77b-be2f2f390dce.png','2024-12-08 17:24:40',0,'2024-12-15 15:44:33',''),(4,'2',2,0,0,'test2','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/50cc41b6-c76f-4e6c-b77b-be2f2f390dce.png','2024-12-08 17:24:52',-1,'2024-12-15 14:32:42',''),(5,'3',4,0,0,'test3','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/b989aba8-5ce5-4f26-82d8-3822200742ab.png','2024-12-08 17:26:07',-1,'2024-12-17 21:33:25',''),(6,'3',4,0,0,'test343544ffedgde','','2024-12-08 17:26:09',0,'2024-12-18 22:16:05','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/9f589dcd-5b43-42af-b44e-22d200cd7f12.mp4'),(7,'3',4,0,0,'test3','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/b989aba8-5ce5-4f26-82d8-3822200742ab.png','2024-12-08 17:26:11',0,'2024-12-08 17:26:11',''),(8,'3',4,0,0,'test3','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/b989aba8-5ce5-4f26-82d8-3822200742ab.png','2024-12-08 17:26:11',0,'2024-12-08 17:26:11',''),(9,'3',4,0,0,'test3','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/b989aba8-5ce5-4f26-82d8-3822200742ab.png','2024-12-08 17:26:11',0,'2024-12-18 22:01:13','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/c93005af-05e1-4d10-b798-16273b5c81c3.mp4'),(10,'3',4,0,0,'test3','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/b989aba8-5ce5-4f26-82d8-3822200742ab.png','2024-12-08 17:26:11',0,'2024-12-08 17:26:11',''),(11,'213123',2,0,0,'24141412321','','2024-12-25 18:17:21',0,'2024-12-25 18:17:21',''),(12,'312342',2,0,0,'421412','','2024-12-25 18:18:40',0,'2024-12-25 18:18:40',''),(13,'131243',2,0,51,'2412421','','2024-12-25 18:19:24',0,'2024-12-25 18:20:00',''),(14,'414124',2,0,0,'21412412421','','2024-10-25 18:19:24',0,'2024-10-25 18:19:24',NULL),(15,'123213',5,1438683138,40,'42242','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/5e9ae694-a446-4697-a688-2d724471688f.png','2024-12-25 21:20:16',0,'2024-12-25 21:20:16','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/c7c6401c-fdd7-47e6-b2a1-e7334db48b9b.mp4');
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
  `sdesc` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '助力描述',
  `support_date` datetime NOT NULL COMMENT '创建日期',
  `support_state` int NOT NULL COMMENT '状态，0：待接受；1：已接受；2：拒绝；3：取消',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  `sfile_list` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '上传的介绍图片等文件名称列表',
  `videourl` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sid`) USING BTREE,
  KEY `bid2` (`suser_id`) USING BTREE,
  CONSTRAINT `town_support_ibfk_1` FOREIGN KEY (`suser_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2046857219 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `town_support`
--

/*!40000 ALTER TABLE `town_support` DISABLE KEYS */;
INSERT INTO `town_support` VALUES (524308481,0,3,'22','2224343','2024-12-17 01:16:45',1,'2024-12-17 17:41:23','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/96350db6-3dc7-4a24-9dbb-a26ec960a603.png',''),(885088258,0,3,'1','123','2024-12-17 01:08:04',3,'2024-12-17 17:13:04','',''),(1455460354,1438683138,3,'22发公告g','4reefef','2024-12-17 17:50:07',2,'2024-12-17 20:51:46','',''),(2046857218,0,3,'312323','2323323','2024-12-17 17:44:43',0,'2024-12-18 22:16:18','','https://sky-take-out-1317682162.cos.ap-beijing.myqcloud.com/goodtown/630a586b-ddba-4221-8463-1005135b7174.mp4');
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
) ENGINE=InnoDB AUTO_INCREMENT=1438683139 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (-1556008958,'RandomUser','IDCard','123456789012345678','jrcd','787ac6a6e095632819008fa9623c0e7a','13800138000','2024-12-08 15:26:50','2024-12-08 15:26:50','Normal','随机生成的用户'),(0,'jrcd','IDCard','123456789012345678','jrcd','51995c170515203fbb28df2412ce5e23','13800138000','2024-12-03 18:14:43','2024-12-17 17:48:32','Normal','随机生成的用户'),(1,'admin','IDCard','31224434343','admin','23243refegeg4141','142313','2024-12-17 17:49:34','2024-12-17 17:49:34',NULL,'admin'),(1438683138,'cd','身份证','3432543663436','cd','51995c170515203fbb28df2412ce5e23','15382858999','2024-12-17 17:49:34','2024-12-17 17:49:34',NULL,'');
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

-- Dump completed on 2024-12-26  0:49:55
