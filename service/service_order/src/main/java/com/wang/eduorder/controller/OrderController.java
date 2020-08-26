package com.wang.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutis.JwtUtils;
import com.wang.commonutis.RetMsg;
import com.wang.eduorder.entity.Order;
import com.wang.eduorder.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-24
 */
@Api(tags = {"订单支付管理"})
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 根据课程id生成订单
    @ApiOperation("根据课程id生成订单")
    @PostMapping("/createOrder/{courseId}")
    public RetMsg createOrder(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable("courseId") String courseId,
            HttpServletRequest request) {
        // 创建订单返回订单号
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId, memberId);

        return RetMsg.ok().data("orderNo", orderNo);
    }

    // 根据订单号查询订单信息
    @ApiOperation("根据订单号查询订单信息")
    @GetMapping("/getOrderInfo/{orderNo}")
    public RetMsg getOrderInfo(
            @ApiParam(name = "orderNo", value = "订单号", required = true)
            @PathVariable("orderNo") String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return RetMsg.ok().data("item", order);
    }

    // 根据课程id和用户id查询订单状态
    @ApiOperation("根据课程id和用户id查询订单状态")
    @GetMapping("/getPayState/{courseId}/{memberId}")
    public boolean getPayState(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable("courseId") String courseId,
            @ApiParam(name = "memberId", value = "用户id", required = true)
            @PathVariable("memberId") String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        return count > 0;
    }

}

