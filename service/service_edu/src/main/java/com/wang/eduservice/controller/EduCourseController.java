package com.wang.eduservice.controller;


import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.vo.CourseInfoVo;
import com.wang.eduservice.entity.vo.CoursePublishVo;
import com.wang.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        // CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(courseId);
        return RetMsg.ok();
    }
}

