package com.wang.educenter.service;

import com.wang.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-16
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    // 用户登录
    String login(UcenterMember member);

    // 用于注册
    void register(RegisterVo registerVo);

    // 根据openId获取member对象
    UcenterMember getMemberByOpenId(String openid);

    // 查询某一天的注册人数
    Integer countRegisterDay(String day);
}
