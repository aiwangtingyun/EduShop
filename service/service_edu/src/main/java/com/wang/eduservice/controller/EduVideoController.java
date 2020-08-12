package com.wang.eduservice.controller;


import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.EduVideo;
import com.wang.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
@Api(tags = {"课程小节管理"})
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    // 添加课程小节
    @ApiOperation(value = "添加课程小节")
    @PostMapping(value = "/addVideo")
    public RetMsg addVideo(
            @ApiParam(name = "eduVideo", value = "课程小节信息", required = true)
            @RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.save(eduVideo);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

    // 根据id获取课程小节信息
    @ApiOperation(value = "根据id获取课程小节信息")
    @GetMapping(value = "/getVideoInfo/{id}")
    public RetMsg editVideo(
            @ApiParam(name = "id", value = "课程小节ID", required = true)
            @PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        return RetMsg.ok().data("video", eduVideo);
    }

    // 修改课程小节
    @ApiOperation(value = "修改课程小节")
    @PostMapping(value = "/updateVideo")
    public RetMsg updateVideo(
            @ApiParam(name = "eduVideo", value = "课程小节信息", required = true)
            @RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.updateById(eduVideo);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

    // 删除课程小节
    @ApiOperation(value = "删除课程小节")
    @DeleteMapping(value = "/deleteVideo/{id}")
    public RetMsg deleteVideo(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        boolean flag = eduVideoService.removeById(id);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }
}

