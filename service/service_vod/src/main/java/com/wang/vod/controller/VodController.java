package com.wang.vod.controller;

import com.wang.commonutis.RetMsg;
import com.wang.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"阿里云视频点播微服务"})
@RestController
@RequestMapping(value = "/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传视频
    @ApiOperation(value = "上传视频")
    @PostMapping(value = "/upload")
    public RetMsg uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        if (videoId == null) {
            return RetMsg.error().message("视频上传失败");
        } else {
            return RetMsg.ok().message("视频上传成功").data("videoId", videoId);
        }
    }

    // 根据云端视频id删除视频
    @ApiOperation(value = "根据视频id删除视频")
    @DeleteMapping(value = "/removeVideo/{videoId}")
    public RetMsg removeVideo(
            @ApiParam(name = "videoId", value = "云端视频id")
            @PathVariable String videoId) {
        vodService.removeVideo(videoId);
        return RetMsg.ok().message("视频删除成功");
    }

    // 批量删除视频
    @ApiOperation(value = "批量删除视频")
    @DeleteMapping(value = "/removeBatch")
    public RetMsg removeBatch(
            @ApiParam(name = "videoId", value = "云端视频id")
            @RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeVideoBatch(videoIdList);
        return RetMsg.ok().message("视频删除成功");
    }

}