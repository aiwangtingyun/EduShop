package com.wang.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.wang.aclservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-29
 */
public interface PermissionService extends IService<Permission> {

    // 查询所有权限菜单
    List<Permission> queryAllPermission();

    // 根据角色id获取权限菜单
    List<Permission> queryPermissionById(String roleId);

    // 给角色分配权限
    void assignRolePermission(String roleId, String[] permissionIds);

    // 根据id递归删除权限菜单
    void removePermissionById(String id);

    // 根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String userId);

    List<JSONObject> selectPermissionByUserId(String userId);

}
