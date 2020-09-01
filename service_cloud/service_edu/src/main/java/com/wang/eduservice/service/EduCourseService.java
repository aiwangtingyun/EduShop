package com.wang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.entity.vo.*;

import java.util.Map;

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

    // 根据课程id删除课程
    boolean removeCourseById(String courseId);

    // 带条件的课程分页查询
    void pageQuery(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo);

    // 前端带条件分页查询课程列表
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo);

    // 获取课程详解信息
    CourseWebVo getWebCourseInfo(String courseId);
}
