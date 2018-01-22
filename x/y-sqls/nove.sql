SET FOREIGN_KEY_CHECKS=0;  
  

DROP TABLE IF EXISTS `novel_classes`;
CREATE TABLE `novel_classes` (  
  `id` bigint(20) NOT NULL AUTO_INCREMENT,  
  `title` varchar(100) NOT NULL, 
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `novel_classes` (`id`, `title`) VALUES
	(1, '玄幻'),
	(2, '奇幻'),
	(3, '武侠'),
	(4, '仙侠'),
	(5, '都市'),
	(6, '现实'),
	(7, '军事'),
	(8, '历史'),
	(9, '游戏'),
	(10, '体育'),
	(11, '科幻'),
	(12, '灵异');


-- ----------------------------  
-- Table structure for `crawllist` 小说采集入口  
-- ----------------------------  
DROP TABLE IF EXISTS `crawllist`;  
CREATE TABLE `crawllist` (  
  `id` bigint(20) NOT NULL AUTO_INCREMENT,  
  `url` varchar(100) NOT NULL,##采集url  
  `state` enum('1','0') NOT NULL,##采集状态  
  `info` varchar(100) DEFAULT NULL,##描述  
  `frequency` int(11) DEFAULT '60',##采集频率  
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  
  
-- ----------------------------  
-- Table structure for `novelchapter` 小说章节信息  
-- ----------------------------  
DROP TABLE IF EXISTS `novelchapter`;  
CREATE TABLE `novelchapter` (  
  `id` varchar(32) NOT NULL,  
  `url` varchar(100) NOT NULL,##阅读页URL  
  `title` varchar(50) DEFAULT NULL,##章节名  
  `wordcount` int(11) DEFAULT NULL,##字数  
  `chapterid` int(11) DEFAULT NULL,##章节排序  
  `chaptertime` bigint(20) DEFAULT NULL,##章节时间  
  `createtime` bigint(20) DEFAULT NULL,##创建时间  
  `state` enum('1','0') NOT NULL,##采集状态  
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  
  
-- ----------------------------  
-- Table structure for `novelchapterdetail` 小说章节详细信息  
-- ----------------------------  
DROP TABLE IF EXISTS `novelchapterdetail`;  
CREATE TABLE `novelchapterdetail` (  
  `id` varchar(32) NOT NULL,  
  `url` varchar(100) NOT NULL,##阅读页url  
  `title` varchar(50) DEFAULT NULL,##章节标题  
  `wordcount` int(11) DEFAULT NULL,##字数  
  `chapterid` int(11) DEFAULT NULL,##章节排序  
  `content` text,##正文  
  `chaptertime` bigint(20) DEFAULT NULL,##章节时间  
  `createtime` bigint(20) DEFAULT NULL,##创建时间  
  `updatetime` bigint(20) DEFAULT NULL,##最后更新时间  
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  
  
-- ----------------------------  
-- Table structure for `novelinfo` 小说简介信息  
-- ----------------------------  
DROP TABLE IF EXISTS `novelinfo`;  
CREATE TABLE `novelinfo` (  
  `id` varchar(32) NOT NULL,  
  `url` varchar(100) NOT NULL,##简介页url  
  `name` varchar(50) DEFAULT NULL,##小说名  
  `author` varchar(30) DEFAULT NULL,##作者名  
  `description` text,##小说简介  
  `type` varchar(20) DEFAULT NULL,##分类  
  `lastchapter` varchar(100) DEFAULT NULL,##最新章节名  
  `chaptercount` int(11) DEFAULT NULL,##章节数  
  `chapterlisturl` varchar(100) DEFAULT NULL,##章节列表页url  
  `wordcount` int(11) DEFAULT NULL,##字数  
  `keywords` varchar(100) DEFAULT NULL,##关键字  
  `createtime` bigint(20) DEFAULT NULL,##创建时间  
  `updatetime` bigint(20) DEFAULT NULL,##最后更新时间  
  `state` enum('1','0') NOT NULL,##采集状态  
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  