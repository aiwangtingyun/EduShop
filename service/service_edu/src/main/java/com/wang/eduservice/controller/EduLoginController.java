package com.wang.eduservice.controller;

import com.wang.commonutis.Msg;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/edu/user")
@CrossOrigin    // 解决跨域问题
public class EduLoginController {

    // 登录
    @PostMapping(value = "login")
    public Msg login() {
        return Msg.ok().data("token", "admin");
    }

    // 获取用户信息
    @GetMapping(value = "info")
    public Msg info() {
        return Msg.ok().data("roles", "admin").data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
