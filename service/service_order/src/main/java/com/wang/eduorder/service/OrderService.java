package com.wang.eduorder.service;

import com.wang.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

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
    String createOrder(String courserId, String memberId);
}
