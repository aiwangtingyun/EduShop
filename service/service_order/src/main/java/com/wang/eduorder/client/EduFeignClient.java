package com.wang.eduorder.client;

import com.wang.commonutis.ordervo.CourseWebOrderVo;
import org.springframework.stereotype.Component;

@Component
public class EduFeignClient implements EduClient {
    @Override
    public CourseWebOrderVo getCourseInfoOrder(String courseId) {
        return null;
    }
}
