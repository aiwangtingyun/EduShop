package com.wang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.entity.EduChapter;
import com.wang.eduservice.entity.EduVideo;
import com.wang.eduservice.entity.chapter.ChapterVo;
import com.wang.eduservice.entity.chapter.VideoVo;
import com.wang.eduservice.mapper.EduChapterMapper;
import com.wang.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.eduservice.service.EduVideoService;
import org.apache.velocity.runtime.directive.Foreach;
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

    // 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        this.baseMapper.delete(wrapper);
    }
}
