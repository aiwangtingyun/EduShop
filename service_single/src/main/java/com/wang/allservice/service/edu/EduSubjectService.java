package com.wang.allservice.service.edu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.allservice.entity.edu.EduSubject;
import com.wang.allservice.entity.edu.subject.FirstSubject;
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
