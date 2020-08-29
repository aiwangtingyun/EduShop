package com.wang.aclservice.service.impl;

import com.wang.aclservice.entity.Permission;
import com.wang.aclservice.mapper.PermissionMapper;
import com.wang.aclservice.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
