package com.wang.allservice.controller.msm;

import com.wang.allservice.service.msm.MsmService;
import com.wang.allservice.utils.RandomUtils;
import com.wang.allservice.utils.RetMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Api(tags = {"阿里云短信服务"})
@RestController
@RequestMapping(value = "/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplatel;

    // 发送短信
    @ApiOperation(value = "发送短信")
    @GetMapping("/send/{phone}")
    public RetMsg sendMsm(
            @ApiParam(name = "phone", value = "手机号码", required = true)
            @PathVariable String phone) {
        // 从redis获取验证码，如果获取得到则直接返回
        String code = redisTemplatel.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return RetMsg.ok();
        }

        // 生产随机值，传递给阿里云进行发送
        code = RandomUtils.getFourBitRandom();
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);

        // 调用service发送短信的接口
        boolean isSend = msmService.sendCode(param, phone);
        if (isSend) {
            // 发送成功，把发送成功验证码放到redis里面，并设置有效时间为5分钟
            redisTemplatel.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return RetMsg.ok();
        } else {
            return RetMsg.error().message("短信发送失败");
        }
    }
}
