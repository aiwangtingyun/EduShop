package com.wang.eduorder.client;

import com.wang.commonutils.ordervo.UcenterMemberOrderVo;
import org.springframework.stereotype.Component;

@Component
public class UcenterFeignClient implements UcenterClient {

    @Override
    public UcenterMemberOrderVo getMemberInfoOrder(String id) {
        return null;
    }
}
