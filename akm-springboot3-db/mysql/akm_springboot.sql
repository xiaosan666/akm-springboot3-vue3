/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : akm_springboot

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 04/12/2025 09:37:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for biz_app_version
-- ----------------------------
DROP TABLE IF EXISTS `biz_app_version`;
CREATE TABLE `biz_app_version` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID主键',
  `record_type` smallint(6) NOT NULL COMMENT '记录分类：1Android，2iOS，3小程序，4web',
  `version` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '版本号',
  `note` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新内容，多行用中文分号；隔开',
  `force` smallint(6) NOT NULL COMMENT '是否强制更新（1强制，默认0）',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新包下载地址',
  `hot` smallint(6) NOT NULL DEFAULT '0' COMMENT '是否属于热更新（1是，默认0）',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用版本记录表';

-- ----------------------------
-- Records of biz_app_version
-- ----------------------------
BEGIN;
INSERT INTO `biz_app_version` (`id`, `record_type`, `version`, `note`, `force`, `url`, `hot`, `create_user_id`, `create_time`, `del_flag`) VALUES ('1', 1, '1.0.0', 'Android版本上线啦！；赶快体验吧', 0, 'http://192.168.43.6:9001/akm-1/app/__UNI__136990D_20210119120017.apk', 0, NULL, '2021-01-18 16:37:45', 0);
INSERT INTO `biz_app_version` (`id`, `record_type`, `version`, `note`, `force`, `url`, `hot`, `create_user_id`, `create_time`, `del_flag`) VALUES ('2', 2, '1.0.0', 'iOS版本上线啦！；赶快体验吧', 0, 'iOS一般要在AppStore更新，这里是更新页面的地址', 0, NULL, '2021-04-19 16:20:49', 0);
INSERT INTO `biz_app_version` (`id`, `record_type`, `version`, `note`, `force`, `url`, `hot`, `create_user_id`, `create_time`, `del_flag`) VALUES ('3', 3, '1.0.0', '小程序上线上线啦！；赶快体验吧', 0, '', 0, NULL, '2021-07-22 16:37:45', 0);
COMMIT;

-- ----------------------------
-- Table structure for biz_attachment
-- ----------------------------
DROP TABLE IF EXISTS `biz_attachment`;
CREATE TABLE `biz_attachment` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `record_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务模块分类标志',
  `record_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务表主键id',
  `attachment_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原文件名称',
  `attachment_url` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '附件路径',
  `attachment_size` longtext COLLATE utf8mb4_unicode_ci COMMENT '文件大小，单位Byte',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `test` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='附件表（业务与文件关系表）';

-- ----------------------------
-- Records of biz_attachment
-- ----------------------------
BEGIN;
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1837065644908916736', '11', '1', '001_张三.pdf', 'sys_attachment/8a23a421df7b4b78b001a06c6a95b973.pdf', '49672', '1183307967865425920', '2024-09-20 17:45:55', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1854429434902917120', '', '', '9aa3c-main.zip', 'sys_attachment/997b616151f9463fb907e79eaaa9cc66.zip', '31554', '1183307967865425920', '2024-11-07 15:43:26', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1854476369965264896', 'message', '1829088813677850624', '1.pdf', 'message/5d8b9a248bd84232a4a18ea70e2e3122.pdf', '49672', '1183307967865425920', '2024-11-07 18:49:09', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1854691507821522944', 'message', '1854691424879161344', '8a4a-keyancx5151103.jpg', 'message/e0caf85924cb461b994e1cd09715898a.jpg', '106477', '1183307967865425920', '2024-11-08 09:04:27', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1854691507821522945', 'message', '1854691424879161344', '1.pdf', 'message/5d8b9a248bd84232a4a18ea70e2e3122.pdf', '49672', '1183307967865425920', '2024-11-07 18:49:09', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1995687623932383232', '', '', '11.xlsx', 'sys_attachment/file/85cc5d8b95c54aaea229f233cbfc140f.xlsx', '1093329', '1983839748202696704', '2025-12-02 10:53:02', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1995690883669954560', 'a', 'a', '11_export.xlsx', 'sys_attachment/yxj/9173ff3960694d62968a3fbab195b6b9.xlsx', '428811', '1983839748202696704', '2025-12-02 11:05:59', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1995753465654546432', 'message', '1995753422532907008', '来访人员审批表(2025年6月10日更新)-1023.pdf', 'message/410f5c426863418492d78497c07e564b.pdf', '235600', '1183307967865425920', '2025-12-02 15:14:16', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1995753465654546433', 'message', '1995753422532907008', '来访人员审批表(2025年6月10日更新)-1031的副本.doc', 'message/d20a366757b54f2a9d28e6b70d1b6c7f.doc', '28672', '1183307967865425920', '2025-12-02 15:14:28', NULL);
INSERT INTO `biz_attachment` (`id`, `record_type`, `record_id`, `attachment_name`, `attachment_url`, `attachment_size`, `create_user_id`, `create_time`, `test`) VALUES ('1995753651671928832', 'message', '1995753628225769472', '来访人员审批表(2025年6月10日更新)-0929.pdf', 'message/693427133c90446b961815f4aff38519.pdf', '294196', '1183307967865425920', '2025-12-02 15:15:18', NULL);
COMMIT;

-- ----------------------------
-- Table structure for biz_banner
-- ----------------------------
DROP TABLE IF EXISTS `biz_banner`;
CREATE TABLE `biz_banner` (
  `id` int(11) NOT NULL COMMENT 'ID主键',
  `type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '轮播图分类',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `img_addr` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片地址',
  `orders` int(11) DEFAULT '0' COMMENT '排序（倒序）',
  `is_jump` int(11) NOT NULL DEFAULT '0' COMMENT '是否可跳转0：否；1是',
  `jump_addr` text COLLATE utf8mb4_unicode_ci COMMENT '跳转地址',
  `create_user_id` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL,
  `enable` int(11) NOT NULL DEFAULT '0' COMMENT '是否启用(默认1,禁用0)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='轮播图管理表';

-- ----------------------------
-- Records of biz_banner
-- ----------------------------
BEGIN;
INSERT INTO `biz_banner` (`id`, `type`, `name`, `description`, `img_addr`, `orders`, `is_jump`, `jump_addr`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `enable`) VALUES (1, 'banner_1', '1', '第一张', 'https://public-1253449983.cos.ap-guangzhou.myqcloud.com/banner/b1.jpg', 0, 0, NULL, '1183307967865425920', '2021-01-12 16:10:50', NULL, '2021-01-12 16:11:41', 1);
INSERT INTO `biz_banner` (`id`, `type`, `name`, `description`, `img_addr`, `orders`, `is_jump`, `jump_addr`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `enable`) VALUES (2, 'banner_1', '2', '第二张', 'https://public-1253449983.cos.ap-guangzhou.myqcloud.com/banner/b2.jpg', 0, 0, NULL, '1183307967865425920', '2021-01-12 16:10:50', NULL, '2021-01-12 16:11:41', 1);
INSERT INTO `biz_banner` (`id`, `type`, `name`, `description`, `img_addr`, `orders`, `is_jump`, `jump_addr`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `enable`) VALUES (3, 'banner_1', '3', '第三张', 'https://public-1253449983.cos.ap-guangzhou.myqcloud.com/banner/b3.jpg', 0, 0, NULL, '1183307967865425920', '2021-01-12 16:10:50', NULL, '2021-01-12 16:11:44', 1);
INSERT INTO `biz_banner` (`id`, `type`, `name`, `description`, `img_addr`, `orders`, `is_jump`, `jump_addr`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `enable`) VALUES (4, 'banner_1', '4', '第四张', 'https://public-1253449983.cos.ap-guangzhou.myqcloud.com/banner/b4.jpg', 0, 0, NULL, '1183307967865425920', '2021-01-12 16:10:50', NULL, '2021-01-12 16:11:44', 1);
COMMIT;

-- ----------------------------
-- Table structure for biz_district
-- ----------------------------
DROP TABLE IF EXISTS `biz_district`;
CREATE TABLE `biz_district` (
  `ad_code` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '区域编码',
  `parent_ad_code` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '父级区域编码（100000:中国）',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '行政区名称',
  `center` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '区域中心点经纬度',
  `level` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '行政区划级别（province:省份，city:市，district:区县）',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  PRIMARY KEY (`ad_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='行政区域信息表（https://www.mca.gov.cn/article/sj/xzqh/1980/）';

-- ----------------------------
-- Records of biz_district
-- ----------------------------
BEGIN;
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440000', '100000', '广东省', '113.266887,23.133306', 'province', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440100', '440000', '广州市', '113.264499,23.130061', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440103', '440100', '荔湾区', '113.218998,23.107123', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440104', '440100', '越秀区', '113.267065,23.128673', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440105', '440100', '海珠区', '113.317412,23.084003', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440106', '440100', '天河区', '113.361597,23.124817', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440111', '440100', '白云区', '113.2732,23.157159', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440112', '440100', '黄埔区', '113.480613,23.181355', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440113', '440100', '番禺区', '113.383917,22.93756', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440114', '440100', '花都区', '113.220125,23.404326', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440115', '440100', '南沙区', '113.525178,22.801435', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440117', '440100', '从化区', '113.586329,23.54915', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440118', '440100', '增城区', '113.810734,23.261452', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440200', '440000', '韶关市', '113.597324,24.810977', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440203', '440200', '武江区', '113.58792,24.7929', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440204', '440200', '浈江区', '113.611077,24.804898', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440205', '440200', '曲江区', '113.604591,24.682826', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440222', '440200', '始兴县', '114.061998,24.953908', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440224', '440200', '仁化县', '113.749175,25.085764', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440229', '440200', '翁源县', '114.129986,24.350581', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440232', '440200', '乳源瑶族自治县', '113.275875,24.775856', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440233', '440200', '新丰县', '114.206641,24.059909', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440281', '440200', '乐昌市', '113.347669,25.129892', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440282', '440200', '南雄市', '114.31184,25.117653', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440300', '440000', '深圳市', '114.057939,22.543527', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440303', '440300', '罗湖区', '114.131611,22.548309', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440304', '440300', '福田区', '114.055198,22.520922', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440305', '440300', '南山区', '113.930478,22.533191', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440306', '440300', '宝安区', '113.883831,22.554986', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440307', '440300', '龙岗区', '114.246884,22.720889', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440308', '440300', '盐田区', '114.236739,22.557001', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440309', '440300', '龙华区', '114.04491,22.696735', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440310', '440300', '坪山区', '114.350844,22.708786', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440311', '440300', '光明区', '113.936059,22.74875', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440400', '440000', '珠海市', '113.576892,22.271644', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440402', '440400', '香洲区', '113.543372,22.265635', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440403', '440400', '斗门区', '113.296228,22.209134', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440404', '440400', '金湾区', '113.363224,22.146717', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440500', '440000', '汕头市', '116.681956,23.354152', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440507', '440500', '龙湖区', '116.716464,23.372211', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440511', '440500', '金平区', '116.70341,23.365716', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440512', '440500', '濠江区', '116.726701,23.286605', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440513', '440500', '潮阳区', '116.601677,23.264923', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440514', '440500', '潮南区', '116.439105,23.239196', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440515', '440500', '澄海区', '116.755945,23.466314', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440523', '440500', '南澳县', '117.023482,23.421658', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440600', '440000', '佛山市', '113.121586,23.021351', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440604', '440600', '禅城区', '113.122532,23.009475', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440605', '440600', '南海区', '113.143246,23.028875', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440606', '440600', '顺德区', '113.293197,22.805413', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440607', '440600', '三水区', '112.897271,23.156675', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440608', '440600', '高明区', '112.892573,22.900047', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440700', '440000', '江门市', '113.081548,22.578948', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440703', '440700', '蓬江区', '113.078914,22.595285', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440704', '440700', '江海区', '113.111029,22.561301', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440705', '440700', '新会区', '113.034454,22.458519', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440781', '440700', '台山市', '112.793812,22.251947', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440783', '440700', '开平市', '112.698113,22.377378', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440784', '440700', '鹤山市', '112.964203,22.765912', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440785', '440700', '恩平市', '112.304904,22.183743', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440800', '440000', '湛江市', '110.357538,21.270108', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440802', '440800', '赤坎区', '110.365592,21.265948', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440803', '440800', '霞山区', '110.397721,21.192463', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440804', '440800', '坡头区', '110.455192,21.244405', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440811', '440800', '麻章区', '110.333833,21.26437', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440823', '440800', '遂溪县', '110.25043,21.378371', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440825', '440800', '徐闻县', '110.17595,20.325969', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440881', '440800', '廉江市', '110.286109,21.609988', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440882', '440800', '雷州市', '110.097011,20.914548', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440883', '440800', '吴川市', '110.779361,21.440763', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440900', '440000', '茂名市', '110.925533,21.662728', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440902', '440900', '茂南区', '110.918566,21.641661', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440904', '440900', '电白区', '111.013368,21.513946', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440981', '440900', '高州市', '110.853169,21.918017', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440982', '440900', '化州市', '110.639581,21.664483', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('440983', '440900', '信宜市', '110.946866,22.354887', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441200', '440000', '肇庆市', '112.465245,23.047747', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441202', '441200', '端州区', '112.485577,23.051847', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441203', '441200', '鼎湖区', '112.56738,23.159062', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441204', '441200', '高要区', '112.458055,23.02474', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441223', '441200', '广宁县', '112.440694,23.634808', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441224', '441200', '怀集县', '112.166908,23.920806', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441225', '441200', '封开县', '111.512177,23.423928', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441226', '441200', '德庆县', '111.785544,23.145035', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441284', '441200', '四会市', '112.734309,23.326991', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441300', '440000', '惠州市', '114.415587,23.112368', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441302', '441300', '惠城区', '114.382526,23.084657', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441303', '441300', '惠阳区', '114.456107,22.789431', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441322', '441300', '博罗县', '114.289602,23.172587', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441323', '441300', '惠东县', '114.720136,22.984831', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441324', '441300', '龙门县', '114.254898,23.727873', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441400', '440000', '梅州市', '116.122046,24.288832', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441402', '441400', '梅江区', '116.116686,24.31065', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441403', '441400', '梅县区', '116.081395,24.266191', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441422', '441400', '大埔县', '116.694825,24.347037', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441423', '441400', '丰顺县', '116.181895,23.739364', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441424', '441400', '五华县', '115.775751,23.932568', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441426', '441400', '平远县', '115.891235,24.566928', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441427', '441400', '蕉岭县', '116.171477,24.658963', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441481', '441400', '兴宁市', '115.731133,24.137296', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441500', '440000', '汕尾市', '115.375557,22.787204', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441502', '441500', '城区', '115.36522,22.779204', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441521', '441500', '海丰县', '115.322974,22.967212', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441523', '441500', '陆河县', '115.659978,23.301557', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441581', '441500', '陆丰市', '115.652142,22.91874', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441600', '440000', '河源市', '114.700215,23.744276', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441602', '441600', '源城区', '114.703305,23.734055', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441621', '441600', '紫金县', '115.183814,23.635597', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441622', '441600', '龙川县', '115.26002,24.100599', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441623', '441600', '连平县', '114.488358,24.369552', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441624', '441600', '和平县', '114.935634,24.469177', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441625', '441600', '东源县', '114.7466,23.790079', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441700', '440000', '阳江市', '111.98343,21.856853', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441702', '441700', '江城区', '111.95486,21.862451', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441704', '441700', '阳东区', '112.005586,21.869081', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441721', '441700', '阳西县', '111.617696,21.753935', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441781', '441700', '阳春市', '111.791587,22.17041', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441800', '440000', '清远市', '113.056098,23.682064', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441802', '441800', '清城区', '113.062612,23.697889', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441803', '441800', '清新区', '113.017747,23.734677', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441821', '441800', '佛冈县', '113.531559,23.879455', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441823', '441800', '阳山县', '112.641199,24.465234', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441825', '441800', '连山壮族瑶族自治县', '112.093726,24.570553', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441826', '441800', '连南瑶族自治县', '112.287212,24.725953', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441881', '441800', '英德市', '113.401827,24.20716', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441882', '441800', '连州市', '112.377255,24.780873', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('441900', '440000', '东莞市', '113.751884,23.021016', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('442000', '440000', '中山市', '113.392517,22.517024', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445100', '440000', '潮州市', '116.621901,23.657662', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445102', '445100', '湘桥区', '116.628343,23.675104', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445103', '445100', '潮安区', '116.676971,23.463598', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445122', '445100', '饶平县', '117.0045,23.663294', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445200', '440000', '揭阳市', '116.372732,23.550968', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445202', '445200', '榕城区', '116.36714,23.525918', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445203', '445200', '揭东区', '116.411928,23.567252', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445222', '445200', '揭西县', '115.841742,23.431314', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445224', '445200', '惠来县', '116.295038,23.033889', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445281', '445200', '普宁市', '116.165646,23.297742', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445300', '440000', '云浮市', '112.044524,22.915163', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445302', '445300', '云城区', '112.043945,22.92815', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445303', '445300', '云安区', '112.002947,23.070334', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445321', '445300', '新兴县', '112.225174,22.695915', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445322', '445300', '郁南县', '111.535387,23.234561', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('445381', '445300', '罗定市', '111.569788,22.768345', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450000', '100000', '广西壮族自治区', '108.327537,22.816659', 'province', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450100', '450000', '南宁市', '108.366407,22.8177', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450102', '450100', '兴宁区', '108.368071,22.85354', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450103', '450100', '青秀区', '108.495204,22.785833', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450105', '450100', '江南区', '108.273206,22.781166', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450107', '450100', '西乡塘区', '108.31344,22.833852', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450108', '450100', '良庆区', '108.393889,22.753613', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450109', '450100', '邕宁区', '108.487438,22.758633', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450110', '450100', '武鸣区', '108.274869,23.159257', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450123', '450100', '隆安县', '107.695721,23.166356', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450124', '450100', '马山县', '108.177764,23.708448', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450125', '450100', '上林县', '108.605089,23.432451', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450126', '450100', '宾阳县', '108.810336,23.217771', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450181', '450100', '横州市', '109.262448,22.681257', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450200', '450000', '柳州市', '109.428071,24.326442', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450202', '450200', '城中区', '109.427398,24.366964', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450203', '450200', '鱼峰区', '109.452781,24.318276', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450204', '450200', '柳南区', '109.385504,24.336229', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450205', '450200', '柳北区', '109.402009,24.362611', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450206', '450200', '柳江区', '109.326365,24.255644', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450222', '450200', '柳城县', '109.267125,24.654329', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450223', '450200', '鹿寨县', '109.750527,24.472897', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450224', '450200', '融安县', '109.397538,25.224549', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450225', '450200', '融水苗族自治县', '109.256609,25.066624', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450226', '450200', '三江侗族自治县', '109.607675,25.783197', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450300', '450000', '桂林市', '110.179752,25.235615', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450302', '450300', '秀峰区', '110.264102,25.273954', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450303', '450300', '叠彩区', '110.301489,25.314158', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450304', '450300', '象山区', '110.281223,25.261585', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450305', '450300', '七星区', '110.317576,25.253093', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450311', '450300', '雁山区', '110.286611,25.101798', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450312', '450300', '临桂区', '110.212425,25.238556', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450321', '450300', '阳朔县', '110.496305,24.778785', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450323', '450300', '灵川县', '110.319837,25.3948', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450324', '450300', '全州县', '111.087089,25.94939', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450325', '450300', '兴安县', '110.672013,25.612576', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450326', '450300', '永福县', '109.982974,24.980447', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450327', '450300', '灌阳县', '111.160764,25.489419', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450328', '450300', '龙胜各族自治县', '110.011662,25.79803', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450329', '450300', '资源县', '110.652612,26.042452', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450330', '450300', '平乐县', '110.632867,24.622814', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450332', '450300', '恭城瑶族自治县', '110.82841,24.831581', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450381', '450300', '荔浦市', '110.395232,24.48843', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450400', '450000', '梧州市', '111.279022,23.476733', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450403', '450400', '万秀区', '111.318065,23.470543', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450405', '450400', '长洲区', '111.274276,23.486279', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450406', '450400', '龙圩区', '111.247514,23.414869', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450421', '450400', '苍梧县', '111.547244,23.868208', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450422', '450400', '藤县', '110.914606,23.375538', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450423', '450400', '蒙山县', '110.525003,24.193567', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450481', '450400', '岑溪市', '110.994955,22.918327', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450500', '450000', '北海市', '109.120248,21.481305', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450502', '450500', '海城区', '109.117256,21.474914', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450503', '450500', '银海区', '109.139689,21.449729', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450512', '450500', '铁山港区', '109.422099,21.529878', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450521', '450500', '合浦县', '109.207236,21.66132', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450600', '450000', '防城港市', '108.35467,21.686732', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450602', '450600', '港口区', '108.380273,21.643426', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450603', '450600', '防城区', '108.353978,21.768826', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450621', '450600', '上思县', '107.983627,22.153671', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450681', '450600', '东兴市', '107.971828,21.547821', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450700', '450000', '钦州市', '108.654355,21.980894', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450702', '450700', '钦南区', '108.657427,21.940438', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450703', '450700', '钦北区', '108.638738,22.0024', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450721', '450700', '灵山县', '109.290698,22.416671', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450722', '450700', '浦北县', '109.556232,22.271902', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450800', '450000', '贵港市', '109.598903,23.11182', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450802', '450800', '港北区', '109.572227,23.11136', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450803', '450800', '港南区', '109.599357,23.075816', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450804', '450800', '覃塘区', '109.452668,23.127405', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450821', '450800', '平南县', '110.392489,23.540413', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450881', '450800', '桂平市', '110.079315,23.394208', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450900', '450000', '玉林市', '110.18097,22.654001', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450902', '450900', '玉州区', '110.150717,22.628476', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450903', '450900', '福绵区', '110.059564,22.585316', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450921', '450900', '容县', '110.558093,22.85844', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450922', '450900', '陆川县', '110.26412,22.321563', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450923', '450900', '博白县', '109.975856,22.273539', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450924', '450900', '兴业县', '109.875223,22.736395', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('450981', '450900', '北流市', '110.353765,22.708415', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451000', '450000', '百色市', '106.61869,23.90307', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451002', '451000', '右江区', '106.618151,23.900804', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451003', '451000', '田阳区', '106.915424,23.735631', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451022', '451000', '田东县', '107.125609,23.596706', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451024', '451000', '德保县', '106.615087,23.324084', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451026', '451000', '那坡县', '105.83234,23.387997', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451027', '451000', '凌云县', '106.56114,24.347444', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451028', '451000', '乐业县', '106.556564,24.776812', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451029', '451000', '田林县', '106.22874,24.294627', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451030', '451000', '西林县', '105.097229,24.50762', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451031', '451000', '隆林各族自治县', '105.343816,24.770649', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451081', '451000', '靖西市', '106.417666,23.134375', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451082', '451000', '平果市', '107.589768,23.329815', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451100', '450000', '贺州市', '111.567216,24.404182', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451102', '451100', '八步区', '111.552029,24.411822', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451103', '451100', '平桂区', '111.479839,24.453094', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451121', '451100', '昭平县', '110.811158,24.169675', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451122', '451100', '钟山县', '111.303085,24.526041', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451123', '451100', '富川瑶族自治县', '111.296012,24.811601', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451200', '450000', '河池市', '108.085244,24.692906', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451202', '451200', '金城江区', '108.037384,24.689475', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451203', '451200', '宜州区', '108.611303,24.500981', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451221', '451200', '南丹县', '107.540578,24.975066', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451222', '451200', '天峨县', '107.172287,24.999181', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451223', '451200', '凤山县', '107.042157,24.546913', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451224', '451200', '东兰县', '107.374259,24.510698', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451225', '451200', '罗城仫佬族自治县', '108.904613,24.777501', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451226', '451200', '环江毛南族自治县', '108.258426,24.825923', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451227', '451200', '巴马瑶族自治县', '107.258671,24.142216', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451228', '451200', '都安瑶族自治县', '108.105229,23.932704', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451229', '451200', '大化瑶族自治县', '107.998151,23.735699', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451300', '450000', '来宾市', '109.221243,23.750105', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451302', '451300', '兴宾区', '109.183284,23.727647', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451321', '451300', '忻城县', '108.665641,24.066176', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451322', '451300', '象州县', '109.704852,23.973832', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451323', '451300', '武宣县', '109.663153,23.594444', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451324', '451300', '金秀瑶族自治县', '110.190354,24.130509', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451381', '451300', '合山市', '108.885829,23.807032', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451400', '450000', '崇左市', '107.364973,22.377139', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451402', '451400', '江州区', '107.353694,22.40609', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451421', '451400', '扶绥县', '107.90389,22.635542', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451422', '451400', '宁明县', '107.076394,22.140242', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451423', '451400', '龙州县', '106.854001,22.343591', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451424', '451400', '大新县', '107.200654,22.829287', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451425', '451400', '天等县', '107.143539,23.080818', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('451481', '451400', '凭祥市', '106.766715,22.094421', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460000', '100000', '海南省', '110.348781,20.018639', 'province', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460100', '460000', '海口市', '110.198418,20.045805', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460105', '460100', '秀英区', '110.293566,20.007703', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460106', '460100', '龙华区', '110.328628,20.030843', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460107', '460100', '琼山区', '110.384318,19.984293', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460108', '460100', '美兰区', '110.366359,20.028983', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460200', '460000', '三亚市', '109.511709,18.252865', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460202', '460200', '海棠区', '109.735676,18.384177', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460203', '460200', '吉阳区', '109.578238,18.28146', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460204', '460200', '天涯区', '109.452325,18.298975', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460205', '460200', '崖州区', '109.172298,18.357572', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460300', '460000', '三沙市', '112.338649,16.831004', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460301', '460300', '西沙区', '112.346961,16.834372', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460302', '460300', '南沙区', '112.896229,9.548531', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('460400', '460000', '儋州市', '109.580812,19.520948', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469001', '460000', '五指山市', '109.516784,18.774827', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469002', '460000', '琼海市', '110.474524,19.259112', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469005', '460000', '文昌市', '110.797473,19.544234', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469006', '460000', '万宁市', '110.392605,18.793697', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469007', '460000', '东方市', '108.651829,19.095187', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469021', '460000', '定安县', '110.358001,19.681215', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469022', '460000', '屯昌县', '110.101667,19.351662', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469023', '460000', '澄迈县', '110.007497,19.738885', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469024', '460000', '临高县', '109.690508,19.912025', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469025', '460000', '白沙黎族自治县', '109.4429,19.221641', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469026', '460000', '昌江黎族自治县', '109.055783,19.298139', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469027', '460000', '乐东黎族自治县', '109.173384,18.750063', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469028', '460000', '陵水黎族自治县', '110.037553,18.506045', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469029', '460000', '保亭黎族苗族自治县', '109.700279,18.640339', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('469030', '460000', '琼中黎族苗族自治县', '109.838423,19.03327', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520000', '100000', '贵州省', '106.705251,26.600328', 'province', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520100', '520000', '贵阳市', '106.628201,26.646694', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520102', '520100', '南明区', '106.714305,26.568055', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520103', '520100', '云岩区', '106.724394,26.604604', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520111', '520100', '花溪区', '106.67026,26.409817', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520112', '520100', '乌当区', '106.75069,26.630911', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520113', '520100', '白云区', '106.623069,26.677932', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520115', '520100', '观山湖区', '106.598978,26.616134', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520121', '520100', '开阳县', '106.964716,27.057823', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520122', '520100', '息烽县', '106.740407,27.090479', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520123', '520100', '修文县', '106.591958,26.836048', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520181', '520100', '清镇市', '106.468686,26.570435', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520200', '520000', '六盘水市', '104.830357,26.592538', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520201', '520200', '钟山区', '104.843723,26.574699', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520203', '520200', '六枝特区', '105.477199,26.214356', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520204', '520200', '水城区', '104.957871,26.547604', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520281', '520200', '盘州市', '104.471554,25.709878', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520300', '520000', '遵义市', '107.031922,27.721931', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520302', '520300', '红花岗区', '106.893598,27.644793', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520303', '520300', '汇川区', '106.933727,27.749716', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520304', '520300', '播州区', '106.829081,27.535735', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520322', '520300', '桐梓县', '106.824661,28.132991', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520323', '520300', '绥阳县', '107.191326,27.946049', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520324', '520300', '正安县', '107.454463,28.553507', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520325', '520300', '道真仡佬族苗族自治县', '107.613076,28.862548', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520326', '520300', '务川仡佬族苗族自治县', '107.899353,28.562921', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520327', '520300', '凤冈县', '107.71646,27.954424', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520328', '520300', '湄潭县', '107.465492,27.748942', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520329', '520300', '余庆县', '107.906043,27.21513', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520330', '520300', '习水县', '106.19715,28.332923', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520381', '520300', '赤水市', '105.697501,28.590474', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520382', '520300', '仁怀市', '106.401322,27.791883', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520400', '520000', '安顺市', '105.9476,26.253103', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520402', '520400', '西秀区', '105.966086,26.243928', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520403', '520400', '平坝区', '106.255768,26.405968', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520422', '520400', '普定县', '105.743156,26.301876', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520423', '520400', '镇宁布依族苗族自治县', '105.770543,26.057248', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520424', '520400', '关岭布依族苗族自治县', '105.538335,25.953518', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520425', '520400', '紫云苗族布依族自治县', '106.084441,25.751047', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520500', '520000', '毕节市', '105.291544,27.283615', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520502', '520500', '七星关区', '105.305219,27.298304', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520521', '520500', '大方县', '105.601963,27.14443', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520523', '520500', '金沙县', '106.220112,27.458601', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520524', '520500', '织金县', '105.770249,26.663649', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520525', '520500', '纳雍县', '105.414578,26.779344', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520526', '520500', '威宁彝族回族苗族自治县', '104.252787,26.873872', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520527', '520500', '赫章县', '104.728011,27.117933', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520581', '520500', '黔西市', '106.032277,27.008681', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520600', '520000', '铜仁市', '109.189528,27.731555', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520602', '520600', '碧江区', '109.264271,27.815244', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520603', '520600', '万山区', '109.153685,27.671268', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520621', '520600', '江口县', '108.843993,27.704883', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520622', '520600', '玉屏侗族自治县', '108.906415,27.235816', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520623', '520600', '石阡县', '108.223686,27.513499', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520624', '520600', '思南县', '108.253798,27.937464', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520625', '520600', '印江土家族苗族自治县', '108.409638,27.994442', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520626', '520600', '德江县', '108.120773,28.264028', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520627', '520600', '沿河土家族自治县', '108.503152,28.564083', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('520628', '520600', '松桃苗族自治县', '109.202877,28.15427', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522300', '520000', '黔西南布依族苗族自治州', '104.906419,25.087733', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522301', '522300', '兴义市', '104.895503,25.09196', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522302', '522300', '兴仁市', '105.186132,25.43509', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522323', '522300', '普安县', '104.953289,25.784225', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522324', '522300', '晴隆县', '105.218956,25.834729', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522325', '522300', '贞丰县', '105.64976,25.38558', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522326', '522300', '望谟县', '106.094864,25.174204', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522327', '522300', '册亨县', '105.811592,24.983663', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522328', '522300', '安龙县', '105.442702,25.099014', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522600', '520000', '黔东南苗族侗族自治州', '107.982838,26.583759', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522601', '522600', '凯里市', '107.981409,26.5662', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522622', '522600', '黄平县', '107.916651,26.905278', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522623', '522600', '施秉县', '108.124531,27.033107', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522624', '522600', '三穗县', '108.675265,26.953359', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522625', '522600', '镇远县', '108.429536,27.049033', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522626', '522600', '岑巩县', '108.815855,27.174124', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522627', '522600', '天柱县', '109.207826,26.909548', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522628', '522600', '锦屏县', '109.200808,26.675919', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522629', '522600', '剑河县', '108.441501,26.728274', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522630', '522600', '台江县', '108.321244,26.667525', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522631', '522600', '黎平县', '109.125826,26.213304', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522632', '522600', '榕江县', '108.52188,25.931893', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522633', '522600', '从江县', '108.904998,25.754638', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522634', '522600', '雷山县', '108.078217,26.378892', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522635', '522600', '麻江县', '107.589455,26.49129', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522636', '522600', '丹寨县', '107.789301,26.19857', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522700', '520000', '黔南布依族苗族自治州', '107.522303,26.253136', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522701', '522700', '都匀市', '107.518628,26.259456', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522702', '522700', '福泉市', '107.520371,26.686773', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522722', '522700', '荔波县', '107.88057,25.402528', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522723', '522700', '贵定县', '107.232208,26.557205', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522725', '522700', '瓮安县', '107.471307,27.07838', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522726', '522700', '独山县', '107.557591,25.865418', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522727', '522700', '平塘县', '107.322635,25.822395', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522728', '522700', '罗甸县', '106.751724,25.42616', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522729', '522700', '长顺县', '106.44057,26.003187', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522730', '522700', '龙里县', '106.979177,26.453546', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522731', '522700', '惠水县', '106.656993,26.132045', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('522732', '522700', '三都水族自治县', '107.869489,25.983572', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530000', '100000', '云南省', '102.709372,25.046432', 'province', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530100', '530000', '昆明市', '102.833669,24.88149', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530102', '530100', '五华区', '102.707262,25.043635', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530103', '530100', '盘龙区', '102.751643,25.116512', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530111', '530100', '官渡区', '102.748888,24.950285', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530112', '530100', '西山区', '102.664426,25.038039', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530113', '530100', '东川区', '103.187825,26.082997', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530114', '530100', '呈贡区', '102.822104,24.885738', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530115', '530100', '晋宁区', '102.595325,24.669077', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530124', '530100', '富民县', '102.497722,25.221924', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530125', '530100', '宜良县', '103.141674,24.91983', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530126', '530100', '石林彝族自治县', '103.290536,24.771761', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530127', '530100', '嵩明县', '103.043384,25.327273', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530128', '530100', '禄劝彝族苗族自治县', '102.471993,25.551768', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530129', '530100', '寻甸回族彝族自治县', '103.256559,25.558163', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530181', '530100', '安宁市', '102.47865,24.919831', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530300', '530000', '曲靖市', '103.796288,25.490866', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530302', '530300', '麒麟区', '103.804406,25.496472', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530303', '530300', '沾益区', '103.822104,25.600424', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530304', '530300', '马龙区', '103.578459,25.428102', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530322', '530300', '陆良县', '103.66671,25.0293', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530323', '530300', '师宗县', '103.985224,24.822471', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530324', '530300', '罗平县', '104.297124,24.865388', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530325', '530300', '富源县', '104.255082,25.674217', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530326', '530300', '会泽县', '103.297155,26.417116', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530381', '530300', '宣威市', '104.104255,26.218956', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530400', '530000', '玉溪市', '102.526673,24.346786', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530402', '530400', '红塔区', '102.540122,24.341215', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530403', '530400', '江川区', '102.748499,24.299441', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530423', '530400', '通海县', '102.725538,24.11114', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530424', '530400', '华宁县', '102.928914,24.19322', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530425', '530400', '易门县', '102.161947,24.672156', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530426', '530400', '峨山彝族自治县', '102.405698,24.168899', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530427', '530400', '新平彝族傣族自治县', '101.990805,24.070436', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530428', '530400', '元江哈尼族彝族傣族自治县', '101.998138,23.596068', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530481', '530400', '澄江市', '102.904181,24.675536', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530500', '530000', '保山市', '99.161489,25.112018', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530502', '530500', '隆阳区', '99.165638,25.121137', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530521', '530500', '施甸县', '99.18919,24.723084', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530523', '530500', '龙陵县', '98.68941,24.586703', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530524', '530500', '昌宁县', '99.605105,24.827739', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530581', '530500', '腾冲市', '98.490276,25.020283', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530600', '530000', '昭通市', '103.717078,27.338185', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530602', '530600', '昭阳区', '103.706323,27.320035', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530621', '530600', '鲁甸县', '103.557969,27.186668', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530622', '530600', '巧家县', '102.935343,26.896904', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530623', '530600', '盐津县', '104.234458,28.108475', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530624', '530600', '大关县', '103.891164,27.748054', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530625', '530600', '永善县', '103.63756,28.229018', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530626', '530600', '绥江县', '103.968995,28.592119', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530627', '530600', '镇雄县', '104.873486,27.441527', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530628', '530600', '彝良县', '104.055991,27.624277', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530629', '530600', '威信县', '105.049012,27.846839', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530681', '530600', '水富市', '104.415964,28.629951', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530700', '530000', '丽江市', '100.225936,26.855165', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530702', '530700', '古城区', '100.22583,26.876468', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530721', '530700', '玉龙纳西族自治县', '100.236967,26.821494', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530722', '530700', '永胜县', '100.750907,26.684215', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530723', '530700', '华坪县', '101.265373,26.629598', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530724', '530700', '宁蒗彝族自治县', '100.851083,27.282207', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530800', '530000', '普洱市', '100.966011,22.825229', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530802', '530800', '思茅区', '100.977069,22.786769', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530821', '530800', '宁洱哈尼族彝族自治县', '101.045743,23.048809', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530822', '530800', '墨江哈尼族自治县', '101.692461,23.431894', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530823', '530800', '景东彝族自治县', '100.833877,24.446731', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530824', '530800', '景谷傣族彝族自治县', '100.702807,23.496987', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530825', '530800', '镇沅彝族哈尼族拉祜族自治县', '101.108733,24.00445', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530826', '530800', '江城哈尼族彝族自治县', '101.862344,22.585858', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530827', '530800', '孟连傣族拉祜族佤族自治县', '99.584225,22.329053', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530828', '530800', '澜沧拉祜族自治县', '99.932045,22.555799', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530829', '530800', '西盟佤族自治县', '99.59016,22.644237', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530900', '530000', '临沧市', '100.088837,23.884175', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530902', '530900', '临翔区', '100.082073,23.895298', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530921', '530900', '凤庆县', '99.92873,24.580559', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530922', '530900', '云县', '100.1303,24.444461', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530923', '530900', '永德县', '99.258702,24.018463', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530924', '530900', '镇康县', '98.825389,23.762886', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530925', '530900', '双江拉祜族佤族布朗族傣族自治县', '99.828225,23.472719', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530926', '530900', '耿马傣族佤族自治县', '99.397126,23.538092', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('530927', '530900', '沧源佤族自治县', '99.245894,23.146758', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532300', '530000', '楚雄彝族自治州', '101.528304,25.045678', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532301', '532300', '楚雄市', '101.546242,25.032945', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532302', '532300', '禄丰市', '102.079082,25.151061', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532322', '532300', '双柏县', '101.642369,24.688814', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532323', '532300', '牟定县', '101.546898,25.312939', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532324', '532300', '南华县', '101.283236,25.200163', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532325', '532300', '姚安县', '101.241632,25.504287', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532326', '532300', '大姚县', '101.336576,25.729551', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532327', '532300', '永仁县', '101.666795,26.049522', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532328', '532300', '元谋县', '101.87434,25.704499', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532329', '532300', '武定县', '102.403949,25.530731', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532500', '530000', '红河哈尼族彝族自治州', '103.374873,23.363129', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532501', '532500', '个旧市', '103.152663,23.389935', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532502', '532500', '开远市', '103.266908,23.714518', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532503', '532500', '蒙自市', '103.364936,23.396111', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532504', '532500', '弥勒市', '103.414817,24.411774', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532523', '532500', '屏边苗族自治县', '103.675458,22.986733', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532524', '532500', '建水县', '102.826178,23.635824', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532525', '532500', '石屏县', '102.496138,23.705707', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532527', '532500', '泸西县', '103.76615,24.531981', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532528', '532500', '元阳县', '102.835358,23.219671', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532529', '532500', '红河县', '102.420566,23.368946', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532530', '532500', '金平苗族瑶族傣族自治县', '103.227982,22.769894', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532531', '532500', '绿春县', '102.392655,22.993654', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532532', '532500', '河口瑶族自治县', '103.939265,22.529438', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532600', '530000', '文山壮族苗族自治州', '104.21567,23.400983', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532601', '532600', '文山市', '104.233237,23.38683', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532622', '532600', '砚山县', '104.336905,23.605075', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532623', '532600', '西畴县', '104.671802,23.437707', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532624', '532600', '麻栗坡县', '104.702732,23.125837', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532625', '532600', '马关县', '104.394524,23.013108', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532626', '532600', '丘北县', '104.166713,24.05064', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532627', '532600', '广南县', '105.055075,24.046378', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532628', '532600', '富宁县', '105.630921,23.625072', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532800', '530000', '西双版纳傣族自治州', '100.797002,22.009037', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532801', '532800', '景洪市', '100.799595,22.011792', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532822', '532800', '勐海县', '100.452444,21.957323', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532823', '532800', '勐腊县', '101.564635,21.459233', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532900', '530000', '大理白族自治州', '100.267608,25.606548', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532901', '532900', '大理市', '100.301614,25.678466', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532922', '532900', '漾濞彝族自治县', '99.958089,25.669944', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532923', '532900', '祥云县', '100.549961,25.483727', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532924', '532900', '宾川县', '100.590274,25.830491', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532925', '532900', '弥渡县', '100.491038,25.343778', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532926', '532900', '南涧彝族自治县', '100.510333,25.032353', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532927', '532900', '巍山彝族回族自治县', '100.306977,25.227065', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532928', '532900', '永平县', '99.54063,25.464134', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532929', '532900', '云龙县', '99.371021,25.885733', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532930', '532900', '洱源县', '99.962294,26.11337', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532931', '532900', '剑川县', '99.905041,26.536889', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('532932', '532900', '鹤庆县', '100.176331,26.560122', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533100', '530000', '德宏傣族景颇族自治州', '98.585621,24.433146', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533102', '533100', '瑞丽市', '97.855423,24.018377', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533103', '533100', '芒市', '98.58809,24.433766', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533122', '533100', '梁河县', '98.296584,24.804275', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533123', '533100', '盈江县', '97.943474,24.691325', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533124', '533100', '陇川县', '97.793359,24.182347', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533300', '530000', '怒江傈僳族自治州', '98.8566,25.817555', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533301', '533300', '泸水市', '98.857723,25.822579', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533323', '533300', '福贡县', '98.869132,26.901831', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533324', '533300', '贡山独龙族怒族自治县', '98.666279,27.740839', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533325', '533300', '兰坪白族普米族自治县', '99.416628,26.453622', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533400', '530000', '迪庆藏族自治州', '99.70211,27.819149', 'city', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533401', '533400', '香格里拉市', '99.743582,27.842185', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533422', '533400', '德钦县', '98.917851,28.464213', 'district', NULL, '2022-10-24 14:22:26');
INSERT INTO `biz_district` (`ad_code`, `parent_ad_code`, `name`, `center`, `level`, `create_user_id`, `create_time`) VALUES ('533423', '533400', '维西傈僳族自治县', '99.300937,27.163808', 'district', NULL, '2022-10-24 14:22:26');
COMMIT;

-- ----------------------------
-- Table structure for biz_encrypt_columns
-- ----------------------------
DROP TABLE IF EXISTS `biz_encrypt_columns`;
CREATE TABLE `biz_encrypt_columns` (
  `id` int(11) NOT NULL,
  `table_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '表名',
  `column_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='加密字段记录表';

-- ----------------------------
-- Records of biz_encrypt_columns
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_message_info
-- ----------------------------
DROP TABLE IF EXISTS `biz_message_info`;
CREATE TABLE `biz_message_info` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `message_type` smallint(6) NOT NULL DEFAULT '1' COMMENT '消息分类(1消息/站内信；5公告；9其他)',
  `message_priority` smallint(6) NOT NULL DEFAULT '1' COMMENT '消息优先级(1普通；5紧急)',
  `message_status` smallint(6) NOT NULL DEFAULT '0' COMMENT '消息状态(0暂存；1下发)',
  `range_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '发送范围类型(1全部用户；2指定单位/部门；3自定义)',
  `range_str` text COLLATE utf8mb4_unicode_ci COMMENT '发送范围明细',
  `biz_record_type` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务场景分类(冗余字段，分区不同业务下发的消息)',
  `biz_record_id` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务场景业务id',
  `biz_url` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务跳转链接地址',
  `biz_remark` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务根据需要存储其他内容',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息通知信息表';

-- ----------------------------
-- Records of biz_message_info
-- ----------------------------
BEGIN;
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1829088813677850624', '测试', '测试', 1, 1, 0, 2, NULL, 'aaa', '111', '3', NULL, '1183307967865425920', '2024-08-29 17:28:51', '1183307967865425920', '2024-11-07 18:49:56', 0, NULL);
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1829089420132265984', '测试2', '测试2', 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '1183307967865425920', '2024-08-29 17:31:15', NULL, NULL, 0, NULL);
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1829092655952474112', '测试31', '测试31', 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '1183307967865425920', '2024-08-29 17:44:07', '1183307967865425920', '2024-08-29 17:44:16', 0, NULL);
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1830923124511780864', '这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试', '这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试；这是一个测试。', 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '1183307967865425920', '2024-09-03 18:57:44', '1183307967865425920', '2024-09-03 18:59:22', 0, NULL);
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1830923763870511104', '测试一下', '测试一下测试一下测试一下', 1, 1, 1, 1, NULL, NULL, NULL, '/sys/dict', NULL, '1183307967865425920', '2024-09-03 19:00:17', '1183307967865425920', '2024-09-03 19:00:29', 0, NULL);
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1854691424879161344', '测试2', '测试2', 1, 1, 1, 1, NULL, 'aaa', '111', '3', NULL, '1183307967865425920', '2024-11-08 09:04:29', '1183307967865425920', '2024-11-08 09:04:49', 0, NULL);
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1995753422532907008', '消息测试', '消息测试', 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '1183307967865425920', '2025-12-02 15:14:30', '1183307967865425920', '2025-12-02 15:14:40', 0, NULL);
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1995753628225769472', '公告测试', '公告测试', 5, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '1183307967865425920', '2025-12-02 15:15:19', '1183307967865425920', '2025-12-02 15:15:25', 0, NULL);
INSERT INTO `biz_message_info` (`id`, `title`, `content`, `message_type`, `message_priority`, `message_status`, `range_type`, `range_str`, `biz_record_type`, `biz_record_id`, `biz_url`, `biz_remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `del_time`) VALUES ('1995755677176827904', '公告测试2', '公告测试2', 5, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '1183307967865425920', '2025-12-02 15:23:27', NULL, NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for biz_message_org
-- ----------------------------
DROP TABLE IF EXISTS `biz_message_org`;
CREATE TABLE `biz_message_org` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `message_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息表id',
  `org_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位/部门id',
  `org_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位/部门名称',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息下发单位表';

-- ----------------------------
-- Records of biz_message_org
-- ----------------------------
BEGIN;
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293376', '1829088813677850624', '1699335117679538176', 'A公司', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293377', '1829088813677850624', '1699335158242652160', '营销部', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293378', '1829088813677850624', '1699335158448173056', '人事部', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293379', '1829088813677850624', '1699335164840292352', '市场部', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293380', '1829088813677850624', '1699335165037424640', '销售部', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293381', '1829088813677850624', '1699335172998213632', '招聘部', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293382', '1829088813677850624', '1699335177926520832', '培训部', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293383', '1829088813677850624', '1699335177976852480', '市场推广组', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293384', '1829088813677850624', '1699335266766073856', '销售团队', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854476369944293385', '1829088813677850624', '1699335266992566272', '招聘团队', '1183307967865425920', '2024-11-07 18:49:56');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774208', '1854691424879161344', '1699335117679538176', 'A公司', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774209', '1854691424879161344', '1699335158242652160', '营销部', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774210', '1854691424879161344', '1699335158448173056', '人事部', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774211', '1854691424879161344', '1699335164840292352', '市场部', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774212', '1854691424879161344', '1699335165037424640', '销售部', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774213', '1854691424879161344', '1699335172998213632', '招聘部', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774214', '1854691424879161344', '1699335177926520832', '培训部', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774215', '1854691424879161344', '1699335177976852480', '市场推广组', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774216', '1854691424879161344', '1699335266766073856', '销售团队', '1183307967865425920', '2024-11-08 09:04:49');
INSERT INTO `biz_message_org` (`id`, `message_id`, `org_id`, `org_name`, `create_user_id`, `create_time`) VALUES ('1854691507783774217', '1854691424879161344', '1699335266992566272', '招聘团队', '1183307967865425920', '2024-11-08 09:04:49');
COMMIT;

-- ----------------------------
-- Table structure for biz_message_user
-- ----------------------------
DROP TABLE IF EXISTS `biz_message_user`;
CREATE TABLE `biz_message_user` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `message_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息表id',
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  `user_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户姓名',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息下发人员表';

-- ----------------------------
-- Records of biz_message_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_message_user_read
-- ----------------------------
DROP TABLE IF EXISTS `biz_message_user_read`;
CREATE TABLE `biz_message_user_read` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `message_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息表id',
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息人员已读记录表';

-- ----------------------------
-- Records of biz_message_user_read
-- ----------------------------
BEGIN;
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1', '1', '1', '1', '2024-08-28 15:58:59');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1830864895852457984', '1829089420132265984', '1183307967865425920', '1183307967865425920', '2024-09-03 15:06:22');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1830868538078703616', '1829092655952474112', '1183307967865425920', '1183307967865425920', '2024-09-03 15:20:50');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1830873909103079424', '1', '1183307967865425920', '1183307967865425920', '2024-09-03 15:42:11');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1830885067309887488', '4', '1183307967865425920', '1183307967865425920', '2024-09-03 16:26:31');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1830887651231838208', '2', '1183307967865425920', '1183307967865425920', '2024-09-03 16:36:47');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1830923579677650944', '1830923124511780864', '1183307967865425920', '1183307967865425920', '2024-09-03 18:59:33');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1830923831851790336', '1830923763870511104', '1183307967865425920', '1183307967865425920', '2024-09-03 19:00:33');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1854691545423458304', '1854691424879161344', '1183307967865425920', '1183307967865425920', '2024-11-08 09:04:58');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1947846086327709696', '1854691424879161344', '1777651004320428032', '1777651004320428032', '2025-07-23 10:27:51');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1995753497992630272', '1995753422532907008', '1183307967865425920', '1183307967865425920', '2025-12-02 15:14:48');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1995755699876401152', '1995755677176827904', '1183307967865425920', '1183307967865425920', '2025-12-02 15:23:33');
INSERT INTO `biz_message_user_read` (`id`, `message_id`, `user_id`, `create_user_id`, `create_time`) VALUES ('1995755703286370304', '1995753628225769472', '1183307967865425920', '1183307967865425920', '2025-12-02 15:23:34');
COMMIT;

-- ----------------------------
-- Table structure for biz_otp_log
-- ----------------------------
DROP TABLE IF EXISTS `biz_otp_log`;
CREATE TABLE `biz_otp_log` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `realname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `operation` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作描述',
  `ip` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'IP地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='otp验证码校验成功日志记录表';

-- ----------------------------
-- Records of biz_otp_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for demo_user
-- ----------------------------
DROP TABLE IF EXISTS `demo_user`;
CREATE TABLE `demo_user` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `realname` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `age` smallint(6) DEFAULT NULL COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `id_card` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号码（AES加密）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户测试表';

-- ----------------------------
-- Records of demo_user
-- ----------------------------
BEGIN;
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('1', '11669321995', '张三', 28, '1996-06-17', '0CECA0B31E3AFBC953C3F1441C4E8BBE65A2CC30700E4961D3C466E11D4AF925', '2019-08-10 22:11:17', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('10', '16642131821', '麻子', 18, '1996-06-17', 'E9D086231C6C877972E8D7C26DD13EDF09633E4D3D0BAA3E132412F7B4D020F3', '2019-10-17 21:50:52', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('11', '16674548152', '测试', 18, '1996-06-17', '12CD30E5A16EC32E2ACE016CF9D39BC554DCBCFA6B59E23CAF89A70A49A5EE50', '2020-02-19 17:47:30', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('12', '16642132230', 'test', 18, '1991-06-16', '1A36A601816C0D6AE8D798D3A885891E6735511324EE5B769F47636D75DF224C', '2020-02-19 17:50:52', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('13', '16641708355', '张三', 18, '2020-03-15', 'F214642CEE6FE70E0798C61153E7F71B98B05F8CC4A9963A9BD03F7E46E09EDE', '2020-03-15 15:31:33', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('15', '16623229479', '王五', 18, '1996-06-17', '61BD87C9A2735AA7EF7401D53BF5CF66AE2869ADE1DB796A5C691E76278CD30D', '2020-03-15 17:47:41', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('18', 'test', '张三', 18, '2021-09-02', '249ED0119AABCF3C1AAED323BB33B9ACD1075542D17C5A1B628875D75A6BCF9B', '2021-09-14 15:10:08', '2021-09-14 15:10:13', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('1802714763794755584', '18612341233', '李啊', 23, '1991-01-01', NULL, '2024-06-17 22:47:47', NULL, 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('1802717525379035136', '18612344444', '李思', 18, '2024-06-04', '496E851A36DBEAC34FA30C5796089B02', '2024-06-17 22:58:45', '2024-06-17 22:58:46', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('19', '18688498342', '测测', 18, '1989-12-01', NULL, '2022-04-07 17:32:42', NULL, 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('1983707759915831296', '18637231234', '测试啊', 20, '2020-01-01', '249ED0119AABCF3C1AAED323BB33B9ACF3C86199147DAA8569B874D27555A859', '2025-10-30 09:29:20', '2025-10-30 09:29:41', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('1983712766706978816', '18637231235', '小米', 20, '2020-01-01', '249ED0119AABCF3C1AAED323BB33B9ACF3C86199147DAA8569B874D27555A859', '2025-10-30 09:49:13', '2025-10-30 10:39:33', 1);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('1983712766706978817', '18637231236', '大米', 20, '2020-01-01', '249ED0119AABCF3C1AAED323BB33B9ACF3C86199147DAA8569B874D27555A859', '2025-10-30 09:49:13', '2025-10-30 10:39:35', 1);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('1983725497183637504', '18637231235', '小米', 20, '2020-01-01', '249ED0119AABCF3C1AAED323BB33B9ACF3C86199147DAA8569B874D27555A859', '2025-10-30 10:39:49', NULL, 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('1983725497183637505', '18637231236', '大米', 20, '2020-01-01', '249ED0119AABCF3C1AAED323BB33B9ACF3C86199147DAA8569B874D27555A859', '2025-10-30 10:39:49', NULL, 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('2', '11662193208', '李四', 18, '1996-06-17', '805F3DFC6129A543E231C5735619E64D4E49859C3FC114FAECF1EFA01980953E', '2019-08-10 22:29:28', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('20', '18612344321', '测试', 22, '2020-01-08', '496E851A36DBEAC34FA30C5796089B02', '2023-03-29 18:54:12', NULL, 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('21', '18612344321', '测试', 22, '2020-01-08', '496E851A36DBEAC34FA30C5796089B02', '2023-03-29 18:54:39', NULL, 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('3', '11666879288', '王五', 28, '1996-06-17', '1E344479BDBAEE14083CB852E3624CBD241457E5826377ABEE556AC98D3DC540', '2019-08-10 22:52:58', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('4', '11669322045', '麻子', 18, '1996-06-17', 'EA6D9F6AF909CC713BCAC6833DA230918493F9BD271E7D47A436768869B75DE2', '2019-08-10 22:53:07', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('5', '11665204783', '测试', 18, '1996-06-17', '4F154003A435A0F17D1A7CD6C1EBF36D4861E7B92A0B8E22DE9433461D83E0B7', '2019-08-10 22:53:07', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('6', '11662521782', 'test', 40, '1996-06-17', 'EB93F8B0216EC9E8A56DE780BE8C11D3F3C86199147DAA8569B874D27555A859', '2019-08-10 22:53:26', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('7', '11661470787', '张三', 18, '1996-06-17', 'F35B155365580368A22A19166A86A7150FA89A99059FA0B5D76A17619D12592D', '2019-08-11 00:43:22', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('8', '11669310308', '李四', 18, '1996-06-17', 'A696217CB02C7D4E9F38416C0EB3A113A5C406218A7595961D2E60BB89E3C5E9', '2019-08-11 01:21:26', '2021-09-10 17:58:56', 0);
INSERT INTO `demo_user` (`id`, `username`, `realname`, `age`, `birthday`, `id_card`, `create_time`, `update_time`, `del_flag`) VALUES ('9', '11662129115', '王五', 30, '1996-06-17', '300E48E6695E3712D41E370061CA5B33B0DCF51A4DBF43B1443F3F6AC9A82210', '2019-10-17 21:50:40', '2021-09-10 17:58:56', 0);
COMMIT;

-- ----------------------------
-- Table structure for magic_api_backup
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_backup`;
CREATE TABLE `magic_api_backup` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原对象ID',
  `create_date` bigint(20) NOT NULL COMMENT '备份时间',
  `tag` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签',
  `type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原名称',
  `content` longtext COLLATE utf8mb4_unicode_ci COMMENT '备份内容',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`,`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of magic_api_backup
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for magic_api_file
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_file`;
CREATE TABLE `magic_api_file` (
  `file_path` varchar(512) NOT NULL,
  `file_content` mediumtext,
  PRIMARY KEY (`file_path`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of magic_api_file
-- ----------------------------
BEGIN;
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/group.json', '{\n  \"properties\" : { },\n  \"id\" : \"aa31fa7f644140b98e22a67365c8db6b\",\n  \"name\" : \"API\",\n  \"type\" : \"api\",\n  \"parentId\" : \"0\",\n  \"path\" : \"/dataapi\",\n  \"createTime\" : 1707208084235,\n  \"updateTime\" : 1686810286167,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : \"magic\",\n  \"paths\" : [ ],\n  \"options\" : [ ]\n}');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/1查询List-导出.ms', '{\n  \"properties\" : { },\n  \"id\" : \"copy1721651353695d92391\",\n  \"script\" : null,\n  \"groupId\" : \"2ee24b00f70246e7b785b95958407412\",\n  \"name\" : \"1查询List-导出\",\n  \"createTime\" : null,\n  \"updateTime\" : 1721654713392,\n  \"lock\" : null,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : \"magic\",\n  \"path\" : \"/list_export\",\n  \"method\" : \"POST\",\n  \"parameters\" : [ ],\n  \"options\" : [ ],\n  \"requestBody\" : \"\",\n  \"headers\" : [ ],\n  \"paths\" : [ ],\n  \"responseBody\" : \"{\\n    \\\"code\\\": 100200,\\n    \\\"msg\\\": \\\"success\\\",\\n    \\\"data\\\": [\\n        {\\n            \\\"realname\\\": \\\"张三\\\",\\n            \\\"username\\\": \\\"11669321995\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"麻子\\\",\\n            \\\"username\\\": \\\"16642131821\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"测试\\\",\\n            \\\"username\\\": \\\"16674548152\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"test\\\",\\n            \\\"username\\\": \\\"16642132230\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"张三\\\",\\n            \\\"username\\\": \\\"16641708355\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"王五\\\",\\n            \\\"username\\\": \\\"16623229479\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"张三\\\",\\n            \\\"username\\\": \\\"test\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"李啊\\\",\\n            \\\"username\\\": \\\"18612341233\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"李思\\\",\\n            \\\"username\\\": \\\"18612344444\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"测测\\\",\\n            \\\"username\\\": \\\"18688498342\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"李四\\\",\\n            \\\"username\\\": \\\"11662193208\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"测试\\\",\\n            \\\"username\\\": \\\"18612344321\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"测试\\\",\\n            \\\"username\\\": \\\"18612344321\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"王五\\\",\\n            \\\"username\\\": \\\"11666879288\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"麻子\\\",\\n            \\\"username\\\": \\\"11669322045\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"测试\\\",\\n            \\\"username\\\": \\\"11665204783\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"test\\\",\\n            \\\"username\\\": \\\"11662521782\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"张三\\\",\\n            \\\"username\\\": \\\"11661470787\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"李四\\\",\\n            \\\"username\\\": \\\"11669310308\\\"\\n        },\\n        {\\n            \\\"realname\\\": \\\"王五\\\",\\n            \\\"username\\\": \\\"11662129115\\\"\\n        }\\n    ]\\n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : null\n}\r\n================================\r\nimport response\nimport java.net.URLEncoder\nimport com.akm.springboot.web.sys.entity.SysUser\nimport com.alibaba.excel.EasyExcelFactory\nimport com.akm.springboot.web.demo.entity.DemoUser\nvar sql = \"\"\"\n    select realname, username from demo_user\n\"\"\"\nusers = db.select(sql);\n\n\nreturn users\n// fileName = \'test\'\n\n// // response.setHeader(\"content-Type\", \"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8\");\n// response.setHeader(\"content-Type\", \"application/vnd.ms-excel\");\n// response.setHeader(\"characterEncoding\", \"utf-8\");\n// response.setHeader(\"content-disposition\", \"attachment;filename=\" + URLEncoder.encode(fileName, \"utf-8\") + \".xlsx\");\n// EasyExcelFactory.write(response.getOutputStream(), DemoUser.class).sheet(\"Sheet1\").doWrite(users)\n\n// response.end()');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/1查询List.ms', '{\n  \"properties\" : { },\n  \"id\" : \"7760950099a949c582547bb81d28427f\",\n  \"script\" : null,\n  \"groupId\" : \"2ee24b00f70246e7b785b95958407412\",\n  \"name\" : \"1查询List\",\n  \"createTime\" : null,\n  \"updateTime\" : 1721651183411,\n  \"lock\" : null,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : \"magic\",\n  \"path\" : \"/list\",\n  \"method\" : \"POST\",\n  \"parameters\" : [ ],\n  \"options\" : [ ],\n  \"requestBody\" : \"\",\n  \"headers\" : [ ],\n  \"paths\" : [ ],\n  \"responseBody\" : \"{\\n    \\\"code\\\": 100200,\\n    \\\"msg\\\": \\\"success\\\",\\n    \\\"data\\\": [\\n        {\\n            \\\"count(1)\\\": 2\\n        }\\n    ]\\n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : {\n    \"name\" : \"\",\n    \"value\" : \"\",\n    \"description\" : \"\",\n    \"required\" : false,\n    \"dataType\" : \"Object\",\n    \"type\" : null,\n    \"defaultValue\" : null,\n    \"validateType\" : \"\",\n    \"error\" : \"\",\n    \"expression\" : \"\",\n    \"children\" : [ {\n      \"name\" : \"code\",\n      \"value\" : \"100401\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"msg\",\n      \"value\" : \"未提供token或token已过期\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"String\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"reqId\",\n      \"value\" : \"null\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Object\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"data\",\n      \"value\" : \"null\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Array\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    } ]\n  }\n}\r\n================================\r\n// 列表查询接口示例\nvar sql = \"\"\"\n    select * from sys_user\n\"\"\"\nreturn db.select(sql);');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/2分页查询.ms', '{\n  \"properties\" : { },\n  \"id\" : \"copy1661830578773d63310\",\n  \"script\" : null,\n  \"groupId\" : \"2ee24b00f70246e7b785b95958407412\",\n  \"name\" : \"2分页查询\",\n  \"createTime\" : 1707208084263,\n  \"updateTime\" : 1707202440107,\n  \"lock\" : null,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : \"magic\",\n  \"path\" : \"/page\",\n  \"method\" : \"POST\",\n  \"parameters\" : [ {\n    \"name\" : \"page\",\n    \"value\" : \"1\",\n    \"description\" : \"页码\",\n    \"required\" : true,\n    \"dataType\" : \"String\",\n    \"type\" : null,\n    \"defaultValue\" : \"1\",\n    \"validateType\" : null,\n    \"error\" : null,\n    \"expression\" : null,\n    \"children\" : null\n  }, {\n    \"name\" : \"size\",\n    \"value\" : \"5\",\n    \"description\" : \"页大小\",\n    \"required\" : true,\n    \"dataType\" : \"String\",\n    \"type\" : null,\n    \"defaultValue\" : \"10\",\n    \"validateType\" : null,\n    \"error\" : null,\n    \"expression\" : null,\n    \"children\" : null\n  } ],\n  \"options\" : [ ],\n  \"requestBody\" : \"\",\n  \"headers\" : [ ],\n  \"paths\" : [ ],\n  \"responseBody\" : \"{\\n    \\\"code\\\": 100200,\\n    \\\"msg\\\": \\\"success\\\",\\n    \\\"data\\\": {\\n        \\\"list\\\": [\\n            {\\n                \\\"id\\\": \\\"1698636320474443776\\\",\\n                \\\"requestId\\\": null,\\n                \\\"apiDescription\\\": \\\"获取系统配置\\\",\\n                \\\"uri\\\": \\\"/auth/open/config\\\",\\n                \\\"method\\\": \\\"POST\\\",\\n                \\\"parameter\\\": \\\"[]\\\",\\n                \\\"result\\\": \\\"{\\\\\\\"config\\\\\\\":\\\\\\\"6aIR3tgLTsS0J4Ti9hfiTcvfPC6dn2gcIaLiPmRTd0mYBZ3I5X+55tLHtil7WG/MtAuKwZAAOCcnYmL5QDaQizL4UWGipLFiJg1BFh6RDGPmg1V/W5a6KvZv81yNd4hgJeHzdytug9/QJxiLwnj3CxN0iWUab8O/3IjmHDg0ec3V/nb9vSDICAfgsv1cqMej4nxGHd5Y2jLkvjOftLTYErCc7n7eOc4u6GZ3BdkakstIAEDYnYEJrp4OcpRCTLhmdw72oTYQULAzA23Dtff/uxDr/UQXAj3cQYuD5Vjf/9DIJTS4J5ELWlCGgEeDaElXPs5LysCNJx0Nb39DF5ZB//prIjfkOahgRTjFg7trbYouOXo1c3MAsxuGIdUNTN8f1G0rwvj6iPYNJOHh+yPTxEcuzdoHUon9/BtGYHHun0cMFMbrcn9JNkGtB9gHlWJ/UEYAt4dV3nfTQ9Xi5gFhnTtDEH64ZgxWTN4H2Uc3KZWqzNyehVOOLAPiQPfBXxgAIjDdHHtal8y7U7L6rZy6JP41dekuHCbPZaABG/EtF2WKO4wJLfgc76SHJDLOH2xokaSOjmFDEBuKgwGVtYPtxr6ebVOmH/SbAus+LXAiCH6nH3dKwBR/tqp1yBJ+LPP5b6fVKXWDvk+DbyuHC1197VfxxEOxkXysPqe0UqQE800gTw1rwMDdUeu2qP0qlY5L1jsZvDRH5o8jiRiYzBvGyik+2j+O+EFHXeo5534g/g/VoII5Xjja2H7aZDMtWCNUHKWiD8cnYUAwl5d5s6NfYc4JwbQpe2fRp+XmcIHpPlMYwqyNB3vot7dmA32MoB+8sFNlJJaja4zedITQTysymw0FszBB/i5eOwrp+8eCIL7G7IpWtRaL/SpDZ9pQE+2641Uw0uMQT4sdl7cJn+8LLxnI6ywwSs97Y9wXS+nQmezXC/0Mw5ix02MGEcDn3FZklLugrK76txsqCWI1qmSDQQwFsIyvNMq94tK3SkuQAwTSBy49SMcvXtTyTJoAhZ/FDENdCf4DyNb6/EUyVa7SGA5OTnjTVnurGnSONVTHUoX8yArwuLJuZi6jV164ZPGftbAagEgimbD0p6TPAn3qCH+S33Po1km1QFaU6ZMb3C5gvwmZR3h5IGt/NEQjalP866StGZFgcAcspws2tN4WRoxRX5R0RmH80Ye5GAZ7FGdMqCXQudwg0/mMaFr/p6/wG7j4rYl2ebx1Fq4KrbqppzBqFQcuylLHFlRlaXi8kTfMOEt+tkLSM3W8Wg1VfN0jik/Dw1v/V2gZqGGgrIwbL3OiCGmu4qMfvjxn/Lm3oKPZpgg00nEK6MQQyMFCaFT5O9dq4DI5owdwetXvGJ82mLavguB2kRVxtKznyDZaUpbMF9xNhsYJ4If3czWzpr1yCcTReG4B9Y2FeBl/CKB7n/tnwIXZDV6Kn4O1vcALzDQvSHHu2UfNgLlcMeEjxI/a+rV1dlInOTICKlv4yDEPX1c/aMXJe/P9IXdIddHwmqkvov0q2Y4YnY4gZbVy0yFX0uWri4WzcRoPy2zxHcG/VVD36sl2UlZSzjIzC9owmtWMkNv786gaSaeb8qA3HYGjgN6O4pkapc/myQFBVVnNnY4V962VXOVp0LYWzF6a87rxoXENbS9wZrQJ/Bt1Cc6Sow2cZPTK1xUJovPloHaQoYksESuplAPJGnrdkwuXQq2O5gBZucQdlK1L3oGeGFDf1F+G165m7Orb1BnpilIHW1kzrVNb1hBF5CFnmZO8Hc6zPgu9MN5frpePu5SkgGQfVHWNFkwz38YIQBKxYVT1iBXDXlfc+mJPDr6SWX8K2W8ryEyHtXKH0u6Yr5BstfEzvZRzuO1cGDX+g7sqwMGDc9D767y0ib8jAtX5do/vDah++Uqb4Qv0h3nT/ip07Omn3NkZEw3RIiy3yf3PmcwUIzzcDL5ZbnK7DmBCQiLoTsM8fL6zDppyKg67fGD7bybZoA6A6my/e5/ftm2WV2oOxDanFa8fId6JScXdlPOHas33NU0P/tqymOl+1BhPZgN/HxysEMh8Wom89GPkmMHXax93QVnaJSzNQ8GQFDZnMrkjcjZ2JvquE4lOo7sNy7EZLGrAGJxPixwtX3sB43waMeY+ce1YLI6emPE+2DA32I8vsVqs8lA4msZ+v+nFHeER\\\\\\\",\\\\\\\"random\\\\\\\":\\\\\\\"e63362b975c942fe9e3e0c0bbe8f5561\\\\\\\",\\\\\\\"timestamp\\\\\\\":\\\\\\\"1693821432571\\\\\\\"}\\\",\\n                \\\"success\\\": 1,\\n                \\\"startTime\\\": 1693821432562,\\n                \\\"spendTime\\\": 31,\\n                \\\"ip\\\": \\\"127.0.0.1\\\",\\n                \\\"url\\\": \\\"http://localhost:8980/auth/open/config\\\",\\n                \\\"userAgent\\\": \\\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\\\",\\n                \\\"userId\\\": null,\\n                \\\"username\\\": null,\\n                \\\"name\\\": null,\\n                \\\"clientType\\\": null,\\n                \\\"createTime\\\": \\\"2023-09-04 17:57:12\\\",\\n                \\\"updateTime\\\": null\\n            },\\n            {\\n                \\\"id\\\": \\\"1698636320473919488\\\",\\n                \\\"requestId\\\": null,\\n                \\\"apiDescription\\\": \\\"获取系统配置\\\",\\n                \\\"uri\\\": \\\"/auth/open/config\\\",\\n                \\\"method\\\": \\\"POST\\\",\\n                \\\"parameter\\\": \\\"[]\\\",\\n                \\\"result\\\": \\\"{\\\\\\\"config\\\\\\\":\\\\\\\"vR0oZSxqHz+VUacRBdppdM92k917G9LzTdPQHnSqL9IOk8xRtZVhav1/V3YW0igbIX9HX6SJbOgIcUqxOgSOEgsL1WBXFmNejD3KlAfoK5AX41OBM7TJpFTPy4sgpXbF0be5rk02HKgZ2yNwD6q7kpxfZ5Un7wq/RmVRNprOJJXboH5p2fgBKzJYeEcIORRlqiv32pSkE5JsiTD6TFzhhCyL2gMCHcZpymsG7RndSEglhoz8CaGu1kchy9LNBQhVLtjzE7DYr4VjyoGWslCyohFjSIjkLInXGEbeFanYYBiMwj5emaBSojoywTo1TB9/F519gQW4D6MBqSEdw398IsVvrk4Q5F1Y83uRpg1UhjCefG2cDgswhkKxOI8p16LPP2tz0EY1Dsk5bYpn+YxioOV28ksD+pVTh3raZeeYhOWb66wO8K1oLkkFEsFi6Qzg2Y9JDNaWsWzcEtN4DNCtKUOZwb40W2k/f1GQC0PCDkYx+0bWaSjOIbao4ns2S/UcWa8GixL3o/JS+wHUO5p5DYBlNQyGfwXtP2GyjpNP+/qhiwcK2rDzYavytUh8C7GjbQNLqGem+ZGLI2IQmrKFS9mlXu6FHLkBqBFoDnGj8us1POK/IqRsHV/pQKbXMvxJ5TGr5odSBRcHXCyazZvCx4KBYaZ5yyL4wJo7migf6oTZvSTLkNTBnAo9s1PuLl8fuNz4rr0UkAOfvt+cdFGWVSot0TgTIUfJEfgfQNCyPDRiRIvRbqolAMW2bBcEFUzMqGBgdk8rTtz3nMbOzKnR4LCwcPABg4/q5S7JcZfzOdb5hOk72FVg22fdgveluKC7BkV4Q9rywucH49XyDdjHRxlFjT9oPrO8XDbKmcIiUREV45wcXiuLdKrfncXExgH5ju0fxOrPFdKGmiNAl6pvRRjAGQo/hPDlPFVst30x/6NUh4X4hIQRNPHHIEjvuW1QA1IQZ3brHZQAmZ1M1HCY1955LE6V6vL4V5IaYWKRVASmisLkBF9nSVee6Oc8B4D/XqQMvH4ZshmLVkjJmXMhsDFVql3gKZYiWxuhqtMzLgoVAzBMusqo0jXeJwWcrsmpN4Dykr8G8wkIgbqmKUZogr7f9Ud2ENeDNWXk6sB5O1DkdpGKuMDzTz1Vhsa03jwLMEDuNP3nIA+rIX5lWXZ/i7ggzcamrBT+PkQL7427PH4EZ3h7BB6xfolFe3HpUaSzzVYa7moEMzE4Tn6C5xHTSspf1vbFQ/0IGFAHqRKM5HnqMmNQ7B7acnY3Z3qykkonjDSZA4+2RR+R2+A9JJLPIi98lSqgIlAqe9BZDdXoz4aL+r5LIz/RwRXg8LY74/bTruB+p6yzS0A2MqtzIEoaPQzjxQhCx1nkglPGeCNDiN5sZPAfjwlY3pC54eHsKIVlXYD5f5NUgXmpERL7p6DPfBPXoblH4UuoXYm6MXQn6YPRnhX9BoaGuZW2q398nmLItNPkS24vQjyHPFWV4nVfSE70q/jTAeJJJqKs3/LmzOz3RaNlxwhsqRq8sP1+BdeAMJvRVQnI4UG8jsSTgx1w0a1tK07aJdWJm/vLwt6ondzAkVCkOrMi1TX6DFbVykBZLR/1uzJ7K6l7X81k8NAxEPYAst6/B/6sa9CYJ/6zPAMyCtHZE1LYgsyihFgRA6hYCSb2TYigzcEwMG670EViAojGi5WHO1oouhbTy/+XFRoRf9R9hv5wYqnyKcwHbquRQOpTelFwHG/vGhTylG+M8dRHUP2Mii4GL/BNqyNofjdMzWQ3pU7eq8cPWiEFNFVlvZjRwQal9QY9XTAssYy/nivUsuCqj0M5f3Ssdi5iZJwZ0LDO9JnKLHpaDjdBjp0cU1QuW7qLVKOMP9S4ast3B449Xe+1mgDnqcTSOIxUUwsFE8pmPwI1Dc6J8GNR6BEzITuDDWD0s9Lz3k3wDwha+cdxE44QgPm/DlkTY1gSWUvEQ3MlRNz4X+hwZcDXlzIYmIC5BLdwSK02HLy+t7LPLtumCFkPVos9B6ju2StANK5u5eSh1LcOHzo/Br4I4lAgPiE6G237q/hA2OjA31Ai84pvTx2CCtATMP73YOZUaSc8hmKzG8D745RZ3EecuJoczr6QUfXUK7wlWfYA5VawCO9X/ET6wqnObnWaxHfRsnDQUWr5rO/n09mcPl+8T2YG\\\\\\\",\\\\\\\"random\\\\\\\":\\\\\\\"ed983160d0784c4888df998dacf6d66c\\\\\\\",\\\\\\\"timestamp\\\\\\\":\\\\\\\"1693821432572\\\\\\\"}\\\",\\n                \\\"success\\\": 1,\\n                \\\"startTime\\\": 1693821432562,\\n                \\\"spendTime\\\": 31,\\n                \\\"ip\\\": \\\"127.0.0.1\\\",\\n                \\\"url\\\": \\\"http://localhost:8980/auth/open/config\\\",\\n                \\\"userAgent\\\": \\\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\\\",\\n                \\\"userId\\\": null,\\n                \\\"username\\\": null,\\n                \\\"name\\\": null,\\n                \\\"clientType\\\": null,\\n                \\\"createTime\\\": \\\"2023-09-04 17:57:12\\\",\\n                \\\"updateTime\\\": null\\n            },\\n            {\\n                \\\"id\\\": \\\"1698636322626121728\\\",\\n                \\\"requestId\\\": \\\"e369e92f4bb3b94a7c883dd750b321cf\\\",\\n                \\\"apiDescription\\\": \\\"获取图形验证码\\\",\\n                \\\"uri\\\": \\\"/auth/open/captcha\\\",\\n                \\\"method\\\": \\\"POST\\\",\\n                \\\"parameter\\\": \\\"[]\\\",\\n                \\\"result\\\": \\\"{\\\\\\\"imgBase64\\\\\\\":\\\\\\\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEgAAAAkCAYAAAAq23xmAAAEyklEQVR42u3YfUxTVxQAcP5ZlmxTE2JI2B/GaEjYgLiYLDqXIW4r6raYuWaQVLKZxWVmQ5xDYQubmVmwNQIqgyIWpgJBFIR0+AFSYAgis4ogkOJooF8UCkWg399nvY/1llpb2kJfWXg3OQnte320v5x77rk3DKjhdYRRBBQQBUQBUUAUEAVEAfk4ppUqyD7EIaL8bH3If6BQ2A+MfVv8itI/WMEDUoxOQeqek0TkZpSFHOjJky7Y8s4qvyKbmbpygASCx0DbuW7BIA0IDfWMlgi91vC/qTWpaXsInK3bVkP34w6qSM8fDQ1XcfYwWWmuRfpZ1mmYPJBJhHV61uuDnmWcJO6bOprtNXvKztQT0VjdGfCXttnjgXwcsv66B4lXauHNC2UQcbYYYjnl8GlNPTSLJEuCo1JNw66PNhA4H38SBWq1q0GY8mAWiF6JJkJVXOnxQQZ+L75vcn960GvQT60d8BIr32sUPOxZNBDKGEf2tLZy3Zd5fTsf//Cx+CSPD1J++zO+T9/WFXSgdN5dAiG6+DIcaWqDwke98EtbJ8RcKMdA4XnnQWU0LmqFQzUH4WT+yPDQB9lsIIuh4R9vGhS6p7taC+KIzcR1WVwioM8EG+iWcATuDIvh+f+kMZlgTS4bI/Ht0zCQYbGYgZGylcCJT4gAhULmuVGcyS7AQKgmudWVi9X4+mwuJ+TLfHLdLQxUIxgK6Bll5Wfw1GIX/eq9kzaPSEH06hsEgHRjPOJ1uQlNPXRNvCYWLAplyIEc0w9F1cBTvz8vl4th+44IAmfn7vWg0agW3mqMJ6bgLNHduYtvMPYN4vcnGGmkN4pTej0Ud/fBseZ2SPmzAWiVtfB6PmdRQOnHknD2XLt23re9mPpyjXOV+uKI8wv+8JsTrqmdNKBZgxG+rG+E104Xel3JrvgJdP9+E8b5jB4HZrPJNyCrWgPitW/NTaXwTWCdUYFNpwdJ5NtzxTn6fftNVlKAUCHeXlGNEeI4FcQq1quYhAmtDg41tgYEhDCSkjf7nD1unfTkVxnOnqikCjSVXPx6hskmbS+GMBwAu6vqQG+2LEkN4nIvYZwdH0SCVqv2D0jf0unsiRKSYYy2b+71qhiwjI6TBsTg3sYAvBGJ1yLtawZZ7dm/lx6LgXLyjgZwHmR/iDQqASM5QkE/SOpuPqGiBgPkdD1yq00fVl73eB2NoswCt/f6+/kuO3aR6GkAQOjA63ieG5DuRjOpQN81tmCAyHMcAqFTJod8fg+sKyh1KdK77FPQMK8tceA8j1RSysQ49M83BX6iaPpn2AXnRX1RsIEEyilYncP2uHIl1d106aYR4j2p3A1l/uuvv6F53LH7feSK6o8DaPrEOf8OzGTKJVnm0TZjA/uiC0xU0SXcOaOsevnU7/haRb/AI5DJZIR33wvHQDze9dCdSUuEYxiIfeLqop5ltGduj2ICbgwNg2TWvdsdVanhpn3PJre3KC+qO57qUUgP7fseDGGgKvZt0g69liWQVq2Dv1v6wKAz/rcI2qCEVYuBOhq6VzaQbFhBQHxPPwXMtBLI2p+PcdDfJqN5ZQOhgnx4LwujOOL4gUIYGZSReq68bGsQyhLhgBR4tV1EDDwU4ilHAS2jQQrQ2u7xBWOlAf0LJjFzAJumkCUAAAAASUVORK5CYII=\\\\\\\",\\\\\\\"codeKey\\\\\\\":\\\\\\\"bea11373e41d4ade96eaed9c2e4f587c\\\\\\\"}\\\",\\n                \\\"success\\\": 1,\\n                \\\"startTime\\\": 1693821432642,\\n                \\\"spendTime\\\": 466,\\n                \\\"ip\\\": \\\"127.0.0.1\\\",\\n                \\\"url\\\": \\\"http://localhost:8980/auth/open/captcha\\\",\\n                \\\"userAgent\\\": \\\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\\\",\\n                \\\"userId\\\": null,\\n                \\\"username\\\": null,\\n                \\\"name\\\": null,\\n                \\\"clientType\\\": null,\\n                \\\"createTime\\\": \\\"2023-09-04 17:57:13\\\",\\n                \\\"updateTime\\\": null\\n            },\\n            {\\n                \\\"id\\\": \\\"1698636343949963264\\\",\\n                \\\"requestId\\\": \\\"732a12195207e3492758c59c6bcdc070\\\",\\n                \\\"apiDescription\\\": \\\"登陆\\\",\\n                \\\"uri\\\": \\\"/auth/open/login\\\",\\n                \\\"method\\\": \\\"POST\\\",\\n                \\\"parameter\\\": \\\"{\\\\\\\"clientType\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"username\\\\\\\":\\\\\\\"18612341234\\\\\\\",\\\\\\\"password\\\\\\\":\\\\\\\"c24a0923685831ba4b2a357720b4c6fef38022b4d52c006e7ac59be58afcd350\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"codeKey\\\\\\\":\\\\\\\"bea11373e41d4ade96eaed9c2e4f587c\\\\\\\"}\\\",\\n                \\\"result\\\": \\\"\\\\\\\"dbc4bdfcaa5748038d0d01a1821a585a\\\\\\\"\\\",\\n                \\\"success\\\": 1,\\n                \\\"startTime\\\": 1693821438130,\\n                \\\"spendTime\\\": 61,\\n                \\\"ip\\\": \\\"127.0.0.1\\\",\\n                \\\"url\\\": \\\"http://localhost:8980/auth/open/login\\\",\\n                \\\"userAgent\\\": \\\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\\\",\\n                \\\"userId\\\": \\\"1183307967865425920\\\",\\n                \\\"username\\\": \\\"18612341234\\\",\\n                \\\"name\\\": \\\"张无忌\\\",\\n                \\\"clientType\\\": \\\"1\\\",\\n                \\\"createTime\\\": \\\"2023-09-04 17:57:18\\\",\\n                \\\"updateTime\\\": null\\n            },\\n            {\\n                \\\"id\\\": \\\"1698636344126124032\\\",\\n                \\\"requestId\\\": \\\"fbfbd3cece59114052e839e4c52e3645\\\",\\n                \\\"apiDescription\\\": \\\"获取图形验证码\\\",\\n                \\\"uri\\\": \\\"/auth/open/captcha\\\",\\n                \\\"method\\\": \\\"POST\\\",\\n                \\\"parameter\\\": \\\"[]\\\",\\n                \\\"result\\\": \\\"{\\\\\\\"imgBase64\\\\\\\":\\\\\\\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEgAAAAkCAYAAAAq23xmAAAGOklEQVR42u2Z2W9UVRzH/Q98MEGML76oLxqffJEIxiWaaATFLQZkiUSDyKIMqEilgIAIUihCK7I0WMB0g1LaobVDC6XrtDMt3ek2HbrPdPa73/vznDvcM9u9d4YyhJTOL/mld+b+OnfOJ9/zW848BinTtcdSCFKAUoBSgFKAUoAeMUC8CEL3OHDlncCbh0AccQMI4oweIIIENskPdcIUjEv07AUkOfwQ+DoXvIsOguvpreCatznCPS/uBPqwCSSKS+iDaRBgH9cOb1AV8AplJL6YroKDXCdwIM4uQGLfZAwUNfevzon7oUNIMZ/SNyLARPs6phF8Ej+LAA06wPNCOlDbi4FvGATJ6Ze3lWhzyu+FQ+IqunS31JdMHQGxn+uAUYmSFdUsOuGzMHAY0uzKQZKkGRj4KpcAon8v14wr4ocJgD3srZj7HomDT+jrJKZDdD8aVYzNbSSAApvyNOO+QapQFo+Vg22r4fEIX3luMYl535wec1/xh2H9S5ZGeMKAcIImCsqo1Ix7lzbJC3+bDsVEL9zwwxOwwH9FjlsQKAPDtnkxMZvXLYRNa9bA2axKsPVPBHNb3wSU5DXAgbQC2Lb2DBzZfQnMtbcT+v5eNws3ym2Qf7oDsn4zQ25WG5hKBsA5RcGo3QfGwj7I+/Yk9NxyzEBBLA/eNw8FAT21RW4BVL8E2j5EGfQ13Y9cSleT2EGU1KOt8J+bsH7ZcdkxhLqqLtiwPIu8F+7GIrPus4b73ZC1rwkOpdXF+NHdjXDxbBd5XW0cujdA4u1J8C87FVLPPqNmLI8S9ELqqrzoRVS5nLC17HO6hgBqEB26gLBSNiwPXm9ckQ1/7CiEzatPkPvfrfwLXE6/6nMmx/yQsaOeAMDKaai+A+aaESg+1w0Zv0QCiwuIu9QK1NZC8K/KAe9bGeB60iCDcT+XBsyZ2rhiW87cJAu3iNOaSnv1LkjsxbxdF5DipQVN4PcGm82Aj4Fft5wn96xNA6rPKsjpJIu/fL4HBF6MUVf2fnPigHACVut/At/ng9AxGhfQUa6bLBwnbCFKRfjVz6w1oifK5QfiAiorjN1GF8/Vkvum0taY+zi3KAs/kl4Pfp96k9vRMnkPCjK2A7WzBALrzoPvo2xwP/NTCNR8A9AHUIkXtbcOhZAsQd1yeK9jRUpySAy0oD4ovMopflUY1QV04WS16rP+K7GQGHwdba2N4yH1XOjR/M6dlsmZ5yDJTQUr2HwDAcX8XaP7P62iC967W83U/EfWAh+H9UI4Pl6SVjPTFasuoOqyIbLwmorhhAAVr828/zLvfna7PNDqmUtiYQfbSsr+AuRfoPyUw/fLyfsd1AYogCZVhthkAMKqURbeUjf2YBRElISSopKwsePZLVHDANwSF7ENFTha1S4ZgCovD5CF15nsDxaQPCagiV4BxNf0zbhbvSzYCSAD26wakwxAuJQrCy/N024orfVjSVCQn4nIQ+LozGeoVUxt3FksGYAGeqbJwjN3NgAViD096O+ehsxdDfcPiEovCeWg59NmDOcCP0TgrGeaNOOSAQjP3jmZ1lCTeLwNHBMBEFEVHrvjk0eP6Ebx+lWbOiDuohX8K84AW9ACkjeUNPE1lVYcoR651OsclP3J9UC9MAVs2KHYFCrz2VwvgfM6VQHtKtUrmYCw3e50xo4YYVAOoy4bz2HK69rKYXVAzKmboX4HJWPPS7vA8/LeCDDYfUuO6/ZBuHopEHC3/AHqiT4Mm7uUU8Vu0aOrtGQBwtbb7pBnrmhQWD23zBPQHtYo4jFEFRCb3ywPoloniXhbMdnXkdQE3YXhU0JcmdT6n9fQ+xvRtsJqimdFYV1yS716Qagytul20hEFxsVAe/OEXNmw93Y4gaaCOam+6g4B1NY0rp2D8Hmz0GyTYWEYzBETsEUWECx2kAJswnkGnzd3ouRbJoygMWIQ8nkbdCHF8DrD68O00rxeAmioz5362Sfcph00HE4PTvvH9jQBywhzC9A4qlYl//ZCX9c0CEKkgocHPHA6wxJqJq/ZZ37kOlstPAFjpZw81CKX+2N7Iw/QThxAaYUW5h4gPEZonSYqlQwnbTU4cyoH4S56xOaVy3otmsvwX/ugR/OMKJWkk/2zz1y1/wGeGa0f5EQirAAAAABJRU5ErkJggg==\\\\\\\",\\\\\\\"codeKey\\\\\\\":\\\\\\\"fd813292ce8f4685a547e075ee6932ae\\\\\\\"}\\\",\\n                \\\"success\\\": 1,\\n                \\\"startTime\\\": 1693821438227,\\n                \\\"spendTime\\\": 6,\\n                \\\"ip\\\": \\\"127.0.0.1\\\",\\n                \\\"url\\\": \\\"http://localhost:8980/auth/open/captcha\\\",\\n                \\\"userAgent\\\": \\\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\\\",\\n                \\\"userId\\\": \\\"1183307967865425920\\\",\\n                \\\"username\\\": \\\"18612341234\\\",\\n                \\\"name\\\": \\\"张无忌\\\",\\n                \\\"clientType\\\": \\\"1\\\",\\n                \\\"createTime\\\": \\\"2023-09-04 17:57:18\\\",\\n                \\\"updateTime\\\": null\\n            }\\n        ],\\n        \\\"total\\\": 1367\\n    }\\n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : {\n    \"name\" : \"\",\n    \"value\" : \"\",\n    \"description\" : \"\",\n    \"required\" : false,\n    \"dataType\" : \"Object\",\n    \"type\" : null,\n    \"defaultValue\" : null,\n    \"validateType\" : \"\",\n    \"error\" : \"\",\n    \"expression\" : \"\",\n    \"children\" : [ {\n      \"name\" : \"code\",\n      \"value\" : \"100200\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"msg\",\n      \"value\" : \"success\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"String\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"data\",\n      \"value\" : \"\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Object\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ {\n        \"name\" : \"total\",\n        \"value\" : \"309\",\n        \"description\" : \"\",\n        \"required\" : false,\n        \"dataType\" : \"Integer\",\n        \"type\" : null,\n        \"defaultValue\" : null,\n        \"validateType\" : \"\",\n        \"error\" : \"\",\n        \"expression\" : \"\",\n        \"children\" : [ ]\n      }, {\n        \"name\" : \"list\",\n        \"value\" : \"\",\n        \"description\" : \"\",\n        \"required\" : false,\n        \"dataType\" : \"Array\",\n        \"type\" : null,\n        \"defaultValue\" : null,\n        \"validateType\" : \"\",\n        \"error\" : \"\",\n        \"expression\" : \"\",\n        \"children\" : [ {\n          \"name\" : \"\",\n          \"value\" : \"\",\n          \"description\" : \"\",\n          \"required\" : false,\n          \"dataType\" : \"Object\",\n          \"type\" : null,\n          \"defaultValue\" : null,\n          \"validateType\" : \"\",\n          \"error\" : \"\",\n          \"expression\" : \"\",\n          \"children\" : [ {\n            \"name\" : \"id\",\n            \"value\" : \"1667053344023048192\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"request_id\",\n            \"value\" : \"2d60694de8a24f1e8d32f818f58ade1a\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"uri\",\n            \"value\" : \"/auth/sys/user/public/getLoginUserInfo\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"method\",\n            \"value\" : \"POST\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"parameter\",\n            \"value\" : \"{}\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"result\",\n            \"value\" : \"{\\\\\\\"openid\\\\\\\":null,\\\\\\\"avatar\\\\\\\":null,\\\\\\\"roleList\\\\\\\":[{\\\\\\\"code\\\\\\\":\\\\\\\"platform_admin\\\\\\\",\\\\\\\"remark\\\\\\\":\\\\\\\"拥有后台管理系统所有权限\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"平台管理员\\\\\\\",\\\\\\\"id\\\\\\\":\\\\\\\"1164384808021921792\\\\\\\"}],\\\\\\\"orgId\\\\\\\":null,\\\\\\\"realname\\\\\\\":\\\\\\\"张无忌\\\\\\\",\\\\\\\"username\\\\\\\":\\\\\\\"18612341234\\\\\\\"}\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"message\",\n            \"value\" : \"\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"code\",\n            \"value\" : \"100200\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"Integer\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"success\",\n            \"value\" : \"true\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"Boolean\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"start_time\",\n            \"value\" : \"1686291464226\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"Long\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"spend_time\",\n            \"value\" : \"26\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"Integer\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"ip\",\n            \"value\" : \"127.0.0.1\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"user_agent\",\n            \"value\" : \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"user_id\",\n            \"value\" : \"1183307967865425920\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"username\",\n            \"value\" : \"18612341234\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"name\",\n            \"value\" : \"张无忌\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"client_type\",\n            \"value\" : \"1\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"Integer\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"record_type\",\n            \"value\" : \"0\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"Integer\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"create_time\",\n            \"value\" : \"2023-06-09 14:17:44\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          }, {\n            \"name\" : \"update_time\",\n            \"value\" : \"null\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"Object\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          } ]\n        } ]\n      } ]\n    } ]\n  }\n}\r\n================================\r\n// 分页查询接口示例\nimport log;\n\nvar sql = \"\"\"\n    select * from sys_log\n\"\"\"\n\nlog.info(\"--------打印参数 page: \"+ page)\nlog.info(\"--------打印参数 size: \"+ size)\n\nreturn db.page(sql);');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/3常用示例.ms', '{\n  \"properties\" : { },\n  \"id\" : \"copy1661874770336d87746\",\n  \"script\" : null,\n  \"groupId\" : \"2ee24b00f70246e7b785b95958407412\",\n  \"name\" : \"3常用示例\",\n  \"createTime\" : 1707208084272,\n  \"updateTime\" : 1707202459183,\n  \"lock\" : null,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : \"magic\",\n  \"path\" : \"/example\",\n  \"method\" : \"POST\",\n  \"parameters\" : [ ],\n  \"options\" : [ ],\n  \"requestBody\" : \"\",\n  \"headers\" : [ ],\n  \"paths\" : [ ],\n  \"responseBody\" : \"{\\n    \\\"code\\\": 100200,\\n    \\\"msg\\\": \\\"success\\\",\\n    \\\"data\\\": {\\n        \\\"token\\\": \\\"ca60f534ef424f35a228ef4d2e8e0017\\\",\\n        \\\"userId\\\": \\\"1183307967865425920\\\",\\n        \\\"result\\\": [\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/API/\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/API/group.json\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/API/测试/\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/API/测试/1查询List.ms\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/API/测试/2分页查询.ms\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/API/测试/3常用示例.ms\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/API/测试/4数据权限.ms\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/api/API/测试/group.json\\\"\\n            },\\n            {\\n                \\\"filePath\\\": \\\"magic-api/datasource/\\\"\\n            }\\n        ]\\n    }\\n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : {\n    \"name\" : \"\",\n    \"value\" : \"\",\n    \"description\" : \"\",\n    \"required\" : false,\n    \"dataType\" : \"Object\",\n    \"type\" : null,\n    \"defaultValue\" : null,\n    \"validateType\" : \"\",\n    \"error\" : \"\",\n    \"expression\" : \"\",\n    \"children\" : [ {\n      \"name\" : \"code\",\n      \"value\" : \"100200\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"msg\",\n      \"value\" : \"success\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"String\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"data\",\n      \"value\" : \"\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Object\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ {\n        \"name\" : \"token\",\n        \"value\" : \"null\",\n        \"description\" : \"\",\n        \"required\" : false,\n        \"dataType\" : \"Object\",\n        \"type\" : null,\n        \"defaultValue\" : null,\n        \"validateType\" : \"\",\n        \"error\" : \"\",\n        \"expression\" : \"\",\n        \"children\" : [ ]\n      }, {\n        \"name\" : \"userId\",\n        \"value\" : \"null\",\n        \"description\" : \"\",\n        \"required\" : false,\n        \"dataType\" : \"Object\",\n        \"type\" : null,\n        \"defaultValue\" : null,\n        \"validateType\" : \"\",\n        \"error\" : \"\",\n        \"expression\" : \"\",\n        \"children\" : [ ]\n      }, {\n        \"name\" : \"result\",\n        \"value\" : \"\",\n        \"description\" : \"\",\n        \"required\" : false,\n        \"dataType\" : \"Array\",\n        \"type\" : null,\n        \"defaultValue\" : null,\n        \"validateType\" : \"\",\n        \"error\" : \"\",\n        \"expression\" : \"\",\n        \"children\" : [ {\n          \"name\" : \"\",\n          \"value\" : \"\",\n          \"description\" : \"\",\n          \"required\" : false,\n          \"dataType\" : \"Object\",\n          \"type\" : null,\n          \"defaultValue\" : null,\n          \"validateType\" : \"\",\n          \"error\" : \"\",\n          \"expression\" : \"\",\n          \"children\" : [ {\n            \"name\" : \"file_path\",\n            \"value\" : \"magic-api/api/\",\n            \"description\" : \"\",\n            \"required\" : false,\n            \"dataType\" : \"String\",\n            \"type\" : null,\n            \"defaultValue\" : null,\n            \"validateType\" : \"\",\n            \"error\" : \"\",\n            \"expression\" : \"\",\n            \"children\" : [ ]\n          } ]\n        } ]\n      } ]\n    } ]\n  }\n}\r\n================================\r\n// 查询接口示例，调用java工具方法\nimport log;\nimport com.akm.springboot.core.utils.UserThreadUtils;\n\n\nvar sql = \"\"\"\n    select file_path from magic_api_file \n    limit 10\n\"\"\"\nlet list =  db.select(sql);\n\nlog.info(\"-------- token: \"+UserThreadUtils.getToken());\nlog.info(\"-------- userId: \"+UserThreadUtils.getUserId());\n\nreturn {\n    token:UserThreadUtils.getToken(),\n    userId:UserThreadUtils.getUserId(),\n    result:list\n};');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/4数据权限.ms', '{\n  \"properties\" : { },\n  \"id\" : \"copy1661874748795d36592\",\n  \"script\" : null,\n  \"groupId\" : \"2ee24b00f70246e7b785b95958407412\",\n  \"name\" : \"4数据权限\",\n  \"createTime\" : 1707208084276,\n  \"updateTime\" : 1707202542825,\n  \"lock\" : null,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : \"magic\",\n  \"path\" : \"/data_auth\",\n  \"method\" : \"POST\",\n  \"parameters\" : [ ],\n  \"options\" : [ ],\n  \"requestBody\" : \"\",\n  \"headers\" : [ ],\n  \"paths\" : [ ],\n  \"responseBody\" : \"{\\n    \\\"code\\\": 100200,\\n    \\\"msg\\\": \\\"success\\\",\\n    \\\"data\\\": [\\n        {\\n            \\\"id\\\": \\\"7760950099a949c582547bb81d28427f\\\",\\n            \\\"name\\\": \\\"1查询List\\\"\\n        },\\n        {\\n            \\\"id\\\": \\\"7760950099a949c582547bb81d28427f\\\",\\n            \\\"name\\\": \\\"1查询List\\\"\\n        },\\n        {\\n            \\\"id\\\": \\\"copy1661830578773d63310\\\",\\n            \\\"name\\\": \\\"2分页查询\\\"\\n        },\\n        {\\n            \\\"id\\\": \\\"copy1661830578773d63310\\\",\\n            \\\"name\\\": \\\"2分页查询\\\"\\n        },\\n        {\\n            \\\"id\\\": \\\"7760950099a949c582547bb81d28427f\\\",\\n            \\\"name\\\": \\\"1查询List\\\"\\n        },\\n        {\\n            \\\"id\\\": \\\"copy1661830578773d63310\\\",\\n            \\\"name\\\": \\\"2分页查询\\\"\\n        },\\n        {\\n            \\\"id\\\": \\\"7760950099a949c582547bb81d28427f\\\",\\n            \\\"name\\\": \\\"1查询List\\\"\\n        },\\n        {\\n            \\\"id\\\": \\\"copy1661874770336d87746\\\",\\n            \\\"name\\\": \\\"3常用示例\\\"\\n        },\\n        {\\n            \\\"id\\\": \\\"copy1661874748795d36592\\\",\\n            \\\"name\\\": \\\"4数据权限\\\"\\n        }\\n    ]\\n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : {\n    \"name\" : \"\",\n    \"value\" : \"\",\n    \"description\" : \"\",\n    \"required\" : false,\n    \"dataType\" : \"Object\",\n    \"type\" : null,\n    \"defaultValue\" : null,\n    \"validateType\" : \"\",\n    \"error\" : \"\",\n    \"expression\" : \"\",\n    \"children\" : [ {\n      \"name\" : \"code\",\n      \"value\" : \"100200\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"msg\",\n      \"value\" : \"success\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"String\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"data\",\n      \"value\" : \"\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Array\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ {\n        \"name\" : \"\",\n        \"value\" : \"\",\n        \"description\" : \"\",\n        \"required\" : false,\n        \"dataType\" : \"Object\",\n        \"type\" : null,\n        \"defaultValue\" : null,\n        \"validateType\" : \"\",\n        \"error\" : \"\",\n        \"expression\" : \"\",\n        \"children\" : [ {\n          \"name\" : \"id\",\n          \"value\" : \"7760950099a949c582547bb81d28427f\",\n          \"description\" : \"\",\n          \"required\" : false,\n          \"dataType\" : \"String\",\n          \"type\" : null,\n          \"defaultValue\" : null,\n          \"validateType\" : \"\",\n          \"error\" : \"\",\n          \"expression\" : \"\",\n          \"children\" : [ ]\n        }, {\n          \"name\" : \"name\",\n          \"value\" : \"1查询List\",\n          \"description\" : \"\",\n          \"required\" : false,\n          \"dataType\" : \"String\",\n          \"type\" : null,\n          \"defaultValue\" : null,\n          \"validateType\" : \"\",\n          \"error\" : \"\",\n          \"expression\" : \"\",\n          \"children\" : [ ]\n        } ]\n      } ]\n    } ]\n  }\n}\r\n================================\r\n// 数据权限接口示例\nvar sql = \"\"\"\n    select id, name from magic_api_backup @SQL_FILTER_QYDM limit 10\n\"\"\"\nreturn db.select(sql);');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/group.json', '{\n  \"properties\" : { },\n  \"id\" : \"2ee24b00f70246e7b785b95958407412\",\n  \"name\" : \"测试\",\n  \"type\" : \"api\",\n  \"parentId\" : \"aa31fa7f644140b98e22a67365c8db6b\",\n  \"path\" : \"/test/open\",\n  \"createTime\" : 1707208084245,\n  \"updateTime\" : 1661907681263,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : \"magic\",\n  \"paths\" : [ ],\n  \"options\" : [ ]\n}');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/未定义名称(复制).ms', '{\n  \"properties\" : { },\n  \"id\" : \"copy1715422523973d73790\",\n  \"script\" : null,\n  \"groupId\" : \"2ee24b00f70246e7b785b95958407412\",\n  \"name\" : \"未定义名称(复制)\",\n  \"createTime\" : null,\n  \"updateTime\" : 1715423630858,\n  \"lock\" : null,\n  \"createBy\" : null,\n  \"updateBy\" : \"magic\",\n  \"path\" : \"/async_test\",\n  \"method\" : \"POST\",\n  \"parameters\" : [ {\n    \"name\" : \"file\",\n    \"value\" : { },\n    \"description\" : null,\n    \"required\" : false,\n    \"dataType\" : \"MultipartFile\",\n    \"type\" : null,\n    \"defaultValue\" : null,\n    \"validateType\" : null,\n    \"error\" : null,\n    \"expression\" : null,\n    \"children\" : null\n  } ],\n  \"options\" : [ ],\n  \"requestBody\" : \"\",\n  \"headers\" : [ ],\n  \"paths\" : [ ],\n  \"responseBody\" : \"{\\n    \\\"code\\\": 100200,\\n    \\\"msg\\\": \\\"success\\\",\\n    \\\"data\\\": \\\"aaaaaaa\\\"\\n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : null\n}\r\n================================\r\nimport \"@post:/dataapi/test/open/test\" as test\nasync test()\nreturn \'aaaaaaa\'');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/API/测试/未定义名称.ms', '{\n  \"properties\" : { },\n  \"id\" : \"d3cf5fac26be44859e6966c736f83f8f\",\n  \"script\" : null,\n  \"groupId\" : \"2ee24b00f70246e7b785b95958407412\",\n  \"name\" : \"未定义名称\",\n  \"createTime\" : null,\n  \"updateTime\" : 1715423591536,\n  \"lock\" : null,\n  \"createBy\" : null,\n  \"updateBy\" : \"magic\",\n  \"path\" : \"/test\",\n  \"method\" : \"POST\",\n  \"parameters\" : [ {\n    \"name\" : \"file\",\n    \"value\" : { },\n    \"description\" : null,\n    \"required\" : false,\n    \"dataType\" : \"MultipartFile\",\n    \"type\" : null,\n    \"defaultValue\" : null,\n    \"validateType\" : null,\n    \"error\" : null,\n    \"expression\" : null,\n    \"children\" : null\n  } ],\n  \"options\" : [ ],\n  \"requestBody\" : \"\",\n  \"headers\" : [ ],\n  \"paths\" : [ ],\n  \"responseBody\" : \"{\\n    \\\"code\\\": 100200,\\n    \\\"msg\\\": \\\"success\\\",\\n    \\\"data\\\": \\\"Hello magic-api\\\"\\n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : null\n}\r\n================================\r\nimport http;\nimport cn.hutool.json.JSONUtil;\n// return http.connect(\'http://127.0.0.1:8980/auth/open/config\').post().getBody()\nfor(i in range(1,5)){\n    // Thread.sleep(1000);\n    let res = http.connect(\'http://127.0.0.1:8980/auth/open/config\').post().getBody()\n    let json = JSONUtil.toJsonStr(res)\n    log.info(\"\"+i)\n    // db.insert(\"\"\"\n    // insert into test(id, val) VALUES (${i}, \'${json}\');\n    // \"\"\")\n    db.table(\'test\').insert({ id : i, val : json})\n}\nreturn \'Hello magic-api\'');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/component/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/datasource/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/function/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/function/数学函数/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/function/数学函数/group.json', '{\n  \"properties\" : { },\n  \"id\" : \"3a290c99247a4091b6f3444202834b9a\",\n  \"name\" : \"数学函数\",\n  \"type\" : \"function\",\n  \"parentId\" : \"0\",\n  \"path\" : \"/math\",\n  \"createTime\" : 1707208084249,\n  \"updateTime\" : null,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : null,\n  \"paths\" : [ ],\n  \"options\" : [ ]\n}');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/function/数学函数/系统统一数值保留位数方法.ms', '{\n  \"properties\" : { },\n  \"id\" : \"c2a4041df7b644a5a784806b905836b0\",\n  \"script\" : null,\n  \"groupId\" : \"3a290c99247a4091b6f3444202834b9a\",\n  \"name\" : \"系统统一数值保留位数方法\",\n  \"createTime\" : 1707208084281,\n  \"updateTime\" : 1707208102038,\n  \"lock\" : null,\n  \"createBy\" : \"magic\",\n  \"updateBy\" : \"magic\",\n  \"path\" : \"toRound\",\n  \"description\" : null,\n  \"returnType\" : null,\n  \"mappingPath\" : null,\n  \"parameters\" : [ ]\n}\r\n================================\r\nif(value==null || value==0){\n    return 0.00;\n}\nreturn value.round(2)');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/task/', 'this is directory');
COMMIT;

-- ----------------------------
-- Table structure for sys_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_api`;
CREATE TABLE `sys_api` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `parent_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '父id，0表示根节点',
  `type` smallint(6) NOT NULL DEFAULT '2' COMMENT '类型：1目录，2uri',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'api接口名称',
  `uri` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'service接口的权限验证规则，如：/user/op/save、/user/op/**',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `orders` int(11) DEFAULT '0' COMMENT '排序（倒序）',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  PRIMARY KEY (`id`),
  KEY `sys_api_parent_id_index` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统接口表';

-- ----------------------------
-- Records of sys_api
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型编码，形如：sex（英文，多个单词下划线分隔）',
  `label` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称，形如：男（用于给用户展示）',
  `value` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '值，形如：1（业务表保存该值）',
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典码',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `orders` int(11) DEFAULT '0' COMMENT '排序（倒序）',
  `enable` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否启用(默认1,禁用0)',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1263379764819787776', 'sex', '男', '1', 'men', '性别（1男；2女）', 100, 1, '1183307967865425920', '2019-08-14 15:56:23', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1263719812610129920', 'sex', '女', '2', 'women', '性别（1男；2女）', 100, 1, '1183307967865425920', '2019-08-14 15:57:55', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1264729567587729408', 'menu_type', '目录', '1', 'catalog', '菜单类型（1目录；2菜单；3按钮）', 100, 1, '1183307967865425920', '2019-08-30 10:12:26', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1264736431524282368', 'menu_type', '菜单', '2', 'menu', '菜单类型（1目录；2菜单；3按钮）', 100, 1, '1183307967865425920', '2019-08-19 19:04:48', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1264739471937503232', 'menu_type', '按钮', '3', 'button', '菜单类型（1目录；2菜单；3按钮）', 100, 1, '1183307967865425920', '2019-08-19 19:05:05', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1264825012045479936', 'http_method', 'POST', 'POST', 'POST', '请求方法（POST;GET;DELETE）', 100, 1, '1183307967865425920', '2020-08-23 10:11:02', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1264825890546647040', 'http_method', 'GET', 'GET', 'GET', '请求方法（POST;GET;DELETE）', 100, 1, '1183307967865425920', '2020-08-23 10:11:12', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1264844589735346176', 'http_method', 'DELETE', 'DELETE', 'DELETE', '请求方法（POST;GET;DELETE）', 100, 1, '1183307967865425920', '2020-08-23 10:12:22', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1264845383213776896', 'exception_type', 'BusinessException', '1', 'BusinessException', '异常类型（1BusinessException；7其他Exception）', 100, 1, '1183307967865425920', '2020-08-16 12:47:47', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265106590587944960', 'exception_type', '其他Exception', '7', 'Exception', '异常类型（1BusinessException；7其他Exception）', 100, 1, '1183307967865425920', '2020-08-16 16:45:49', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265126987110809600', 'enable_status_op', '启用', '1', 'enable', '启用状态（1启用；0停用）', 100, 1, '1183307967865425920', '2020-10-26 09:40:09', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265214829610663936', 'enable_status_op', '停用', '0', 'disable', '启用状态（1启用；0停用）', 100, 1, '1183307967865425920', '2020-10-26 09:40:28', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265450642214748160', 'enable_status', '启用', '1', 'enable', '启用状态（1启用；0已停用）', 100, 1, '1183307967865425920', '2020-07-14 15:36:37', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265452489088434176', 'enable_status', '已停用', '0', 'disable', '启用状态（1启用；0已停用）', 100, 1, '1183307967865425920', '2020-07-14 15:38:23', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265459040406732800', 'client_type', 'web端', '1', 'web', '客户端类型', 100, 1, '1183307967865425920', '2019-08-19 19:06:33', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265460792661114880', 'client_type', '移动端', '2', 'app', '客户端类型', 100, 1, '1183307967865425920', '2019-08-19 19:06:54', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265480242605064192', 'api_type', '目录', '1', 'catalog', '接口类型（1目录；2接口）', 100, 1, '1183307967865425920', '2019-10-11 19:52:45', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1265486690080784384', 'api_type', '接口', '2', 'api', '接口类型（1目录；2接口）', 100, 1, '1183307967865425920', '2019-10-17 18:38:40', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1346372317949026304', 'excel_template_demo_user', '用户管理demo导入模版.xlsx', 'hello/excel_template_demo_user.xlsx', 'excel_template_demo_user', 'value值是文件在文件服务器的key', 100, 1, '1183307967865425920', '2021-01-05 16:25:49', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1716475761954697216', 'data_scope_org', '全部数据权限', '10', 'data_scope_org_all', '数据权限范围', 100, 1, '1183307967865425920', '2023-10-23 23:24:47', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1716475929915600896', 'data_scope_org', '自定义数据权限', '20', 'data_scope_org_custom', '数据权限范围', 100, 1, '1183307967865425920', '2023-10-23 23:25:27', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1716476027651272704', 'data_scope_org', '部门数据权限', '30', 'data_scope_org_dept', '数据权限范围', 100, 1, '1183307967865425920', '2023-10-23 23:25:50', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1716476229883834368', 'data_scope_org', '部门及以下数据权限', '40', 'data_scope_org_dept_and_below', '数据权限范围', 100, 1, '1183307967865425920', '2023-10-23 23:26:38', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1716476421920043008', 'data_scope_org', '仅本人', '50', 'data_scope_org_only_myself', '数据权限范围', 100, 1, '1183307967865425920', '2023-10-23 23:27:24', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829062277964804096', 'biz_message_type', '消息/站内信', '1', '1', '消息分类(1消息/站内信；5公告；9其他)', 100, 1, '1183307967865425920', '2024-08-29 15:43:24', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829062344096395264', 'biz_message_type', '公告', '5', '5', '消息分类(1消息/站内信；5公告；9其他)', 100, 1, '1183307967865425920', '2024-08-29 15:43:40', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829062383304749056', 'biz_message_type', '其他', '9', '9', '消息分类(1消息/站内信；5公告；9其他)', 100, 1, '1183307967865425920', '2024-08-29 15:43:49', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829062487981993984', 'biz_message_priority_type', '普通', '1', '1', '消息优先级(1普通；5紧急)', 100, 1, '1183307967865425920', '2024-08-29 15:44:14', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829062769453346816', 'biz_message_priority_type', '紧急', '5', '5', '消息优先级(1普通；5紧急)', 100, 1, '1183307967865425920', '2024-08-29 15:45:21', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829062988882554880', 'biz_message_range_type', '全部用户', '1', '1', '发送范围类型(1全部用户；2指定单位/部门；3指定用户)', 100, 1, '1183307967865425920', '2024-08-29 15:46:14', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829063055957864448', 'biz_message_range_type', '指定单位/部门', '2', '2', '发送范围类型(1全部用户；2指定单位/部门；3指定用户)', 100, 1, '1183307967865425920', '2024-08-29 15:46:30', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829063101776441344', 'biz_message_range_type', '指定用户', '3', '3', '发送范围类型(1全部用户；2指定单位/部门；3指定用户)', 100, 1, '1183307967865425920', '2024-08-29 15:46:40', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829063793496862720', 'biz_message_status', '暂存', '0', '0', '消息状态(0暂存；1下发)', 100, 1, '1183307967865425920', '2024-08-29 15:49:25', NULL, NULL, 0);
INSERT INTO `sys_dict` (`id`, `type`, `label`, `value`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1829063841467117568', 'biz_message_status', '已下发', '1', '1', '消息状态(0暂存；1下发)', 100, 1, '1183307967865425920', '2024-08-29 15:49:37', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `request_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求id',
  `api_description` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接口描述/操作描述',
  `uri` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'URI',
  `method` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求类型',
  `parameter` longtext COLLATE utf8mb4_unicode_ci COMMENT '请求入参',
  `result` longtext COLLATE utf8mb4_unicode_ci COMMENT '响应结果',
  `success` smallint(6) DEFAULT '1' COMMENT '请求是否成功，1成功，0异常；默认1',
  `start_time` bigint(20) NOT NULL COMMENT '操作时间',
  `spend_time` bigint(20) NOT NULL COMMENT '消耗时间',
  `ip` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'IP地址',
  `url` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'URL',
  `user_agent` text COLLATE utf8mb4_unicode_ci COMMENT '用户标识',
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户id',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户真实姓名',
  `client_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '应用类型,对应字典表client_type,由于前端分离,后台系统可能要对接多个客户端,如web，app',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  PRIMARY KEY (`id`),
  KEY `log_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统请求日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_log_exception
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_exception`;
CREATE TABLE `sys_log_exception` (
  `log_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应sys_log表id',
  `type` smallint(6) NOT NULL COMMENT '1.BusinessException；7.其他Exception',
  `content` longtext COLLATE utf8mb4_unicode_ci COMMENT '异常堆栈信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统异常日志';

-- ----------------------------
-- Records of sys_log_exception
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login` (
  `log_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应sys_log表id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统登录日志';

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `parent_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '父id，0表示根节点',
  `type` smallint(6) NOT NULL DEFAULT '1' COMMENT '资源类型1:目录,2:菜单,3:按钮,4:其他',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源名称',
  `uri` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单uri',
  `code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源编码，前端根据该code控制资源的显示隐藏',
  `icon` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源图标class',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `orders` int(11) NOT NULL DEFAULT '0' COMMENT '排序（倒序）',
  `client_type` smallint(6) NOT NULL COMMENT '应用类型,对应字典表client_type,由于前端分离,后台系统可能要对接多个客户端,如web，app',
  `enable` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否启用(默认1,禁用0)',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  `dynamic_route` smallint(6) NOT NULL DEFAULT '0' COMMENT '动态创建路由（1是，0否）',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id_index` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限资源表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1167003012795863040', '0', 1, '系统管理', NULL, NULL, 'el-icon-menu', NULL, 100, 1, 1, '1183307967865425920', '2019-08-29 17:16:14', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1167003348218548224', '1167003012795863040', 2, '后台接口管理', '/sys/api', NULL, 'el-icon-menu', NULL, 70, 1, 1, '1183307967865425920', '2019-08-29 17:17:34', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1167003541898924032', '1167003012795863040', 2, '菜单管理', '/sys/menu', NULL, 'el-icon-menu', NULL, 80, 1, 1, '1183307967865425920', '2019-08-29 17:18:20', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1167003860695388160', '1167003012795863040', 2, '角色管理', '/sys/role', NULL, 'el-icon-menu', NULL, 90, 1, 1, '1183307967865425920', '2019-08-29 17:19:36', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1167004033748176896', '1167003012795863040', 2, '用户管理', '/sys/user', NULL, 'el-icon-menu', NULL, 100, 1, 1, '1183307967865425920', '2019-08-29 17:20:18', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1184016665428496384', '1167003012795863040', 2, '数据字典管理', '/sys/dict', NULL, 'el-icon-menu', NULL, 60, 1, 1, '1183307967865425920', '2019-10-15 16:02:25', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1186196450544062464', '1184016665428496384', 3, '新增', NULL, 'sys_dict_add', NULL, NULL, 0, 1, 1, '1183307967865425920', '2019-10-21 16:24:06', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1186196577551781888', '1184016665428496384', 3, '编辑', NULL, 'sys_dict_edit', NULL, NULL, 0, 1, 1, '1183307967865425920', '2019-10-21 16:24:37', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1186196632895623168', '1184016665428496384', 3, '删除', NULL, 'sys_dict_del', NULL, NULL, 0, 1, 1, '1183307967865425920', '2019-10-21 16:24:50', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1294564582339993600', '0', 1, '系统日志', NULL, NULL, 'el-icon-menu', NULL, 90, 1, 1, '1183307967865425920', '2020-08-15 17:20:22', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1294564837475311616', '1294564582339993600', 2, '登录日志', '/sys/log/login', NULL, 'el-icon-menu', NULL, 10, 1, 1, '1183307967865425920', '2020-08-15 17:21:23', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1294564916516970496', '1294564582339993600', 2, '异常日志', '/sys/log/exception', NULL, 'el-icon-menu', NULL, 9, 1, 1, '1183307967865425920', '2020-08-15 17:21:42', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1297343214116495360', '1294564582339993600', 2, '请求日志', '/sys/log/request', NULL, 'el-icon-menu', NULL, 8, 1, 1, '1183307967865425920', '2020-08-23 09:21:40', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320931477909495808', '1167004033748176896', 3, '删除', NULL, 'sys_user_del', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:33:00', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320931564630925312', '1167004033748176896', 3, '编辑', NULL, 'sys_user_edit', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:33:21', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320931614656389120', '1167004033748176896', 3, '新增', NULL, 'sys_user_add', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:33:33', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320931794940157952', '1167004033748176896', 3, '重置密码', NULL, 'sys_user_reset_password', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:34:16', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320931901571948544', '1167004033748176896', 3, '分配角色', NULL, 'sys_user_assign_role', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:34:41', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320932706110758912', '1167003860695388160', 3, '删除', NULL, 'sys_role_del', NULL, NULL, 90, 1, 1, '1183307967865425920', '2020-10-27 11:37:53', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320932756048142336', '1167003860695388160', 3, '编辑', NULL, 'sys_role_edit', NULL, NULL, 90, 1, 1, '1183307967865425920', '2020-10-27 11:38:05', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320932808464359424', '1167003860695388160', 3, '新增', NULL, 'sys_role_add', NULL, NULL, 90, 1, 1, '1183307967865425920', '2020-10-27 11:38:17', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320933159758290944', '1167003860695388160', 3, '功能权限', '', 'sys_role_assign_menu', NULL, NULL, 90, 1, 1, '1183307967865425920', '2020-10-27 11:39:41', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320935611211538432', '1167003541898924032', 3, '删除', NULL, 'sys_menu_del', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:49:26', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320935660888875008', '1167003541898924032', 3, '编辑', NULL, 'sys_menu_edit', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:49:38', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320935692862054400', '1167003541898924032', 3, '新增', NULL, 'sys_menu_add', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:49:45', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320935970613059584', '1167003541898924032', 3, '分配接口权限', NULL, 'sys_menu_assign_api', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:50:51', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320937209568518144', '1167003348218548224', 3, '删除', NULL, 'sys_api_del', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:55:47', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320937263666651136', '1167003348218548224', 3, '编辑', NULL, 'sys_api_edit', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:56:00', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320937303722254336', '1167003348218548224', 3, '新增', NULL, 'sys_api_add', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 11:56:09', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320940438696058880', '1294564837475311616', 3, '详情', NULL, 'sys_login_log_detail', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 12:08:37', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320940611539132416', '1294564916516970496', 3, '详情', NULL, 'sys_exception_log_detail', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 12:09:18', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1320940764283101184', '1297343214116495360', 3, '详情', NULL, 'sys_request_log_detail', NULL, NULL, 100, 1, 1, '1183307967865425920', '2020-10-27 12:09:54', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1346742291342516224', '1167004033748176896', 3, '重置锁定状态', '', 'sys_user_reset_lock', NULL, NULL, 100, 1, 1, '1183307967865425920', '2021-01-06 16:55:58', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1399671523454836736', '1167004033748176896', 3, '扮演该用户', '', 'sys_user_role_play', NULL, NULL, 100, 1, 1, '1183307967865425920', '2021-06-01 18:17:50', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1413421489015840768', '0', 1, '移动端目录test', '', '', 'el-icon-menu', NULL, 100, 2, 1, '1183307967865425920', '2021-07-09 16:55:17', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1413421661418512384', '1413421489015840768', 3, '移动端按钮1', '', 'btn_test_1', 'el-icon-menu', NULL, 100, 2, 1, '1183307967865425920', '2021-07-09 16:55:58', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1432263397794734080', '1167004033748176896', 3, '查看用户权限', '', 'sys_user_view_menu', NULL, NULL, 100, 1, 1, '1183307967865425920', '2021-08-30 16:46:18', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1484434828524720128', '1167003012795863040', 2, '租户管理', '/sys/tenant', NULL, 'el-icon-menu', NULL, 110, 1, 1, '1183307967865425920', '2022-01-21 15:56:57', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1484436755459280896', '1484434828524720128', 3, '删除', '', 'sys_tenant_del', NULL, NULL, 90, 1, 1, '1183307967865425920', '2022-01-21 16:04:36', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1484436806084530176', '1484434828524720128', 3, '编辑', '', 'sys_tenant_edit', NULL, NULL, 90, 1, 1, '1183307967865425920', '2022-01-21 16:04:48', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1484436885549813760', '1484434828524720128', 3, '新增', '', 'sys_tenant_add', NULL, NULL, 90, 1, 1, '1183307967865425920', '2022-01-21 16:05:07', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1675771738935439360', '0', 1, '系统监控', '', '', 'el-icon-menu', NULL, 80, 1, 1, '1183307967865425920', '2023-07-03 15:41:31', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1675771907374493696', '1675771738935439360', 2, 'SQL监控', 'http://localhost:8980/druid/sql.html', '', 'el-icon-menu', NULL, 100, 1, 1, '1183307967865425920', '2023-07-03 15:42:11', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1675772041856462848', '1675771738935439360', 2, '接口文档', 'http://localhost:8980/doc.html', '', 'el-icon-menu', NULL, 99, 1, 1, '1183307967865425920', '2023-07-03 15:42:43', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1700896300751695872', '1167003012795863040', 2, '组织机构管理', '/sys/org', NULL, 'el-icon-menu', NULL, 85, 1, 1, '1183307967865425920', '2023-09-10 23:37:34', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1707051267676090368', '1700896300751695872', 3, '删除', '', 'sys_org_del', NULL, NULL, 100, 1, 1, '1183307967865425920', '2023-09-27 23:15:12', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1707051308641857536', '1700896300751695872', 3, '新增', '', 'sys_org_add', NULL, NULL, 100, 1, 1, '1183307967865425920', '2023-09-27 23:15:22', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1707051378833534976', '1700896300751695872', 3, '编辑', '', 'sys_org_edit', NULL, NULL, 100, 1, 1, '1183307967865425920', '2023-09-27 23:15:39', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1716473768230039552', '1167003860695388160', 3, '数据权限', '', 'sys_role_data_scope', NULL, NULL, 90, 1, 1, '1183307967865425920', '2023-10-23 23:16:52', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1737490945684844544', '1675771738935439360', 2, 'MagicApi', 'http://localhost:8980/magic/web/index.html', '', 'el-icon-menu', NULL, 98, 1, 1, '1183307967865425920', '2023-12-20 23:11:37', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1777645054493372416', '1167004033748176896', 3, '重置过期状态', '', 'sys_user_reset_expired', NULL, NULL, 100, 1, 1, '1183307967865425920', '2024-04-09 18:29:43', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1801560933857144832', '1297343214116495360', 3, '导出', '', 'sys_request_log_export', NULL, NULL, 100, 1, 1, '1183307967865425920', '2024-06-14 18:22:53', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1801884563862761472', '1294564916516970496', 3, '导出', '', 'sys_exception_log_export', NULL, NULL, 100, 1, 1, '1183307967865425920', '2024-06-15 15:48:52', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1801884751843078144', '1294564837475311616', 3, '导出', '', 'sys_login_log_export', NULL, NULL, 100, 1, 1, '1183307967865425920', '2024-06-15 15:49:37', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1818488832413446144', '1675771738935439360', 2, '在线用户', '/sys/user/online', '', 'el-icon-menu', NULL, 90, 1, 1, '1183307967865425920', '2024-07-31 11:28:18', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1818505257618812928', '1818488832413446144', 3, '强制下线', '', 'sys-user-forced-offline', 'el-icon-menu', NULL, 100, 1, 1, '1183307967865425920', '2024-07-31 12:33:34', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1819199107717750784', '1167003012795863040', 2, '在线用户', '/sys/user/online', '', 'el-icon-menu', NULL, 30, 1, 1, '1183307967865425920', '2024-08-02 10:30:41', '1183307967865425920', NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1829052113719177216', '1167003012795863040', 2, '消息/公告管理', '/sys/message', NULL, 'el-icon-message', NULL, 50, 1, 1, '1183307967865425920', '2024-08-29 15:03:01', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1829053079164071936', '1829052113719177216', 3, '新增', NULL, 'sys_message_add', NULL, NULL, 0, 1, 1, '1183307967865425920', '2024-08-29 15:06:51', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1829053126391934976', '1829052113719177216', 3, '编辑', NULL, 'sys_message_edit', NULL, NULL, 0, 1, 1, '1183307967865425920', '2024-08-29 15:07:02', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1829053166334291968', '1829052113719177216', 3, '删除', NULL, 'sys_message_del', NULL, NULL, 0, 1, 1, '1183307967865425920', '2024-08-29 15:07:12', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1831142939964911616', '1167003012795863040', 2, '附件管理', '/sys/attachment', NULL, 'el-icon-files', NULL, 50, 1, 1, '1183307967865425920', '2024-09-04 09:31:13', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1831143089802227712', '1831142939964911616', 3, '新增', NULL, 'sys_attachment_add', NULL, NULL, 0, 1, 1, '1183307967865425920', '2024-09-04 09:31:48', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1831143138753949696', '1831142939964911616', 3, '编辑', NULL, 'sys_attachment_edit', NULL, NULL, 0, 1, 1, '1183307967865425920', '2024-09-04 09:32:00', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` (`id`, `parent_id`, `type`, `name`, `uri`, `code`, `icon`, `remark`, `orders`, `client_type`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `dynamic_route`) VALUES ('1831143199827210240', '1831142939964911616', 3, '删除', NULL, 'sys_attachment_del', NULL, NULL, 0, 1, 1, '1183307967865425920', '2024-09-04 09:32:14', NULL, NULL, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_api`;
CREATE TABLE `sys_menu_api` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `menu_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sys_menu表id',
  `uri` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sys_api表uri(支持配置swagger)',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `method` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方法（POST、GET、DELETE、PUT）',
  PRIMARY KEY (`id`),
  KEY `sys_menu_api_api_id_index_copy1_copy1` (`uri`) USING BTREE,
  KEY `sys_menu_api_menu_id_index_copy1_copy1` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源和api关系表';

-- ----------------------------
-- Records of sys_menu_api
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320932121940680704', '1320931477909495808', '/auth/sys/user/op/del', '1183307967865425920', '2020-10-27 11:35:34', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320932141314170880', '1320931564630925312', '/auth/sys/user/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:35:38', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320932161237114880', '1320931614656389120', '/auth/sys/user/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:35:43', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320932187925471232', '1320931794940157952', '/auth/sys/user/op/updatePassword', '1183307967865425920', '2020-10-27 11:35:49', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320934537494880256', '1320932706110758912', '/auth/sys/role/op/batchDel', '1183307967865425920', '2020-10-27 11:45:10', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320934557988249600', '1320932756048142336', '/auth/sys/role/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:45:15', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320934572529901568', '1320932808464359424', '/auth/sys/role/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:45:18', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320935430965518336', '1320933159758290944', '/auth/sys/role/op/updateMenuByRoleId', '1183307967865425920', '2020-10-27 11:48:43', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320935430965518337', '1320933159758290944', '/auth/sys/menu/view/findAll', '1183307967865425920', '2020-10-27 11:48:43', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320936084530356224', '1320935611211538432', '/auth/sys/menu/op/batchDel', '1183307967865425920', '2020-10-27 11:51:19', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320936099810205696', '1320935660888875008', '/auth/sys/menu/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:51:22', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320936137558941696', '1320935692862054400', '/auth/sys/menu/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:51:31', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320936213366792192', '1320935970613059584', '/auth/sys/menu/op/updateApiByMenuId', '1183307967865425920', '2020-10-27 11:51:49', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320936213366792193', '1320935970613059584', '/auth/sys/api/view/findAll', '1183307967865425920', '2020-10-27 11:51:49', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320937394528935936', '1320937209568518144', '/auth/sys/api/op/batchDel', '1183307967865425920', '2020-10-27 11:56:31', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320937452162867200', '1320937263666651136', '/auth/sys/api/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:56:45', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320937469179158528', '1320937303722254336', '/auth/sys/api/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:56:49', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320937636452196352', '1186196450544062464', '/auth/sys/dict/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:57:29', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320937658648453120', '1186196577551781888', '/auth/sys/dict/op/insertOrUpdate', '1183307967865425920', '2020-10-27 11:57:34', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320938065969897472', '1167003541898924032', '/auth/sys/menu/view/findAll', '1183307967865425920', '2020-10-27 11:59:11', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320938084676493312', '1167003348218548224', '/auth/sys/api/view/findAll', '1183307967865425920', '2020-10-27 11:59:15', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320940472510537728', '1320940438696058880', '/auth/sys/log/view/login/detail', '1183307967865425920', '2020-10-27 12:08:45', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320940674814402560', '1320940611539132416', '/auth/sys/log/view/exception/detail', '1183307967865425920', '2020-10-27 12:09:33', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320940691595812864', '1294564916516970496', '/auth/sys/log/view/exception/findPage', '1183307967865425920', '2020-10-27 12:09:37', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320940800962289664', '1320940764283101184', '/auth/sys/log/view/request/detail', '1183307967865425920', '2020-10-27 12:10:03', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1320940814409228288', '1297343214116495360', '/auth/sys/log/view/request/findPage', '1183307967865425920', '2020-10-27 12:10:06', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1386568199806083072', '1320931901571948544', '/auth/sys/user/op/updateRoleByUserId', '1183307967865425920', '2021-04-26 14:29:54', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1386568199806083073', '1320931901571948544', '/auth/sys/role/view/findAll', '1183307967865425920', '2021-04-26 14:29:54', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1386568199806083074', '1320931901571948544', '/auth/sys/role/view/findRoleByUser', '1183307967865425920', '2021-04-26 14:29:54', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1399671796470472704', '1346742291342516224', '/auth/sys/user/op/resetLock', '1183307967865425920', '2021-06-01 18:18:55', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1399671816976424960', '1399671523454836736', '/auth/op/rolePlay', '1183307967865425920', '2021-06-01 18:19:00', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1432263896291958784', '1432263397794734080', '/auth/sys/menu/view/findAll', '1183307967865425920', '2021-08-30 16:48:17', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1432263896291958785', '1432263397794734080', '/auth/sys/menu/view/getMenuIdByUserId', '1183307967865425920', '2021-08-30 16:48:17', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1484440819228151808', '1484436755459280896', '/auth/sys/tenant/op/del', '1183307967865425920', '2022-01-21 16:20:45', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1484440851574624256', '1484436806084530176', '/auth/sys/tenant/op/insertOrUpdate', '1183307967865425920', '2022-01-21 16:20:53', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1484440872722305024', '1484436885549813760', '/auth/sys/tenant/op/insertOrUpdate', '1183307967865425920', '2022-01-21 16:20:58', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1707046698304843776', '1700896300751695872', '/auth/sys/org/view/findAll', '1183307967865425920', '2023-09-27 22:57:03', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1707051431862120448', '1707051378833534976', '/auth/sys/org/op/insertOrUpdate', '1183307967865425920', '2023-09-27 23:15:51', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1707051454075154432', '1707051308641857536', '/auth/sys/org/op/insertOrUpdate', '1183307967865425920', '2023-09-27 23:15:57', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1707051480130170880', '1707051267676090368', '/auth/sys/org/op/batchDel', '1183307967865425920', '2023-09-27 23:16:03', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1721441267656077312', '1716473768230039552', '/auth/sys/role/op/updateOrgDataScopeByRoleId', '1183307967865425920', '2023-11-06 16:15:56', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1729099847700492288', '1186196632895623168', '/auth/sys/dict/op/batchDel', '1183307967865425920', '2023-11-27 19:28:23', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1731968406713053184', '1184016665428496384', '/auth/sys/dict/view/findPage', '1183307967865425920', '2023-12-05 17:27:01', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1733764011160416256', '1484434828524720128', '/auth/sys/tenant/view/findPage', '1183307967865425920', '2023-12-10 16:22:07', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1733765499756654592', '1167004033748176896', '/auth/sys/tenant/view/findAll', '1183307967865425920', '2023-12-10 16:28:02', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1733765499756654593', '1167004033748176896', '/auth/sys/user/view/findPage', '1183307967865425920', '2023-12-10 16:28:02', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1733768832567390208', '1167003860695388160', '/auth/sys/role/view/findAll', '1183307967865425920', '2023-12-10 16:41:16', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1733768832567390209', '1167003860695388160', '/auth/sys/tenant/view/findAll', '1183307967865425920', '2023-12-10 16:41:16', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1777647010770104320', '1777645054493372416', '/auth/sys/user/op/resetExpired', '1183307967865425920', '2024-04-09 18:37:29', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1801560991168114688', '1801560933857144832', '/auth/sys/log/op/request/export', '1183307967865425920', '2024-06-14 18:23:06', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1801884661862674432', '1801884563862761472', '/auth/sys/log/op/exception/export', '1183307967865425920', '2024-06-15 15:49:15', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1801884832054947840', '1801884751843078144', '/auth/sys/log/op/login/export', '1183307967865425920', '2024-06-15 15:49:56', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1818489208958005248', '1818488832413446144', '/auth/op/onlineUserList', '1183307967865425920', '2024-07-31 11:29:48', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1818505318432026624', '1818505257618812928', '/auth/op/forcedOffline', '1183307967865425920', '2024-07-31 12:33:49', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1819199356406423552', '1819199107717750784', '/auth/op/onlineUserList', '1183307967865425920', '2024-08-02 10:31:21', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1846759098713812992', '1675771907374493696', '/auth/demo/user/open/op/insertOrUpdate', '1183307967865425920', '2024-10-17 11:44:15', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1846798101760618496', '1294564837475311616', '/auth/demo/user/open/op/insertOrUpdate', '1183307967865425920', '2024-10-17 14:19:14', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1846798101760618497', '1294564837475311616', '/auth/sys/log/view/login/findPage', '1183307967865425920', '2024-10-17 14:19:14', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1846798101760618498', '1294564837475311616', '/auth/demo/user/open/op/insertOrUpdate', '1183307967865425920', '2024-10-17 14:19:14', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1846798101760618499', '1294564837475311616', '/auth/demo/user/open/view/findPage', '1183307967865425920', '2024-10-17 14:19:14', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1846798101760618500', '1294564837475311616', '/auth/sys/log/view/login/findPage', '1183307967865425920', '2024-10-17 14:19:14', NULL, NULL, NULL);
INSERT INTO `sys_menu_api` (`id`, `menu_id`, `uri`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `method`) VALUES ('1945412322915344384', '1320940764283101184', '/auth/sys/log/view/run_log', '1183307967865425920', '2025-12-03 11:39:24', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID主键',
  `parent_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '父级组织ID',
  `org_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织编码',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织名称',
  `level` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构等级',
  `level_name` text COLLATE utf8mb4_unicode_ci COMMENT '组织机构等级中文名称',
  `org_full_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构全路径(使用英文逗号分割)',
  `org_full_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构id全路径(使用英文逗号分割)',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `orders` int(11) DEFAULT NULL COMMENT '排序（倒序）',
  `enable` smallint(6) NOT NULL COMMENT '是否启用(默认1,禁用0)',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  PRIMARY KEY (`id`),
  KEY `biz_org_parent_id_index_copy2_copy1` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织机构表';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335117679538176', '0', NULL, 'A公司', NULL, NULL, 'A公司', '1699335117679538176', NULL, 1, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335158242652160', '1699335117679538176', NULL, '营销部', NULL, NULL, 'A公司,营销部', '1699335117679538176,1699335158242652160', NULL, 2, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335158448173056', '1699335117679538176', NULL, '人事部', NULL, NULL, 'A公司,人事部', '1699335117679538176,1699335158448173056', NULL, 3, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335164840292352', '1699335158242652160', NULL, '市场部', NULL, NULL, 'A公司,营销部,市场部', '1699335117679538176,1699335158242652160,1699335164840292352', NULL, 4, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335165037424640', '1699335158242652160', NULL, '销售部', NULL, NULL, 'A公司,营销部,销售部', '1699335117679538176,1699335158242652160,1699335165037424640', NULL, 5, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335172998213632', '1699335158448173056', NULL, '招聘部', NULL, NULL, 'A公司,人事部,招聘部', '1699335117679538176,1699335158448173056,1699335172998213632', NULL, 6, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335177926520832', '1699335158448173056', NULL, '培训部', NULL, NULL, 'A公司,人事部,培训部', '1699335117679538176,1699335158448173056,1699335177926520832', NULL, 7, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335177976852480', '1699335164840292352', NULL, '市场推广组', NULL, NULL, 'A公司,营销部,市场部,市场推广组', '1699335117679538176,1699335158242652160,1699335164840292352,1699335177976852480', NULL, 8, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335266766073856', '1699335164840292352', NULL, '销售团队', NULL, NULL, 'A公司,营销部,市场部,销售团队', '1699335117679538176,1699335158242652160,1699335164840292352,1699335266766073856', NULL, 9, 1, '1183307967865425920', '2023-09-10 12:07:17', NULL, NULL, 0);
INSERT INTO `sys_org` (`id`, `parent_id`, `org_code`, `name`, `level`, `level_name`, `org_full_name`, `org_full_id`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1699335266992566272', '1699335165037424640', NULL, '招聘团队', NULL, NULL, 'A公司,营销部,销售部/招聘团队', '1699335117679538176,1699335158242652160,1699335165037424640,1699335266992566272', NULL, 10, 1, '1183307967865425920', '2023-09-10 12:07:17', '1983839748202696704', '2025-12-02 09:24:25', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `tenant_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户id',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `orders` int(11) NOT NULL DEFAULT '0' COMMENT '排序（倒序）',
  `enable` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否启用(默认1,禁用0)',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  `data_scope_org` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构数据权限范围，字典类型：data_scope_org',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `tenant_id`, `name`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `data_scope_org`) VALUES ('1164384808021921792', '1483736224997847040', '超级管理员', 'tenant-admin-role-admin', '', 100, 1, '1183307967865425920', '2019-08-22 11:54:07', '1183307967865425920', '2023-11-10 18:21:50', 0, '10');
INSERT INTO `sys_role` (`id`, `tenant_id`, `name`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `data_scope_org`) VALUES ('1920672235608518656', '1483736224997847040', '测试管理员', 'test_admin', NULL, 100, 1, '1183307967865425920', '2025-05-09 10:48:40', '1183307967865425920', '2025-10-30 18:12:16', 0, '10');
INSERT INTO `sys_role` (`id`, `tenant_id`, `name`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `data_scope_org`) VALUES ('1951492696301023232', '1483736224997847040', '运维管理员', 'yw', NULL, 100, 1, '1183307967865425920', '2025-08-02 11:58:10', NULL, NULL, 0, '50');
INSERT INTO `sys_role` (`id`, `tenant_id`, `name`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `data_scope_org`) VALUES ('1986601605205856256', '1986600936507969536', '管理员', 'admin', NULL, 100, 1, '1183307967865425920', '2025-11-07 09:08:27', NULL, NULL, 0, '50');
INSERT INTO `sys_role` (`id`, `tenant_id`, `name`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`, `data_scope_org`) VALUES ('1993226834857955328', '1993128377904009216', 'A-admin', 'A-admin', NULL, 100, 1, '1183307967865425920', '2025-11-25 15:54:44', '1183307967865425920', '2025-11-27 09:20:03', 0, '10');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `role_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sys_role表id',
  `menu_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sys_menu表id',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  PRIMARY KEY (`id`),
  KEY `sys_role_menu_menu_id_index` (`menu_id`) USING BTREE,
  KEY `sys_role_menu_role_id_index` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色和前端资源关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572672', '1320983785481527296', '1294564582339993600', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572673', '1320983785481527296', '1294564837475311616', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572674', '1320983785481527296', '1320940438696058880', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572675', '1320983785481527296', '1294564916516970496', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572676', '1320983785481527296', '1320940611539132416', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572677', '1320983785481527296', '1297343214116495360', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572678', '1320983785481527296', '1320940764283101184', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572679', '1320983785481527296', '1675771738935439360', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572680', '1320983785481527296', '1675771907374493696', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572681', '1320983785481527296', '1675772041856462848', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1777650657388572682', '1320983785481527296', '1737490945684844544', '1183307967865425920', '2024-04-09 18:51:59', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014848', '1164384808021921792', '1167003012795863040', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014849', '1164384808021921792', '1484434828524720128', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014850', '1164384808021921792', '1484436755459280896', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014851', '1164384808021921792', '1484436806084530176', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014852', '1164384808021921792', '1484436885549813760', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014853', '1164384808021921792', '1167004033748176896', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014854', '1164384808021921792', '1320931477909495808', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014855', '1164384808021921792', '1320931564630925312', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014856', '1164384808021921792', '1320931614656389120', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014857', '1164384808021921792', '1320931794940157952', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014858', '1164384808021921792', '1320931901571948544', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014859', '1164384808021921792', '1346742291342516224', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014860', '1164384808021921792', '1399671523454836736', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014861', '1164384808021921792', '1432263397794734080', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014862', '1164384808021921792', '1777645054493372416', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014863', '1164384808021921792', '1167003860695388160', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014864', '1164384808021921792', '1320932706110758912', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014865', '1164384808021921792', '1320932756048142336', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014866', '1164384808021921792', '1320932808464359424', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014867', '1164384808021921792', '1320933159758290944', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014868', '1164384808021921792', '1716473768230039552', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014869', '1164384808021921792', '1700896300751695872', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014870', '1164384808021921792', '1707051267676090368', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014871', '1164384808021921792', '1707051308641857536', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014872', '1164384808021921792', '1707051378833534976', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014873', '1164384808021921792', '1167003541898924032', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014874', '1164384808021921792', '1320935611211538432', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014875', '1164384808021921792', '1320935660888875008', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014876', '1164384808021921792', '1320935692862054400', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014877', '1164384808021921792', '1320935970613059584', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014878', '1164384808021921792', '1167003348218548224', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014879', '1164384808021921792', '1320937209568518144', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014880', '1164384808021921792', '1320937263666651136', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014881', '1164384808021921792', '1320937303722254336', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014882', '1164384808021921792', '1184016665428496384', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014883', '1164384808021921792', '1186196450544062464', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014884', '1164384808021921792', '1186196577551781888', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014885', '1164384808021921792', '1186196632895623168', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014886', '1164384808021921792', '1829052113719177216', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014887', '1164384808021921792', '1829053079164071936', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014888', '1164384808021921792', '1829053126391934976', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014889', '1164384808021921792', '1829053166334291968', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014890', '1164384808021921792', '1831142939964911616', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014891', '1164384808021921792', '1831143089802227712', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014892', '1164384808021921792', '1831143138753949696', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014893', '1164384808021921792', '1831143199827210240', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014894', '1164384808021921792', '1294564582339993600', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014895', '1164384808021921792', '1294564837475311616', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014896', '1164384808021921792', '1320940438696058880', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014897', '1164384808021921792', '1801884751843078144', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014898', '1164384808021921792', '1294564916516970496', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014899', '1164384808021921792', '1320940611539132416', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014900', '1164384808021921792', '1801884563862761472', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014901', '1164384808021921792', '1297343214116495360', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1831256051992014902', '1164384808021921792', '1320940764283101184', '1183307967865425920', '2024-09-04 17:00:41', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411392', '1920672235608518656', '1167003012795863040', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411393', '1920672235608518656', '1484434828524720128', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411394', '1920672235608518656', '1484436755459280896', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411395', '1920672235608518656', '1484436806084530176', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411396', '1920672235608518656', '1484436885549813760', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411397', '1920672235608518656', '1167004033748176896', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411398', '1920672235608518656', '1320931477909495808', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411399', '1920672235608518656', '1320931564630925312', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411400', '1920672235608518656', '1320931614656389120', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411401', '1920672235608518656', '1320931794940157952', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411402', '1920672235608518656', '1320931901571948544', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411403', '1920672235608518656', '1346742291342516224', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411404', '1920672235608518656', '1399671523454836736', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411405', '1920672235608518656', '1432263397794734080', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411406', '1920672235608518656', '1777645054493372416', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411407', '1920672235608518656', '1167003860695388160', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411408', '1920672235608518656', '1320932706110758912', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411409', '1920672235608518656', '1320932756048142336', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411410', '1920672235608518656', '1320932808464359424', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411411', '1920672235608518656', '1320933159758290944', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411412', '1920672235608518656', '1716473768230039552', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411413', '1920672235608518656', '1700896300751695872', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411414', '1920672235608518656', '1707051267676090368', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411415', '1920672235608518656', '1707051308641857536', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411416', '1920672235608518656', '1707051378833534976', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411417', '1920672235608518656', '1167003541898924032', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411418', '1920672235608518656', '1320935611211538432', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411419', '1920672235608518656', '1320935660888875008', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411420', '1920672235608518656', '1320935692862054400', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411421', '1920672235608518656', '1320935970613059584', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411422', '1920672235608518656', '1167003348218548224', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411423', '1920672235608518656', '1320937209568518144', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411424', '1920672235608518656', '1320937263666651136', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411425', '1920672235608518656', '1320937303722254336', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411426', '1920672235608518656', '1184016665428496384', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411427', '1920672235608518656', '1186196450544062464', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411428', '1920672235608518656', '1186196577551781888', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411429', '1920672235608518656', '1186196632895623168', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411430', '1920672235608518656', '1829052113719177216', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411431', '1920672235608518656', '1829053079164071936', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411432', '1920672235608518656', '1829053126391934976', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411433', '1920672235608518656', '1829053166334291968', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411434', '1920672235608518656', '1831142939964911616', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411435', '1920672235608518656', '1831143089802227712', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411436', '1920672235608518656', '1831143138753949696', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839423236411437', '1920672235608518656', '1831143199827210240', '1183307967865425920', '2025-10-30 18:12:31', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1985171425899667498', '1164384808021921792', '1819199107717750784', '1876814660461879296', '2025-11-03 10:20:33', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868480', '1986601605205856256', '1167003012795863040', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868481', '1986601605205856256', '1484434828524720128', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868482', '1986601605205856256', '1484436755459280896', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868483', '1986601605205856256', '1484436806084530176', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868484', '1986601605205856256', '1484436885549813760', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868485', '1986601605205856256', '1167004033748176896', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868486', '1986601605205856256', '1320931477909495808', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868487', '1986601605205856256', '1320931564630925312', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868488', '1986601605205856256', '1320931614656389120', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868489', '1986601605205856256', '1320931794940157952', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868490', '1986601605205856256', '1320931901571948544', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868491', '1986601605205856256', '1346742291342516224', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868492', '1986601605205856256', '1399671523454836736', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868493', '1986601605205856256', '1432263397794734080', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868494', '1986601605205856256', '1777645054493372416', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868495', '1986601605205856256', '1167003860695388160', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868496', '1986601605205856256', '1320932706110758912', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868497', '1986601605205856256', '1320932756048142336', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868498', '1986601605205856256', '1320932808464359424', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868499', '1986601605205856256', '1320933159758290944', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868500', '1986601605205856256', '1716473768230039552', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868501', '1986601605205856256', '1700896300751695872', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868502', '1986601605205856256', '1707051267676090368', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868503', '1986601605205856256', '1707051308641857536', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868504', '1986601605205856256', '1707051378833534976', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868505', '1986601605205856256', '1167003541898924032', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868506', '1986601605205856256', '1320935611211538432', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868507', '1986601605205856256', '1320935660888875008', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868508', '1986601605205856256', '1320935692862054400', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868509', '1986601605205856256', '1320935970613059584', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868510', '1986601605205856256', '1167003348218548224', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868511', '1986601605205856256', '1320937209568518144', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868512', '1986601605205856256', '1320937263666651136', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868513', '1986601605205856256', '1320937303722254336', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868514', '1986601605205856256', '1184016665428496384', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868515', '1986601605205856256', '1186196450544062464', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868516', '1986601605205856256', '1186196577551781888', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868517', '1986601605205856256', '1186196632895623168', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868518', '1986601605205856256', '1829052113719177216', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868519', '1986601605205856256', '1829053079164071936', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868520', '1986601605205856256', '1829053126391934976', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868521', '1986601605205856256', '1829053166334291968', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868522', '1986601605205856256', '1831142939964911616', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868523', '1986601605205856256', '1831143089802227712', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868524', '1986601605205856256', '1831143138753949696', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601635534868525', '1986601605205856256', '1831143199827210240', '1183307967865425920', '2025-11-07 09:08:34', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186880', '1993226834857955328', '1294564582339993600', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186881', '1993226834857955328', '1294564837475311616', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186882', '1993226834857955328', '1320940438696058880', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186883', '1993226834857955328', '1801884751843078144', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186884', '1993226834857955328', '1294564916516970496', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186885', '1993226834857955328', '1320940611539132416', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186886', '1993226834857955328', '1801884563862761472', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186887', '1993226834857955328', '1297343214116495360', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186888', '1993226834857955328', '1320940764283101184', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993232731160186889', '1993226834857955328', '1801560933857144832', '1183307967865425920', '2025-11-25 16:18:10', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_org`;
CREATE TABLE `sys_role_org` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `role_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sys_role表id',
  `org_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sys_log表id',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  PRIMARY KEY (`id`),
  KEY `sys_role_org_org_id_index` (`org_id`) USING BTREE,
  KEY `sys_role_org_role_id_index` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色组织机构关系表(用于记录角色自定义组织机构数据权限)';

-- ----------------------------
-- Records of sys_role_org
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名称',
  `code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编码',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `orders` int(11) NOT NULL DEFAULT '100' COMMENT '排序（倒序）',
  `enable` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否启用(默认1,禁用0)',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant` (`id`, `name`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1483736224997847040', '平台管理组', 'tenant-admin', '平台管理/运营方/超超级管理员', 100, 1, '1', '2022-01-19 17:48:35', '1183307967865425920', '2022-01-24 22:45:42', 0);
INSERT INTO `sys_tenant` (`id`, `name`, `code`, `remark`, `orders`, `enable`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1993128377904009216', 'A', 'A', '', 100, 1, '1183307967865425920', '2025-11-25 09:23:30', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `tenant_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户id',
  `password` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `salt` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码加盐',
  `username` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `realname` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '真实姓名',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `enable` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否启用(默认1,禁用0)',
  `account4a` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '4a帐号',
  `openid` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信小程序openId',
  `org_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '归属组织/部门',
  `otp_secret` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'otp二次验证秘钥',
  `expired_time` datetime DEFAULT NULL COMMENT '账号过期时间',
  `update_password` smallint(6) DEFAULT '0' COMMENT '密码更新次数',
  `last_password_change_time` datetime DEFAULT NULL COMMENT '最近一次密码修改时间',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  `del_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标志(默认0,删除1)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_username_uindex` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `tenant_id`, `password`, `salt`, `username`, `realname`, `avatar`, `enable`, `account4a`, `openid`, `org_id`, `otp_secret`, `expired_time`, `update_password`, `last_password_change_time`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1183307967865425920', '1483736224997847040', 'a900f1164c23f88298f3bc2517af4955ea1a2ff7f9fb17d0ac8e0ed01eb10cf5', '73468e0545e44f9ebb6ca56ead78d73d', '18612341234', '张无忌', NULL, 1, 'xiejun@gzps.corp.csg', NULL, '1699335117679538176', 'DVMJDGSA3ODOS6FNSC7QHNFYWAQEI6H2', NULL, 0, '2025-11-12 17:29:46', '1183307967865425920', '2019-10-13 17:06:19', '1183307967865425920', NULL, 0);
INSERT INTO `sys_user` (`id`, `tenant_id`, `password`, `salt`, `username`, `realname`, `avatar`, `enable`, `account4a`, `openid`, `org_id`, `otp_secret`, `expired_time`, `update_password`, `last_password_change_time`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1777651004320428032', '1483736224997847040', '84a7ec2e27ebe2967e0b1707714da60a82d5abac5b47939a3c1ea9fb8ae74f9d', '5991b32ac8784d3cb1e673ffb7969352', '18611112222', '张三', NULL, 1, NULL, NULL, '1699335158448173056', NULL, '2026-02-14 11:07:14', 1, '2025-07-05 17:33:21', '1183307967865425920', '2024-04-09 18:53:21', '1983839748202696704', '2025-10-30 18:14:27', 0);
INSERT INTO `sys_user` (`id`, `tenant_id`, `password`, `salt`, `username`, `realname`, `avatar`, `enable`, `account4a`, `openid`, `org_id`, `otp_secret`, `expired_time`, `update_password`, `last_password_change_time`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1983839748202696704', '1483736224997847040', 'd47a52e178dfc7ff7afa3746b94e0e238e6a58396b883b17b7c4341b317cb156', '9c6cf617bf5948fdad099dd6093e2693', '18637231234', '闫大三', NULL, 1, NULL, NULL, '1699335117679538176', NULL, NULL, 0, '2025-10-30 18:13:49', '1183307967865425920', '2025-10-30 18:13:49', '1183307967865425920', '2025-12-02 09:16:17', 0);
INSERT INTO `sys_user` (`id`, `tenant_id`, `password`, `salt`, `username`, `realname`, `avatar`, `enable`, `account4a`, `openid`, `org_id`, `otp_secret`, `expired_time`, `update_password`, `last_password_change_time`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1986601367309127680', '1986600936507969536', 'ebb783df582ce56ab75691a62cd456c123b037d1be6e6cfb565a9893696a45cf', 'a866e4e8467b4aa9ab3a89ee6049d1d5', '18698769876', '发了', NULL, 1, NULL, NULL, NULL, NULL, NULL, 0, '2025-11-07 09:07:30', '1183307967865425920', '2025-11-07 09:07:30', NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `tenant_id`, `password`, `salt`, `username`, `realname`, `avatar`, `enable`, `account4a`, `openid`, `org_id`, `otp_secret`, `expired_time`, `update_password`, `last_password_change_time`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `del_flag`) VALUES ('1993128507910656000', '1993128377904009216', 'de49691f8a3e5c1550c3cc14a93c17af5d272421ba972b4737308188f368112e', '9eae95cc91ba4bb395ece12da096f57f', '18677778888', '隔壁老王', NULL, 1, NULL, NULL, NULL, NULL, NULL, 0, NULL, '1183307967865425920', '2025-11-25 09:24:01', '1183307967865425920', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_pass_history
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_pass_history`;
CREATE TABLE `sys_user_pass_history` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  `password` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `salt` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码加盐',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户登录账号密码历史记录表';

-- ----------------------------
-- Records of sys_user_pass_history
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_pass_history` (`id`, `user_id`, `password`, `salt`, `create_user_id`, `create_time`) VALUES ('1802909654691000320', '1777651004320428032', '50910671bc5af98be73f9016b4de3e3945bfb1422efd5df3fd353ef0d1a98c9f', '5ee2dec320d148388a747f24d9d8446c', '1183307967865425920', '2024-06-18 11:42:13');
INSERT INTO `sys_user_pass_history` (`id`, `user_id`, `password`, `salt`, `create_user_id`, `create_time`) VALUES ('1802909683560394752', '1777651004320428032', '75c2b8dcdbd7f469851d8f1a30b9715b29ce03def9aad1787d5b5e6c32dccf98', '48e799afb68143ab9d834eb56459ddba', '1183307967865425920', '2024-06-18 11:42:20');
INSERT INTO `sys_user_pass_history` (`id`, `user_id`, `password`, `salt`, `create_user_id`, `create_time`) VALUES ('1802909716775088128', '1777651004320428032', 'ab0af2e5e84610286d34331d59e5e2146628f8053b547607f140c198e2b9c34d', 'c1dea78a639244d3a0a72b84e3093e4b', '1183307967865425920', '2024-06-18 11:42:28');
INSERT INTO `sys_user_pass_history` (`id`, `user_id`, `password`, `salt`, `create_user_id`, `create_time`) VALUES ('1941429493333405696', '1777651004320428032', '84a7ec2e27ebe2967e0b1707714da60a82d5abac5b47939a3c1ea9fb8ae74f9d', '5991b32ac8784d3cb1e673ffb7969352', '1183307967865425920', '2025-07-05 17:30:36');
INSERT INTO `sys_user_pass_history` (`id`, `user_id`, `password`, `salt`, `create_user_id`, `create_time`) VALUES ('1993135024702099456', '1993128507910656000', 'de49691f8a3e5c1550c3cc14a93c17af5d272421ba972b4737308188f368112e', '9eae95cc91ba4bb395ece12da096f57f', '1183307967865425920', '2025-11-25 09:49:55');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sys_user表id',
  `role_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sys_role表id',
  `create_user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动更新）',
  `update_user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（自动更新）',
  PRIMARY KEY (`id`),
  KEY `sys_user_role_role_id_index` (`role_id`) USING BTREE,
  KEY `sys_user_role_user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户和角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1801886318021357568', '1777651004320428032', '1320983785481527296', '1183307967865425920', '2024-06-15 15:55:50', NULL, NULL);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1822293349323157504', '1183307967865425920', '1164384808021921792', '1183307967865425920', '2024-08-10 23:26:06', NULL, NULL);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1822293349323157505', '1183307967865425920', '1320983785481527296', '1183307967865425920', '2024-08-10 23:26:06', NULL, NULL);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1983839774870081536', '1983839748202696704', '1920672235608518656', '1183307967865425920', '2025-10-30 18:13:55', NULL, NULL);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1986601827902427136', '1986601367309127680', '1986601605205856256', '1183307967865425920', '2025-11-07 09:09:20', NULL, NULL);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`) VALUES ('1993852372588957696', '1993128507910656000', '1993226834857955328', '1183307967865425920', '2025-11-27 09:20:24', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
