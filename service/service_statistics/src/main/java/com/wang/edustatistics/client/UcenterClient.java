package com.wang.edustatistics.client;

import com.wang.commonutils.RetMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter", fallback = UcenterFeignClient.class)
public interface UcenterClient {

    // 查询某一天的注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public RetMsg countRegister(@PathVariable("day") String day);
}
