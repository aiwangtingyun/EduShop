package com.wang.msm.service;

import java.util.HashMap;

public interface MsmService {

    // 发送验证码
    boolean sendCode(HashMap<String, Object> param, String phone);
}
