package com.wang.allservice.controller.ucenter;

import com.google.gson.Gson;
import com.wang.allservice.entity.ucenter.UcenterMember;
import com.wang.allservice.handler.EduShopException;
import com.wang.allservice.service.ucenter.UcenterMemberService;
import com.wang.allservice.utils.JwtUtils;
import com.wang.allservice.utils.ucenter.ConstantWxUtils;
import com.wang.allservice.utils.ucenter.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller // 只是请求地址不需要返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    // 生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "EduShop"
        );

        // 重定向到请求微信地址中
        return "redirect:" + url;
    }

    // 微信扫码回调请求
    @GetMapping("/callback")
    public String callback(String code, String state) {
        // 从 redis 中将 state 获取出来，和当前传入的 state 作比较
        // 如果一致则放行，如果不一致则抛出异常：非法访问

        try {
            // 微信扫描回调返回的code值为临时票据，相当于验证码
            // 使用code去请求微信固定的地址获取 accsess_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            // 拼接三个参数：微信id、微信秘钥和code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            // 使用httpclient发送请求获取 accsess_token 和 openid
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            // 提取 accsess_token 和 openid
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");

            // 根据openid查询数据库是否已存在，不存在则添加到数据库里面
            UcenterMember member = memberService.getMemberByOpenId(openid);
            if (member == null) {   //memeber是空，则表示没有相同的微信数据
                // 根据accsess_token和openid去请求微信提供固定的地址，获取到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                // 填充accsess_token和openid参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                // 发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                // 提取用于信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname"); // 昵称
                String headimgurl = (String) userInfoMap.get("headimgurl"); // 头像地址
                // 保存用户信息
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }

            // 生产token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            // 返回首页面并通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;

        } catch (Exception e) {
            throw new EduShopException(20001, "登录失败");
        }
    }
}
