package com.wang.eduservice.controller;


import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.vo.CourseInfoVo;
import com.wang.eduservice.entity.vo.CoursePublishVo;
import com.wang.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
@Api(tags = {"课程管理"})
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    // 获取课程列表
    @ApiOperation(value = "获取课程列表")
    @GetMapping(value = "/list")
    public RetMsg getCourseList() {
        List<EduCourse> courseList = courseService.list(null);
        return RetMsg.ok().data("list", courseList);
    }

    // 添加课程基本信息
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping(value = "addCourseInfo")
    public RetMsg addCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo) {

        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);

        return RetMsg.ok().data("courseId", id);
    }

    // 根据课程id查询课程基本信息
    @ApiOperation(value = "查询课程基本信息")
    @GetMapping(value = "/getCourseInfo/{courseId}")
    public RetMsg getCourseInfo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);

        return RetMsg.ok().data("courseInfoVo", courseInfoVo);
    }

    // 修改课程基本信息
    @ApiOperation(value = "更新课程基本信息")
    @PostMapping(value = "/updateCourseInfo")
    public RetMsg updateCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo) {

        courseService.updateCourseInfo(courseInfoVo);

        return RetMsg.ok();
    }

    // 根据课程id查询课程最终发布确认信息
    @ApiOperation(value = "更新课程基本信息")
    @GetMapping(value = "/getPublishCourseInfo/{courseId}")
    public RetMsg getPublishCourseInfo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(courseId);
        return RetMsg.ok().data("publishCourseInfo", coursePublishVo);
    }

    // 课程最终发布
    @ApiOperation(value = "课程最终发布")
    @PostMapping(value = "/publishCourse/{courseId}")
    public RetMsg publishCourse(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");  // 设置课程为发布状态
        boolean flag = courseService.updateById(eduCourse);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

    // 删除课程
    @ApiOperation(value = "删除课程")
    @DeleteMapping(value = "/deleteCourse/{courseId}")
    public RetMsg deleteCourse(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        boolean flag = courseService.removeCourse(courseId);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }
}

