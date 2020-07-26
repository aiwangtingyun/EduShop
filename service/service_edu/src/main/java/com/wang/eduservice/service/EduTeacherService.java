package com.wang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.entity.vo.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-07-25
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);
}
