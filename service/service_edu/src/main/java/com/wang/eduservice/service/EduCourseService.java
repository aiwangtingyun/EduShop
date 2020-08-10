package com.wang.eduservice.service;

import com.wang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
