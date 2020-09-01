package com.wang.allservice.service.edu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.allservice.entity.edu.EduChapter;
import com.wang.allservice.entity.edu.EduVideo;
import com.wang.allservice.entity.edu.chapter.ChapterVo;
import com.wang.allservice.entity.edu.chapter.VideoVo;
import com.wang.allservice.handler.EduShopException;
import com.wang.allservice.mapper.edu.EduChapterMapper;
import com.wang.allservice.service.edu.EduChapterService;
import com.wang.allservice.service.edu.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-10
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    // 根据课程id获取课程大纲列表
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 根据课程id查询课程中的所有章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = this.baseMapper.selectList(chapterWrapper);

        // 根据课程id查询课程中的所有小结
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(videoWrapper);

        // 封装所有章节和小结的最终数据列表
        ArrayList<ChapterVo> finalList = new ArrayList<>();

        // 对所有章节和小结进行封装
        for (EduChapter eduChapter :  eduChapterList) {
            // 封装章节
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);

            // 封装小结列表
            ArrayList<VideoVo> children = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    children.add(videoVo);
                }
            }
            chapterVo.setChildren(children);
        }

        return finalList;
    }

    // 根据章节id删除章节
    @Override
    public boolean removeChapterById(String chapterId) {
        // 判断章节中是否存在小节
        if (videoService.getCountByChapterId(chapterId) > 0) {
            throw new EduShopException(20001, "该分章节下存在视频课程，请先删除视频课程");
        }
        // 删除空小节的章节
        int result = this.baseMapper.deleteById(chapterId);
        return result != 0;
    }

    // 根据课程id删除章节
    @Override
    public boolean removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        this.baseMapper.delete(wrapper);
        return false;
    }
}
