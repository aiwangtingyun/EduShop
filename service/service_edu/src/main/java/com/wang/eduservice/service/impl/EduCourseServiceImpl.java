package com.wang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.eduservice.entity.EduChapter;
import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.EduCourseDescription;
import com.wang.eduservice.entity.vo.CourseInfoVo;
import com.wang.eduservice.entity.vo.CoursePublishVo;
import com.wang.eduservice.entity.vo.CourseQuery;
import com.wang.eduservice.mapper.EduCourseMapper;
import com.wang.eduservice.service.EduChapterService;
import com.wang.eduservice.service.EduCourseDescriptionService;
import com.wang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.eduservice.service.EduVideoService;
import com.wang.servicebase.exceptionhandler.EduShopException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    // 课程章节 service
    @Autowired
    private EduChapterService chapterService;

    // 课程小节 service
    @Autowired
    private EduVideoService videoService;

    // 课程描述 service
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

    // 修改课程基本信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程信息表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = this.baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new EduShopException(20001, "修改课程信息失败");
        }

        // 修改继承简介表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    // 根据课程id查询课程最终发布确认信息
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        //调用mapper
        CoursePublishVo publishCourseInfo = this.baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    // 删除课程
    @Override
    public boolean removeCourse(String courseId) {
        // 1、删除课程ID对应的所有小节
        videoService.removeVideoByCourseId(courseId);

        // 2、删除课程ID对应的所有章节
        chapterService.removeChapterByCourseId(courseId);

        // 3、删除课程ID对应的课程简介
        courseDescriptionService.removeById(courseId);

        // 4、删除课程本身
        int result = this.baseMapper.deleteById(courseId);
        if (result == 0) {
            return false;
        }

        return true;
    }

    // 带条件的课程分页查询
    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create"); // 按创建时间降序排序

        if (courseQuery == null) {
            this.baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        // 填充查询条件
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teaher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }

        this.baseMapper.selectPage(pageParam, queryWrapper);
    }

}
