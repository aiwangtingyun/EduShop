package com.wang.allservice.service.edu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.allservice.entity.edu.EduCourse;
import com.wang.allservice.entity.edu.EduCourseDescription;
import com.wang.allservice.entity.edu.vo.*;
import com.wang.allservice.handler.EduShopException;
import com.wang.allservice.mapper.edu.EduCourseMapper;
import com.wang.allservice.service.edu.EduChapterService;
import com.wang.allservice.service.edu.EduCourseDescriptionService;
import com.wang.allservice.service.edu.EduCourseService;
import com.wang.allservice.service.edu.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 课程章节 allservice
    @Autowired
    private EduChapterService chapterService;

    // 课程小节 allservice
    @Autowired
    private EduVideoService videoService;

    // 课程描述 allservice
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

    // 根据课程id删除课程
    @Override
    public boolean removeCourseById(String courseId) {
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
    public void pageQuery(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create"); // 按创建时间降序排序

        if (courseQueryVo == null) {
            this.baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();

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

    // 前端带条件分页查询课程列表
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        // 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        // 根据条件进行查询
        baseMapper.selectPage(pageParam, wrapper);

        // 获取查询结果数据
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        // 封装分页数据
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        // 返回查询结果
        return map;
    }

    // 获取课程详解信息
    @Override
    public CourseWebVo getWebCourseInfo(String courseId) {
        return baseMapper.getWebCourseInfo(courseId);
    }

}
