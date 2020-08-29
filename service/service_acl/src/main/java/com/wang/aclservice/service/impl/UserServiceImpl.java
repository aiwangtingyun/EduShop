package com.wang.aclservice.service.impl;

import com.wang.aclservice.entity.User;
import com.wang.aclservice.mapper.UserMapper;
import com.wang.aclservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
