package com.wang.allservice.service.edu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.allservice.controller.vod.VodController;
import com.wang.allservice.entity.edu.EduVideo;
import com.wang.allservice.handler.EduShopException;
import com.wang.allservice.mapper.edu.EduVideoMapper;
import com.wang.allservice.service.edu.EduVideoService;
import com.wang.allservice.utils.RetMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    // 注入VodClient
    @Autowired
    private VodController vodClient;

    // 删除课程小节：同时删除云端视频
    @Override
    public boolean removeVideoById(String id) {
        // 根据小节id获取云端视频id
        EduVideo eduVideo = this.baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        // 小节中有id才调用远程远程使用云端视频删除吧
        if (!StringUtils.isEmpty(videoSourceId)) {
            RetMsg result = vodClient.removeVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new EduShopException(20001, "删除视频失败，熔断器...");
            }
        }

        // 删除小节
        return this.baseMapper.deleteById(id) > 0;
    }

    // 根据课程id删除课程小节：同时删除云端小节视频
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 根据课程id查询所有课程所有视频id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");  // 只查询 video_source_id 字段
        List<EduVideo> eduVideosList = this.baseMapper.selectList(wrapper);

        // 封装视频id
        ArrayList<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo: eduVideosList) {
            String videoSourceId = eduVideo.getVideoSourceId();
            // 排除视频id为空的
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }

        // 远程调用删除小节视频
        if (videoIds.size() > 0) {
            vodClient.removeBatch(videoIds);
        }

        // 删除表中小节
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        this.baseMapper.delete(wrapper2);
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
