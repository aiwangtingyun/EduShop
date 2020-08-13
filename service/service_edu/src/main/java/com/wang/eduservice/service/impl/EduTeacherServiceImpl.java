package com.wang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.eduservice.entity.EduTeacher;
import com.wang.eduservice.entity.vo.TeacherQuery;
import com.wang.eduservice.mapper.EduTeacherMapper;
import com.wang.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-07-25
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
        // 构建查询条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (teacherQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        // 多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 动态组合 sql 语句
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.gt("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.lt("gmt_create", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }
}
