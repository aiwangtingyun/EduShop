package com.wang.eduorder.client;

import com.wang.commonutils.ordervo.UcenterMemberOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 远程调用ucenter接口
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterFeignClient.class)
public interface UcenterClient {

    // 根据用户id获取订单用户信息
    @GetMapping("/educenter/member/getMemberInfoOrder/{id}")
    public UcenterMemberOrderVo getMemberInfoOrder(@PathVariable("id") String id);
}
