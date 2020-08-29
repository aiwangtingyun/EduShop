package com.wang.edustatistics.client;

import com.wang.commonutils.RetMsg;
import org.springframework.stereotype.Component;

@Component
public class UcenterFeignClient implements UcenterClient {

    @Override
    public RetMsg countRegister(String day) {
        return RetMsg.error().message("查询注册人数失败！");
    }
}
