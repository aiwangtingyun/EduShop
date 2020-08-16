package com.wang.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.wang.msm.service.MsmService;

import java.util.HashMap;

@Service
public class MsmServiceImpl implements MsmService {

    private String accessKeyId = "";
    private String accessSecretId = "";

    // 发送验证码
    @Override
    public boolean sendCode(HashMap<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        // 创建 Acs 请求客户端
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecretId);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers",phone); //手机号
        request.putQueryParameter("SignName","我的ES在线教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode","SMS_199792318"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}