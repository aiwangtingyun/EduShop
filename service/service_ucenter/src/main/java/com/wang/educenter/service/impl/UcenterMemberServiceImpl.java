package com.wang.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutis.JwtUtils;
import com.wang.commonutis.MD5Utils;
import com.wang.educenter.entity.UcenterMember;
import com.wang.educenter.entity.vo.RegisterVo;
import com.wang.educenter.mapper.UcenterMemberMapper;
import com.wang.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.servicebase.exceptionhandler.EduShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-16
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 用户登录
    @Override
    public String login(UcenterMember member) {
        // 获取登陆手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        // 手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new EduShopException(20001, "手机号码或密码不能为空！");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = this.baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new EduShopException(20001, "用户不存在！");
        }

        // 判断密码是否正确：密码为MD5加密
        if (!MD5Utils.encrypt(password).equals(mobileMember.getPassword())) {
            throw new EduShopException(20001, "密码不正确！");
        }

        // 判断是否被禁言
        if (mobileMember.getIsDisabled()) {
            throw new EduShopException(20001, "该用户已经被禁用！");
        }

        // 登陆成功
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    // 用于注册
    @Override
    public void register(RegisterVo registerVo) {
        // 获取用户注册的数据
        String mobile = registerVo.getMobile(); // 手机号
        String nickname = registerVo.getNickname(); // 用户名
        String password = registerVo.getPassword(); // 密码
        String code = registerVo.getCode(); // 验证码

        // 非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new EduShopException(20001, "注册失败");
        }

        // 判断验证码是否有效
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new EduShopException(20001, "注册失败");
        }

        // 判断手机号是否已注册
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = this.baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new EduShopException(20001, "注册失败");
        }

        // 注册用户数据到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5Utils.encrypt(password)); // 密码需要MD5加密
        member.setNickname(nickname);
        member.setIsDisabled(false);  // 设置用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.baseMapper.insert(member);
    }
}
