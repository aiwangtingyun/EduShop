package com.wang.eduservice.client;

import com.wang.commonutis.RetMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient注解用于指定从哪个服务中调用功能，名称与被调用的服务名保持一致
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    // 根据云端视频id删除视频
    // 调用的方法路径一定要写完整
    // @PathVariable注解一定要指定参数名称，否则出错
    @DeleteMapping(value = "/eduvod/video/removeVideo/{videoId}")
    RetMsg removeVideo(@PathVariable("videoId") String videoId);

    // 批量删除视频
    // 参数为多个视频id
    @DeleteMapping(value = "/eduvod/video/removeBatch")
    RetMsg removeBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
