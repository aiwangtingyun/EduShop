package com.wang.eduservice.controller;


import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.subject.FirstSubject;
import com.wang.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-09
 */
@Api(tags = {"课程管理"})
@RestController
@RequestMapping(value = "/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @ApiOperation(value = "添加课程")
    @PostMapping("/addSubject")
    public RetMsg addSubject(MultipartFile file) {
        //上传过来excel文件
        eduSubjectService.saveSubject(file, eduSubjectService);
        return RetMsg.ok();
    }

    //获取所有课程分类列表（树形）
    @ApiOperation(value = "获取所有课程分类列表")
    @GetMapping("/getAllSubject")
    public RetMsg getAllSubject() {
        //list集合泛型是一级分类
        List<FirstSubject> list = eduSubjectService.getAllOneTwoSubject();
        return RetMsg.ok().data("list", list);
    }

}

