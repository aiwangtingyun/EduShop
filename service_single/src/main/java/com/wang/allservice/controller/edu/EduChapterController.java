package com.wang.allservice.controller.edu;

import com.wang.allservice.entity.edu.EduChapter;
import com.wang.allservice.entity.edu.chapter.ChapterVo;
import com.wang.allservice.service.edu.EduChapterService;
import com.wang.allservice.utils.RetMsg;
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

    // 添加章节
    @ApiOperation(value = "添加章节")
    @PostMapping(value = "/addChapter")
    public RetMsg addChapter(
            @ApiParam(name = "chapter", value = "章节信息", required = true)
            @RequestBody EduChapter chapter) {
        boolean flag = chapterService.save(chapter);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

    // 根据章节id查询章节信息
    @ApiOperation(value = "根据章节id查询章节信息")
    @GetMapping(value = "/getChapterInfo/{chapterId}")
    public RetMsg getChapterInfo(
            @ApiParam(name = "chapterId", value = "章节ID", required = true)
            @PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return RetMsg.ok().data("chapter", eduChapter);
    }

    // 修改章节信息
    @ApiOperation(value = "修改章节信息")
    @PostMapping(value = "/updateChapterInfo")
    public RetMsg updateChapterInfo(
            @ApiParam(name = "chapter", value = "章节信息", required = true)
            @RequestBody EduChapter chapter) {
        boolean flag = chapterService.updateById(chapter);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

    // 根据id删除章节
    @ApiOperation(value = "根据id删除章节")
    @DeleteMapping(value = "/deleteById/{chapterId}")
    public RetMsg deleteById(
            @ApiParam(name = "chapterId", value = "章节ID", required = true)
            @PathVariable String chapterId) {
        boolean flag = chapterService.removeChapterById(chapterId);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

}

