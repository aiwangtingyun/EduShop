package com.wang.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.wang.eduorder.entity.Order;
import com.wang.eduorder.entity.PayLog;
import com.wang.eduorder.mapper.PayLogMapper;
import com.wang.eduorder.service.OrderService;
import com.wang.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.eduorder.utils.HttpClient;
import com.wang.servicebase.exceptionhandler.EduShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-24
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    // 根据订单号生成微信支付二维码
    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            // 1 根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);

            // 2 使用map设置生成二维码需要参数
            Map<String, String> m = new HashMap<>();
            m.put("appid","wx74862e0dfcf69954"); // 关联的公众号appid
            m.put("mch_id", "1558950191");  // 商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n"); // 回调地址
            m.put("trade_type", "NATIVE");

            // 3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            // 设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb")); // 商户key
            client.setHttps(true);
            // 执行post请求发送
            client.post();

            // 4 得到发送请求返回结果，返回内容为xml格式数据
            String xml = client.getContent();

            // 把xml格式转换map集合，把map集合返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            // 5 最终返回数据 的封装
            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  // 返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        // 二维码地址

            return map;
        } catch (Exception e) {
            throw new EduShopException(20001, "生成二维码失败");
        }
    }

    // 根据订单号查询支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            // 1 封装查询参数
            Map<String, String> map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954"); // 关联的公众号appid
            map.put("mch_id", "1558950191");        // 商户号
            map.put("out_trade_no", orderNo);       //订单号
            map.put("nonce_str", WXPayUtil.generateNonceStr());

            // 2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            // 3 获取请求返回的内容
            String content = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    // 更新支付状态
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        // 获取订单号
        String orderNo = map.get("out_trade_no");

        // 根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        // 更新订单表订单状态
        if (order.getStatus() == 1) {
            return;
        }
        order.setStatus(1); // 1代表已经支付
        orderService.updateById(order);

        // 添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(1); //支付类型 1微信
        payLog.setTotalFee(order.getTotalFee()); //总金额(分)
        payLog.setTradeState(map.get("trade_state")); //支付状态
        payLog.setTransactionId(map.get("transaction_id"));  //流水号
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }
}
