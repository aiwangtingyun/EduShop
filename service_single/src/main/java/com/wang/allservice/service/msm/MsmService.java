package com.wang.allservice.service.msm;

import java.util.HashMap;

public interface MsmService {

    // 发送验证码
    boolean sendCode(HashMap<String, Object> param, String phone);
}
