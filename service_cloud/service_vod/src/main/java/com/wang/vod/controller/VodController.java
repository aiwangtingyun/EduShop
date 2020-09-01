package com.wang.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wang.commonutils.RetMsg;
import com.wang.servicebase.exceptionhandler.EduShopException;
import com.wang.vod.service.VodService;
import com.wang.vod.utils.AliyunVodSDKUtils;
import com.wang.vod.utils.ConstantVodUtils;
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

    // 根据视频id获取视频播放凭证
    @ApiOperation(value = "根据视频id获取视频播放凭证")
    @GetMapping(value = "/getPlayAuth/{videoId}")
    public RetMsg getPlayAuth(
            @ApiParam(name = "videoId", value = "视频id")
            @PathVariable String videoId) {
        // 阿里云存储密钥
        String accessKeyId = ConstantVodUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;

        try {
            // 创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            // 创建请求对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);

            // 发送请求
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            // 返回凭证
            String playAuth = response.getPlayAuth();
            return RetMsg.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new EduShopException(20001, "获取凭证失败");
        }
    }

}
