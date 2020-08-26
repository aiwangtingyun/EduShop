package com.wang.eduservice.client;

import org.springframework.stereotype.Component;

// OrderClient调用出错之后执行下面对应的方法
@Component
public class OrderFeignClient implements OrderClient {

    @Override
    public boolean getPayState(String courseId, String memberId) {
        return false;
    }
}
