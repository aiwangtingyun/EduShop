package com.wang.edustatistics.client;

import com.wang.commonutis.RetMsg;

public class UcenterFeignClient implements UcenterClient {

    @Override
    public RetMsg countRegister(String day) {
        return RetMsg.error().message("查询注册人数失败！");
    }
}
