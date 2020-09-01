package com.wang.allservice.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.allservice.entity.order.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-24
 */
public interface OrderService extends IService<Order> {

    // 根据课程id生成订单
    String createOrder(String courseId, String memberId);
}
