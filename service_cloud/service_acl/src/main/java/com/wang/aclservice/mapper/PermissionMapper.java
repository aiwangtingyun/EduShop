package com.wang.aclservice.mapper;

import com.wang.aclservice.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-29
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);

}
