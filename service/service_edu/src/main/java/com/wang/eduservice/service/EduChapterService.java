package com.wang.eduservice.service;

import com.wang.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
public interface EduChapterService extends IService<EduChapter> {

    // 根据课程id获取课程大纲列表
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    // 根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
