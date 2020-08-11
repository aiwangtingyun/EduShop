package com.wang.eduservice.service.impl;

import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.EduCourseDescription;
import com.wang.eduservice.entity.vo.CourseInfoVo;
import com.wang.eduservice.mapper.EduCourseMapper;
import com.wang.eduservice.service.EduCourseDescriptionService;
import com.wang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.servicebase.exceptionhandler.EduShopException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    // 课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    // 添加课程基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 想课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = this.baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new EduShopException(20001, "添加课程信息失败");
        }

        // 获取添加之后的课程ID
        String courseId = eduCourse.getId();

        // 想课程简介表添加课程简介：简介表ID需要对应课程ID
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        description.setId(courseId);
        courseDescriptionService.save(description);

        return courseId;
    }

    // 根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 查询课程表
        EduCourse eduCourse = this.baseMapper.selectById(courseId);

        // 查询课程简介表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);

        // 封装课程表和课程简介表信息
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }
}
