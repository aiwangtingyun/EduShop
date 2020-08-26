package com.wang.eduservice.client;

import com.wang.commonutis.RetMsg;
import org.springframework.stereotype.Component;

import java.util.List;

// VodClient调用出错之后执行下面对应的方法
@Component
public class VodFeignClient implements VodClient {
    @Override
    public RetMsg removeVideo(String videoId) {
        return RetMsg.error().message("Hystrix-删除视频出错了!");
    }

    @Override
    public RetMsg removeBatch(List<String> videoIdList) {
        return RetMsg.error().message("Hystrix-批量删除视频出错了!");
    }
}
