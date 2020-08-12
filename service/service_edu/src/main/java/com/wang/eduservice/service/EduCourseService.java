package com.wang.eduservice.service;

import com.wang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.entity.vo.CourseInfoVo;
import com.wang.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
public interface EduCourseService extends IService<EduCourse> {

    // 添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id查询课程最终发布确认信息
    CoursePublishVo getPublishCourseInfo(String courseId);

    // 删除课程
    boolean removeCourse(String courseId);
}
