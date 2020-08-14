package com.wang.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.wang.servicebase.exceptionhandler.EduShopException;
import com.wang.vod.service.VodService;
import com.wang.vod.utils.ConstantVodUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

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
}
