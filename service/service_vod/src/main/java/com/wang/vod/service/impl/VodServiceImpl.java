package com.wang.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.wang.servicebase.exceptionhandler.EduShopException;
import com.wang.vod.service.VodService;
import com.wang.vod.utils.AliyunVodSDKUtils;
import com.wang.vod.utils.ConstantVodUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    // 上传视频
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            // 文件原始名称
            String filename = file.getOriginalFilename();
            // 上传之后的显示名称
            String title = filename.substring(0, filename.lastIndexOf('.'));

            // 使用文件流的方式上传
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET,
                    title, filename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            // 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if (StringUtils.isEmpty(videoId)) {
                    throw new EduShopException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据云端视频id删除视频
    @Override
    public void removeVideo(String videoId) {
        try {
            // 初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频的request请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request设置视频id
            request.setVideoIds(videoId);
            // 调用初始化对象的方法实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.println("RequestId : " + response.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduShopException(20001, "删除视频失败");
        }
    }

    // 批量删除视频
    @Override
    public void removeVideoBatch(List<String> videoIdList) {
        try {
            // 初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频的request请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            // videoIdList 值转换成逗号分隔的字符串：1, 2, 3
            String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ',');

            // 向request设置视频id
            request.setVideoIds(videoIds);
            // 调用初始化对象的方法实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.println("RequestId : " + response.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduShopException(20001, "删除视频失败");
        }
    }
}
