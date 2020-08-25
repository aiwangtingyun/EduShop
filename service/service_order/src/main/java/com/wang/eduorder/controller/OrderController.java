package com.wang.eduorder.controller;


import com.wang.commonutis.JwtUtils;
import com.wang.commonutis.RetMsg;
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
    @PostMapping("/createOrder/{courserId}")
    public RetMsg createOrder(
            @ApiParam(name = "courserId", value = "课程id", required = true)
            @PathVariable("courserId") String courserId,
            HttpServletRequest request) {
        // 创建订单返回订单号
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courserId, memberId);

        return RetMsg.ok().data("orderNo", orderNo);
    }

}

