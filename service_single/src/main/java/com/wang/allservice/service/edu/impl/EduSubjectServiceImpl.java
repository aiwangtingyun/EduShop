package com.wang.allservice.service.edu.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.allservice.entity.edu.EduSubject;
import com.wang.allservice.entity.edu.excel.SubjectData;
import com.wang.allservice.entity.edu.subject.FirstSubject;
import com.wang.allservice.entity.edu.subject.SecondSubject;
import com.wang.allservice.listener.edu.SubjectExcelListener;
import com.wang.allservice.mapper.edu.EduSubjectMapper;
import com.wang.allservice.service.edu.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //获取所有课程分类列表（树形）
    @Override
    public List<FirstSubject> getAllOneTwoSubject() {
        //查询所有一级分类  parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> firstEduSubjectList = this.baseMapper.selectList(wrapperOne);

        //查询所有二级分类  parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> secondEduSubjectList = this.baseMapper.selectList(wrapperTwo);

        //存储封装一级和二级类表后的最终列表
        List<FirstSubject> finalList = new ArrayList<>();

        //封装一级和二级分类
        for (int i = 0; i < firstEduSubjectList.size(); i++) {
            // 复制一级分类
            EduSubject firstEduSubject = firstEduSubjectList.get(i);
            FirstSubject firstSubject = new FirstSubject();
            BeanUtils.copyProperties(firstEduSubject, firstSubject);
            finalList.add(firstSubject);

            // 获取一级分类对应的二级分类
            List<SecondSubject> children = new ArrayList<>();
            for (int j = 0; j < secondEduSubjectList.size(); j++) {
                EduSubject secondEduSubject = secondEduSubjectList.get(j);
                //根据 parent_id 值进行判断
                if (secondEduSubject.getParentId().equals(firstEduSubject.getId())) {
                    SecondSubject secondSubject = new SecondSubject();
                    BeanUtils.copyProperties(secondEduSubject, secondSubject);
                    children.add(secondSubject);
                }
            }

            // 把二级分类列表放到对应的一级分类中
            firstSubject.setChildren(children);
        }

        return finalList;
    }
}
