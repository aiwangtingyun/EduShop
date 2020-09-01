package com.wang.allservice.service.order.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.allservice.controller.edu.front.CourseFrontController;
import com.wang.allservice.controller.ucenter.UcenterMemberController;
import com.wang.allservice.entity.order.Order;
import com.wang.allservice.entity.order.vo.CourseWebOrderVo;
import com.wang.allservice.entity.order.vo.UcenterMemberOrderVo;
import com.wang.allservice.mapper.order.OrderMapper;
import com.wang.allservice.service.order.OrderService;
import com.wang.allservice.utils.order.OrderNoUtil;
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
    private UcenterMemberController ucenterClient;

    @Autowired
    private CourseFrontController eduClient;

    // 根据课程id生成订单
    @Override
    public String createOrder(String courseId, String memberId) {
        // 远程调用获取用户信息
        UcenterMemberOrderVo userInfo = ucenterClient.getMemberInfoOrder(memberId);

        // 远程调用获取课程信息
        CourseWebOrderVo courseInfo = eduClient.getCourseInfoOrder(courseId);

        // 封装订单对象
        Order order = new Order();
        String orderNo = OrderNoUtil.getOrderNo();          // 生成订单号
        order.setOrderNo(orderNo);                          // 订单号
        order.setCourseId(courseId);                        // 课程id
        order.setCourseTitle(courseInfo.getTitle());        // 课程名称
        order.setCourseCover(courseInfo.getCover());        // 课程封面
        order.setTeacherName(courseInfo.getTeacherName());  // 讲师名称
        order.setTotalFee(courseInfo.getPrice());           // 订单金额
        order.setMemberId(memberId);                        // 会员id
        order.setMobile(userInfo.getMobile());              // 会员手机
        order.setNickname(userInfo.getNickname());          // 会员昵称
        order.setStatus(0);     // 订单状态（0：未支付 1：已支付）
        order.setPayType(1);    // 支付类型（1：微信 2：支付宝）
        baseMapper.insert(order);

        // 返回订单号
        return orderNo;
    }
}
