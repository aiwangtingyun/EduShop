# Host: localhost  (Version 5.7.30)
# Date: 2020-08-10 11:49:32
# Generator: SQLyog Ultimate v12.09 (64 bit)


#
# Structure for table "statistics_daily"
#

CREATE TABLE `statistics_daily` (
  `id` char(19) NOT NULL COMMENT '主键',
  `date_calculated` varchar(20) NOT NULL COMMENT '统计日期',
  `register_num` int(11) NOT NULL DEFAULT '0' COMMENT '注册人数',
  `login_num` int(11) NOT NULL DEFAULT '0' COMMENT '登录人数',
  `video_view_num` int(11) NOT NULL DEFAULT '0' COMMENT '每日播放视频数',
  `course_num` int(11) NOT NULL DEFAULT '0' COMMENT '每日新增课程数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `statistics_day` (`date_calculated`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站统计日数据';

#
# Data for table "statistics_daily"
#

insert into `statistics_daily` 
  (`id`, `date_calculated`, `register_num`, `login_num`, `video_view_num`, `course_num`, `gmt_create`, `gmt_modified`) 
values
  ('1299180296360067073','2020-08-01','112','196','174','181','2020-08-28 11:01:35','2020-08-28 11:01:35'),
  ('1299180432733667329','2020-08-02','66','133','101','172','2020-08-28 11:02:07','2020-08-28 11:02:07'),
  ('1299180456305655809','2020-08-03','159','191','186','117','2020-08-28 11:02:13','2020-08-28 11:02:13'),
  ('1299180533183053825','2020-08-04','185','189','104','167','2020-08-28 11:02:31','2020-08-28 11:02:31'),
  ('1299180567572152321','2020-08-05','116','184','149','122','2020-08-28 11:02:40','2020-08-28 11:02:40'),
  ('1299180599952179202','2020-08-06','102','116','151','115','2020-08-28 11:02:47','2020-08-28 11:02:47'),
  ('1299180861823549442','2020-08-07','134','134','191','149','2020-08-28 11:03:50','2020-08-28 11:03:50'),
  ('1299180885844328449','2020-08-08','158','158','195','151','2020-08-28 11:03:55','2020-08-28 11:03:55'),
  ('1299180915460308993','2020-08-09','167','163','157','117','2020-08-28 11:04:03','2020-08-28 11:04:03'),
  ('1299180931612573697','2020-08-10','117','167','186','147','2020-08-28 11:04:06','2020-08-28 11:04:06');