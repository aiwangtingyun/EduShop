package com.wang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.entity.vo.TeacherQueryVo;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-07-25
 */
public interface EduTeacherService extends IService<EduTeacher> {

    // 后端分页查询讲师列表
    void pageQuery(Page<EduTeacher> pageParam, TeacherQueryVo teacherQueryVo);

    // 前端分页查询讲师列表
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam);
}
