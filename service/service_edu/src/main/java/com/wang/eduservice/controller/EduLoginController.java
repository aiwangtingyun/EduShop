package com.wang.eduservice.controller;

import com.wang.commonutis.RetMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"用户登录"})
@RestController
@RequestMapping(value = "/eduservice/user")
@CrossOrigin    // 解决跨域问题
public class EduLoginController {

    // 登录
    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public RetMsg login() {
        return RetMsg.ok().data("token", "admin");
    }

    // 获取用户信息
    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "/info")
    public RetMsg info() {
        return RetMsg.ok().data("roles", "admin").data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
