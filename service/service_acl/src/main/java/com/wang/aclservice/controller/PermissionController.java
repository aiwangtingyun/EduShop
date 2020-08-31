package com.wang.aclservice.controller;


import com.wang.aclservice.entity.Permission;
import com.wang.aclservice.service.PermissionService;
import com.wang.commonutils.RetMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限菜单管理
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/aclservice/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // 查询所有权限菜单
    @ApiOperation(value = "查询所有权限菜单")
    @GetMapping("/getAll")
    public RetMsg getAllPermission() {
        List<Permission> list =  permissionService.queryAllPermission();
        return RetMsg.ok().data("children",list);
    }

    // 根据角色id获取权限菜单
    @ApiOperation(value = "根据角色id获取权限菜单")
    @GetMapping("/getById/{roleId}")
    public RetMsg getPermissionById(@PathVariable String roleId) {
        List<Permission> list = permissionService.queryPermissionById(roleId);
        return RetMsg.ok().data("children", list);
    }

    // 根据id递归删除权限菜单
    @ApiOperation(value = "根据id递归删除权限菜单")
    @DeleteMapping("/removeById/{id}")
    public RetMsg removeById(@PathVariable String id) {
        permissionService.removePermissionById(id);
        return RetMsg.ok();
    }

    // 给角色分配权限
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/assign")
    public RetMsg assignPermission(String roleId, String[] permissionId) {
        permissionService.assignRolePermission(roleId, permissionId);
        return RetMsg.ok();
    }

    // 新增权限菜单
    @ApiOperation(value = "新增权限菜单")
    @PostMapping("/save")
    public RetMsg save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return RetMsg.ok();
    }

    // 修改权限菜单
    @ApiOperation(value = "修改权限菜单")
    @PutMapping("/update")
    public RetMsg updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return RetMsg.ok();
    }

}

