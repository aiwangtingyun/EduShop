package com.wang.allservice.mapper.edu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.allservice.entity.edu.EduCourse;
import com.wang.allservice.entity.edu.vo.CoursePublishVo;
import com.wang.allservice.entity.edu.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    // 获取课程发布信息
    CoursePublishVo getPublishCourseInfo(String courseId);

    // 获取课程详情信息
    CourseWebVo getWebCourseInfo(String courseId);
}
