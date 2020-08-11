package com.wang.eduservice.controller;


import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.chapter.ChapterVo;
import com.wang.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
@Api(tags = {"课程章节管理"})
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    // 根据课程id获取课程大纲列表
    @ApiOperation(value = "获取课程大纲列表")
    @GetMapping(value = "/getChapterVideo/{courseId}")
    public RetMsg getChapterVideo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        return RetMsg.ok().data("chapterVideoList", chapterVoList);
    }

}

