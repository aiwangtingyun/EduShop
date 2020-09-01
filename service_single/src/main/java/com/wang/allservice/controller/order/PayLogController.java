package com.wang.allservice.controller.order;

import com.wang.allservice.service.order.PayLogService;
import com.wang.allservice.utils.RetMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-24
 */
@Api(tags = {"订单支付记录管理"})
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    // 根据订单号生成微信支付二维码
    @ApiOperation("根据订单号生成微信支付二维码")
    @GetMapping("/createNative/{orderNo}")
    public RetMsg createNative(
            @ApiParam(name = "orderNo", value = "订单号", required = true)
            @PathVariable("orderNo") String orderNo) {
        // 返回信息包括二维码地址和其它需要的信息
        Map<String, Object> map = payLogService.createNative(orderNo);
        System.out.println("------- 二维码map集合数据： " + map);
        return RetMsg.ok().data(map);
    }

    // 根据订单号查询订单支付状态
    @ApiOperation("根据订单号查询订单支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public RetMsg queryPayStatus(
            @ApiParam(name = "orderNo", value = "订单号", required = true)
            @PathVariable("orderNo") String orderNo) {
        // 获取支付状态
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("------- 订单状态map集合数据： " + map);

        // 为空表示支付出错
        if (map == null) {
            return RetMsg.error().message("支付出错了");
        }

        // 如果支付成功则更新支付状态
        if (map.get("trade_state").equals("SUCCESS")) {
            payLogService.updateOrderStatus(map);
            return RetMsg.ok().message("支付成功");
        }

        return RetMsg.ok().code(25000).message("支付中");
    }

}

