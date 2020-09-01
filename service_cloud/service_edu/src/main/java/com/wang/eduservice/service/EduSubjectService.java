package com.wang.eduservice.service;

import com.wang.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.entity.subject.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-09
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<FirstSubject> getAllOneTwoSubject();
}
