package com.wang.allservice.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.allservice.entity.order.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-24
 */
public interface PayLogService extends IService<PayLog> {

    // 根据订单号生成微信支付二维码
    Map<String, Object> createNative(String orderNo);

    // 根据订单号查询支付状态
    Map<String, String> queryPayStatus(String orderNo);

    // 更新支付状态
    void updateOrderStatus(Map<String, String> map);
}
