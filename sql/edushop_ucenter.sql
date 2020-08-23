# Host: localhost  (Version 5.7.30)
# Date: 2020-08-10 11:49:32
# Generator: SQLyog Ultimate v12.09 (64 bit)


#
# Structure for table "ucenter_member"
#

CREATE TABLE `ucenter_member` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(2) unsigned DEFAULT NULL COMMENT '性别 1 女，2 男',
  `age` tinyint(3) unsigned DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

#
# Data for table "ucenter_member"
#

insert into `ucenter_member` 
  (`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) 
values
  ('1295393366606901249',NULL,'13418186670','46ea199638da1b45d0a4dc4136f29c80','王廷云',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,'0','0','2020-08-18 00:13:41','2020-08-18 00:13:41'),
  ('1295757997565345794','o3_SC58-T2LDCFlqmKdhklL8cBlY','',NULL,'廷云Create',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/1xyYSnvOicpFWbRicH4yrNEDoibEBCMf98klUuLAkw3NbAYLhh6WJ5oHBBb6ZAx6ch6icicTNd8g0dpYNxBZFBPqQVw/132',NULL,'0','0','2020-08-19 00:22:35','2020-08-19 00:22:35');
