package com.wang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.EduTeacher;
import com.wang.eduservice.service.EduCourseService;
import com.wang.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"前端首页接口"})
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    // 查询前8条热门课程，查询前4条名师
    @ApiOperation(value = "查询首页课程和名师数据")
    @GetMapping(value = "/index")
    public RetMsg index() {
        // 查询前8条热门课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseWrapper);

        // 查询前4条名师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);

        return RetMsg.ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}
