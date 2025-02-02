/*
 Navicat Premium Dump SQL

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : db_dict_demo

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 02/02/2025 16:59:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dict_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `label` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` (`id`, `dict_type`, `code`, `label`) VALUES (1, 'sex', '1', '男');
INSERT INTO `sys_dict` (`id`, `dict_type`, `code`, `label`) VALUES (2, 'sex', '2', '女');
INSERT INTO `sys_dict` (`id`, `dict_type`, `code`, `label`) VALUES (3, 'order_status', '1', '待支付');
INSERT INTO `sys_dict` (`id`, `dict_type`, `code`, `label`) VALUES (4, 'order_status', '2', '已支付');
COMMIT;

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `amount` decimal(10,2) NOT NULL COMMENT '订单金额',
  `status` tinyint NOT NULL COMMENT '状态 1-待支付 2-已支付',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tb_order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
BEGIN;
INSERT INTO `tb_order` (`id`, `user_id`, `order_no`, `amount`, `status`) VALUES (1, 1, '202500129834', 99.99, 2);
INSERT INTO `tb_order` (`id`, `user_id`, `order_no`, `amount`, `status`) VALUES (2, 2, '202514739208', 199.50, 1);
INSERT INTO `tb_order` (`id`, `user_id`, `order_no`, `amount`, `status`) VALUES (3, 1, '202537082451', 299.00, 2);
INSERT INTO `tb_order` (`id`, `user_id`, `order_no`, `amount`, `status`) VALUES (4, 3, '202548392761', 599.99, 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `sex` tinyint NOT NULL COMMENT '性别（1-男，2-女）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` (`id`, `username`, `sex`) VALUES (1, '张三', 1);
INSERT INTO `tb_user` (`id`, `username`, `sex`) VALUES (2, '李四', 2);
INSERT INTO `tb_user` (`id`, `username`, `sex`) VALUES (3, '王五', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
