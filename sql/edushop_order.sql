# Host: localhost  (Version 5.7.19)
# Date: 2019-11-18 15:49:50
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "t_order"
#

CREATE TABLE `t_order` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '订单ID', 
  `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
  `course_title` varchar(100) DEFAULT NULL COMMENT '课程名称',
  `course_cover` varchar(255) DEFAULT NULL COMMENT '课程封面',
  `teacher_name` varchar(20) DEFAULT NULL COMMENT '讲师名称',
  `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '会员手机',
  `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '订单金额（分）',
  `pay_type` tinyint(3) DEFAULT NULL COMMENT '支付类型（1：微信 2：支付宝）',
  `status` tinyint(3) DEFAULT NULL COMMENT '订单状态（0：未支付 1：已支付）',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_order_no` (`order_no`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单';

#
# Data for table "t_order"
#

insert into `t_order` 
  (`id`, `order_no`, `course_id`, `course_title`, `course_cover`, `teacher_name`, `member_id`, `nickname`, `mobile`, `total_fee`, `pay_type`, `status`, `is_deleted`, `gmt_create`, `gmt_modified`) 
values
  ('1298647004343226369','20200826234228039','1297169099612262402','零基础入门Python学习','https://edushop-files.oss-cn-shenzhen.aliyuncs.com/2020/08/22/24d1af8796a044e9bb0fd67066db42ecpython.jpg','刘晓华','1295393366606901249','王廷云','13418186670','0.01','1','0','0','2020-08-26 23:42:28','2020-08-26 23:42:28'),
  ('1298648518268866562','20200826234829718','1297169099612262402','零基础入门Python学习','https://edushop-files.oss-cn-shenzhen.aliyuncs.com/2020/08/22/24d1af8796a044e9bb0fd67066db42ecpython.jpg','刘晓华','1295393366606901249','王廷云','13418186670','0.01','1','1','0','2020-08-26 23:48:29','2020-08-26 23:48:51');

#
# Structure for table "t_pay_log"
#

CREATE TABLE `t_pay_log` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '支付记录ID',
  `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '支付金额（分）',
  `transaction_id` varchar(30) DEFAULT NULL COMMENT '交易流水号',
  `trade_state` char(20) DEFAULT NULL COMMENT '交易状态',
  `pay_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付类型（1：微信 2：支付宝）',
  `attr` text COMMENT '其他属性',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付日志表';

#
# Data for table "t_pay_log"
#

insert into `t_pay_log` 
  (`id`, `order_no`, `pay_time`, `total_fee`, `transaction_id`, `trade_state`, `pay_type`, `attr`, `is_deleted`, `gmt_create`, `gmt_modified`) 
values
  ('1298645426710302721','20200826233213243','2020-08-26 23:36:12','0.01','4200000705202008269548714031','SUCCESS','1','{\"transaction_id\":\"4200000705202008269548714031\",\"nonce_str\":\"HQOVuV7CusnYTaDf\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuEueLaoxmdHLKSY9RI3sBaI\",\"sign\":\"7903B6B66F824E76C814D2BB00AC1F57\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"1\",\"out_trade_no\":\"20200826233213243\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20200826233304\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}','0','2020-08-26 23:36:12','2020-08-26 23:36:12'),
  ('1298645708068409346','20200826233700740','2020-08-26 23:37:19','0.01','4200000715202008268821030155','SUCCESS','1','{\"transaction_id\":\"4200000715202008268821030155\",\"nonce_str\":\"MIsRoqc7uskj9gkZ\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuEueLaoxmdHLKSY9RI3sBaI\",\"sign\":\"01C06E1F34A9BA35D682CE404068595F\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"1\",\"out_trade_no\":\"20200826233700740\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20200826233717\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}','0','2020-08-26 23:37:19','2020-08-26 23:37:19');
