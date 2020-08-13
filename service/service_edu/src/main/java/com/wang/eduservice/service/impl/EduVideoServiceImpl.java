package com.wang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.entity.EduVideo;
import com.wang.eduservice.mapper.EduVideoMapper;
import com.wang.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    // 根据课程id删除课程小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        this.baseMapper.delete(wrapper);
    }

    // 根据章节id查询小节数量
    @Override
    public Integer getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        Integer count = this.baseMapper.selectCount(wrapper);

        return count;
    }
}
