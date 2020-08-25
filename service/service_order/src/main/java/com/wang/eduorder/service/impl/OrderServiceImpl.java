package com.wang.eduorder.service.impl;

import com.wang.eduorder.client.EduClient;
import com.wang.eduorder.client.UcenterClient;
import com.wang.eduorder.entity.Order;
import com.wang.eduorder.mapper.OrderMapper;
import com.wang.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduClient eduClient;

    // 根据课程id生成订单
    @Override
    public String createOrder(String courserId, String memberId) {
        // 远程调用获取用户信息

        // 远程调用获取课程信息

        // 封装订单对象

        // 返回订单号
        return null;
    }
}
