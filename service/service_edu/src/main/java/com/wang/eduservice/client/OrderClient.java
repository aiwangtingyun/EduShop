package com.wang.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-order", fallback = OrderFeignClient.class)
public interface OrderClient {

    // 根据课程id和用户id查询订单状态
    @GetMapping("/eduorder/order/getPayState/{courseId}/{memberId}")
    public boolean getPayState(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);
}
