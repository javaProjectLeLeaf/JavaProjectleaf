/*
Navicat MySQL Data Transfer

Source Server         : yt
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : javaproject

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-09-30 16:21:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for salary
-- ----------------------------
DROP TABLE IF EXISTS `salary`;
CREATE TABLE `salary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sbmc` varchar(200) DEFAULT NULL,
  `ggxh` varchar(255) DEFAULT NULL,
  `fsss` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `cdate` datetime DEFAULT NULL,
  `dj` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `bz` varchar(255) DEFAULT NULL,
  `zt` int(11) DEFAULT NULL,
  `by1` varchar(255) DEFAULT NULL,
  `by2` varchar(255) DEFAULT NULL,
  `by3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of salary
-- ----------------------------
INSERT INTO `salary` VALUES ('22', '11111111', '', '', '', null, '', null, '', null, '', null, null);
INSERT INTO `salary` VALUES ('23', '11111111', '', '', '', null, '', null, '', '1', null, null, null);
INSERT INTO `salary` VALUES ('24', '设备测试', '规格一', '1', '000872', null, '12', '11', '1', '2', null, null, null);
INSERT INTO `salary` VALUES ('25', '测试', '', '', '', null, '1', '1', '', '3', null, null, null);
INSERT INTO `salary` VALUES ('26', '测', '', '', '', null, '2', '2', '', '3', null, null, null);
INSERT INTO `salary` VALUES ('27', '2342342', '', '', '', null, '', null, '2342', '2', null, null, null);
INSERT INTO `salary` VALUES ('28', '设备测试', '', '', '', null, '234', '23423', '234', '2', null, null, null);
INSERT INTO `salary` VALUES ('29', '1', '', '', '', null, '', null, '', '1', null, null, null);
INSERT INTO `salary` VALUES ('30', '3453', '', '', '3', null, '', null, '', '2', null, null, null);
