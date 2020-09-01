package com.wang.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    // 上传视频
    String uploadVideo(MultipartFile file);

    // 根据云端视频id删除视频
    void removeVideo(String videoId);

    // 批量删除视频
    void removeVideoBatch(List<String> videoIdList);
}
