package com.wang.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.JwtUtils;
import com.wang.commonutils.RetMsg;
import com.wang.commonutils.ordervo.CourseWebOrderVo;
import com.wang.eduservice.client.OrderClient;
import com.wang.eduservice.entity.EduCourse;
import com.wang.eduservice.entity.chapter.ChapterVo;
import com.wang.eduservice.entity.vo.CourseFrontVo;
import com.wang.eduservice.entity.vo.CourseWebVo;
import com.wang.eduservice.service.EduChapterService;
import com.wang.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private OrderClient orderClient;

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
            @PathVariable String courseId,
            HttpServletRequest request) {
        // 根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getWebCourseInfo(courseId);

        // 根据课程id查询课程章节和小节列表
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        // 根据课程id和用户id查询当前课程是否已经支付过了
        boolean payState = orderClient.getPayState(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return RetMsg.ok().data("courseWebInfo", courseWebVo).data("chapterVideoList",chapterVideoList)
                .data("isBuy", payState);
    }

    // 根据课程id查询表订单课程信息
    @ApiOperation("根据课程id查询表订单课程信息")
    @GetMapping("/getCourseInfoOrder/{courseId}")
    public CourseWebOrderVo getCourseInfoOrder(
            @ApiParam(name = "courseId", value = "课程id")
            @PathVariable("courseId") String courseId){
        // 查询课程信息
        CourseWebVo courseInfo = courseService.getWebCourseInfo(courseId);
        // 封装返回的订单课程信息
        CourseWebOrderVo courseWebOrderVo = new CourseWebOrderVo();
        BeanUtils.copyProperties(courseInfo, courseWebOrderVo);
        return courseWebOrderVo;
    }

}
