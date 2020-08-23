package com.wang.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

public class VodSDKTest {

    private static String accessKeyId = "accessKeyId";
    private static String accessKeySecret = "accessKeySecret";

    // 视频上传
    @Test
    public void uploadVideo() {

        //上传之后文件名称
        String title = "TestVideo - upload by sdk";
        //本地文件路径和名称
        String fileName = "D:\\TestVideo.mp4";

        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        // 可指定分片上传时每个分片的大小，默认为2M字节
        request.setPartSize(2 * 1024 * 1024L);
        // 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            // 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    // 根据视频id获取视频播放凭证
    @Test
    public void getPlayAuth() throws Exception{
        // 视频id
        String videoId = "a1a08f1b7ed2419aa636517eee738901";

        // 初始化 vod 客户端、请求对象和相应对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 请求获取播放凭证
        request.setVideoId(videoId);
        response = client.getAcsResponse(request);

        // 打印凭证信息
        System.out.println("PlayAuth : " + response.getPlayAuth());
        System.out.println("VideoMeta.Title : " + response.getVideoMeta().getTitle());
        System.out.println("RequestId : " + response.getRequestId());
    }

    // 根据视频id获取视频播放地址
    @Test
    public void getPlayUrl() throws Exception{
        // 视频id
        String videoId = "7c3fa0b6376046718c8b3beb6002b4e3";

        //创建初始化对象、请求对象和相应对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //请求获取播放地址：这里只能获取非加密视频的播放地址
        request.setVideoId(videoId);
        response = client.getAcsResponse(request);

        //打印播放地址信息
        System.out.print("VideoBase.Title : " + response.getVideoBase().getTitle() + "\n");
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL : " + playInfo.getPlayURL() + "\n");
        }

    }
}
