package com.wang.eduservice.service;

import com.wang.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
public interface EduVideoService extends IService<EduVideo> {

    // 根据课程id删除课程小节
    void removeVideoByCourseId(String courseId);

    // 根据章节id查询小节数量
    Integer getCountByChapterId(String chapterId);

    // 删除课程小节
    boolean removeVideoById(String id);
}
