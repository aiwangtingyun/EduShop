package com.wang.allservice.service.edu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.allservice.entity.edu.EduTeacher;
import com.wang.allservice.entity.edu.vo.TeacherQueryVo;
import com.wang.allservice.mapper.edu.EduTeacherMapper;
import com.wang.allservice.service.edu.EduTeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    // 后端分页查询讲师列表
    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQueryVo teacherQueryVo) {
        // 构建查询条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (teacherQueryVo == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        // 多条件组合查询
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String begin = teacherQueryVo.getBegin();
        String end = teacherQueryVo.getEnd();

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

    // 前端分页查询讲师列表
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam) {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");

        // 查询数据并封装到 page 对象中
        baseMapper.selectPage(pageParam, wrapper);

        // 获取查询结果
        List<EduTeacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        // 封装数据结果并返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

}
