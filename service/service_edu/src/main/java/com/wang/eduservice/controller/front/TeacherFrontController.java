package com.wang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.EduTeacher;
import com.wang.eduservice.service.EduCourseService;
import com.wang.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"前端讲师查询"})
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    // 分页查询讲师列表
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("/pageList/{page}/{limit}")
    public RetMsg getPageList(
            @ApiParam(name = "page", value = "当前页面", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<EduTeacher> PageParam = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(PageParam);

        return RetMsg.ok().data(map);
    }

    // 查询讲师详情
    @ApiOperation(value = "查询讲师详情")
    @GetMapping("/getTeacherInfo/{teacherId}")
    public RetMsg getTeacherInfo(
            @ApiParam(name = "teacherId", value = "讲师id", required = true)
            @PathVariable String teacherId) {
        // 查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        // 查询讲师对应的课程列表
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        return RetMsg.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }

}
