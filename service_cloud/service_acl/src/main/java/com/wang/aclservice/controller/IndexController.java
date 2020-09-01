package com.wang.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.wang.aclservice.service.IndexService;
import com.wang.commonutils.RetMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aclservice/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/info")
    public RetMsg info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return RetMsg.ok().data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("/menu")
    public RetMsg getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return RetMsg.ok().data("permissionList", permissionList);
    }

    @PostMapping("/logout")
    public RetMsg logout(){
        return RetMsg.ok();
    }

}
