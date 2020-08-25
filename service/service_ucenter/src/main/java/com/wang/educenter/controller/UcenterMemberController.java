package com.wang.educenter.controller;


import com.wang.commonutis.JwtUtils;
import com.wang.commonutis.RetMsg;
import com.wang.commonutis.ordervo.UcenterMemberOrderVo;
import com.wang.educenter.entity.UcenterMember;
import com.wang.educenter.entity.vo.RegisterVo;
import com.wang.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-16
 */
@Api(tags = {"用户管理中心"})
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    // 登陆
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public RetMsg login(@RequestBody UcenterMember member) {
        // 登陆成功返回token
        String token = memberService.login(member);
        return RetMsg.ok().data("token", token);
    }

    // 注册
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public RetMsg register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return RetMsg.ok();
    }

    // 根据token获取用户信息
    @ApiOperation("根据token获取用户信息")
    @GetMapping("/getMemberInfo")
    public RetMsg getMemberInfo(HttpServletRequest request) {
        // 调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return RetMsg.ok().data("userInfo", member);
    }

    // 根据用户id获取订单用户信息
    @ApiOperation("根据用户id获取用户信息")
    @GetMapping("/getMemberInfoOrder/{id}")
    public UcenterMemberOrderVo getMemberInfoOrder(
            @ApiParam(name = "id", value = "用户id", required = true)
            @PathVariable("id") String id) {
        // 根据id查询用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        // 封装返回信息
        UcenterMemberOrderVo memberOrderVo = new UcenterMemberOrderVo();
        BeanUtils.copyProperties(ucenterMember, memberOrderVo);
        return memberOrderVo;
    }

}

