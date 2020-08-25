package com.wang.eduorder.service.impl;

import com.wang.eduorder.entity.PayLog;
import com.wang.eduorder.mapper.PayLogMapper;
import com.wang.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
