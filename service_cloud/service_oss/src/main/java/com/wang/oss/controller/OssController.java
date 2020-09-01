package com.wang.oss.controller;

import com.wang.commonutils.RetMsg;
import com.wang.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"OSS文件上传"})
@RestController
@RequestMapping("/eduoss/file")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @ApiOperation(value = "上传头像文件")
    @PostMapping(value = "/upload")
    public RetMsg uploadOssFile(
            @ApiParam(name = "file", value = "文件名", required = true)
            @RequestParam("file") MultipartFile file) {
        //获取上传文件 MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);

        return RetMsg.ok().data("url", url);
    }
}
