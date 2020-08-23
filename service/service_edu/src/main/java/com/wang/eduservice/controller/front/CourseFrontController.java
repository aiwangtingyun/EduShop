package com.wang.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.chapter.ChapterVo;
import com.wang.eduservice.entity.vo.CourseFrontVo;
import com.wang.eduservice.entity.vo.CourseWebVo;
import com.wang.eduservice.service.EduChapterService;
import com.wang.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"前端课程列表查询"})
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    // 带条件分页查询课程列表
    @ApiOperation("带条件分页查询课程列表")
    @PostMapping("/getCourseList/{page}/{limit}")
    public RetMsg getCourseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseFrontVo", value = "查询对象", required = false)
            @RequestBody(required = false)CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object>map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        return RetMsg.ok().data(map);
    }

    // 查询课程详情
    @ApiOperation("根据课程id查询课程详情信息")
    @GetMapping("/getWebCourseInfo/{courseId}")
    public RetMsg getWebCourseInfo(
            @ApiParam(name = "courseId", value = "课程id")
            @PathVariable String courseId) {
        // 查询课程信息
        CourseWebVo courseWebVo = courseService.getWebCourseInfo(courseId);

        // 查询课程章节和小节列表
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return RetMsg.ok().data("courseWebInfo", courseWebVo).data("chapterVideoList",chapterVideoList);
    }

}
