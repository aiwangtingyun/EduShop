package com.wang.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutis.RetMsg;
import com.wang.eduservice.entity.EduTeacher;
import com.wang.eduservice.entity.vo.TeacherQuery;
import com.wang.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-25
 */
@Api(tags = {"讲师管理"})
@RestController
@RequestMapping("/eduservice/admin")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @ResponseBody
    @GetMapping(value = "/teachers")
    public RetMsg findAllTeachers() {
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return RetMsg.ok().data("items", teacherList);
    }

    @ApiOperation(value = "分页查询讲师")
    @ResponseBody
    @GetMapping(value = "/pageTeacher/{page}/{limit}")
    public RetMsg pageTeacher(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        // 创建page对象
        Page<EduTeacher> pageParam = new Page<>(page, limit);

        // 调用方法实现分页
        // 调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(pageParam, null);

        long total = pageParam.getTotal();  // 总记录数
        List<EduTeacher> records = pageParam.getRecords();  // 数据记录集合

        return RetMsg.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "带条件的分页查询讲师")
    @ResponseBody
    @PostMapping(value = "/pageTeacherCondition/{page}/{limit}")
    public RetMsg pageTeacherCondition(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody(required = false)TeacherQuery teacherQuery) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.pageQuery(pageParam, teacherQuery);

        long total = pageParam.getTotal();  // 总记录数
        List<EduTeacher> records = pageParam.getRecords();  // 数据记录集合

        return RetMsg.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @ResponseBody
    @GetMapping(value = "/getTeacher/{id}")
    public RetMsg getTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        EduTeacher teacher = eduTeacherService.getById(id);
        return RetMsg.ok().data("item", teacher);
    }

    @ApiOperation(value = "新增讲师")
    @ResponseBody
    @PostMapping(value = "/addTeacher")
    public RetMsg addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody(required = true)EduTeacher teacher) {

        boolean flag = eduTeacherService.save(teacher);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

    @ApiOperation(value = "根据ID逻辑删除讲师")
    @ResponseBody
    @DeleteMapping(value = "/teacher/{id}")
    public RetMsg removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

    @ApiOperation(value = "根据ID修改讲师")
    @ResponseBody
    @PutMapping(value = "/updateTeacher/{id}")
    public RetMsg updateTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {

        teacher.setId(id);  // 使用 put 请求需要手动添加 id 值
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag) {
            return RetMsg.ok();
        } else {
            return RetMsg.error();
        }
    }

}

