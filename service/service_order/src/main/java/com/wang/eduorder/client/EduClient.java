package com.wang.eduorder.client;

import com.wang.commonutis.ordervo.CourseWebOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 远程调用eduservice接口
@Component
@FeignClient(name = "service-edu", fallback = EduFeignClient.class)
public interface EduClient {

    // 根据课程id查询表订单课程信息
    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    public CourseWebOrderVo getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
