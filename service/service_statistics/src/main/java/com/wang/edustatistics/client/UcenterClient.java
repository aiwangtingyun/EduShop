package com.wang.edustatistics.client;

import com.wang.commonutis.RetMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter", fallback = UcenterFeignClient.class)
public interface UcenterClient {

    // 查询某一天的注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public RetMsg countRegister(@PathVariable("day") String day);
}
