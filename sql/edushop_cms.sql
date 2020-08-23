# Host: localhost  (Version 5.7.30)
# Date: 2020-08-10 11:49:32
# Generator: SQLyog Ultimate v12.09 (64 bit)


#
# Structure for table "crm_banner"
#

CREATE TABLE `crm_banner` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(20) DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片地址',
  `link_url` varchar(500) DEFAULT '' COMMENT '链接地址',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='首页banner表';

#
# Data for table "crm_banner"
#

insert into `crm_banner` 
  (`id`, `title`, `image_url`, `link_url`, `sort`, `is_deleted`, `gmt_create`, `gmt_modified`) 
values
  ('1297367367277391873','banner01','https://edushop-files.oss-cn-shenzhen.aliyuncs.com/2020/08/23/banner02.jpg','/course','1','0','2020-08-23 10:57:39','2020-08-23 10:57:39'),
  ('1297367601294389249','banner02','https://edushop-files.oss-cn-shenzhen.aliyuncs.com/2020/08/23/banner03.jpg','/course','2','0','2020-08-23 10:58:35','2020-08-23 10:58:35'),
  ('1297389503815639042','banner03','https://edushop-files.oss-cn-shenzhen.aliyuncs.com/2020/08/23/banner04.jpg','/course','3','0','2020-08-23 12:25:37','2020-08-23 12:25:37');
