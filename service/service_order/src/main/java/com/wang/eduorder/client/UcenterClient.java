package com.wang.eduorder.client;

import com.wang.commonutis.ordervo.UcenterMemberOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 远程调用ucenter接口
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    // 根据用户id获取订单用户信息
    @GetMapping("/educenter/member/getMemberInfoOrder/{id}")
    public UcenterMemberOrderVo getMemberInfoOrder(@PathVariable("id") String id);
}
