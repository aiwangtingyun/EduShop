package com.wang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.wang.eduservice.entity.EduSubject;
import com.wang.eduservice.entity.excel.SubjectData;
import com.wang.eduservice.listener.SubjectExcelListener;
import com.wang.eduservice.mapper.EduSubjectMapper;
import com.wang.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
}
