package com.wang.aclservice.controller;


import com.wang.aclservice.entity.User;
import com.wang.aclservice.service.RoleService;
import com.wang.aclservice.service.UserService;
import com.wang.commonutils.MD5Utils;
import com.wang.commonutils.RetMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/aclservice/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public RetMsg index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return RetMsg.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public RetMsg save(@RequestBody User user) {
        user.setPassword(MD5Utils.encrypt(user.getPassword()));
        userService.save(user);
        return RetMsg.ok();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public RetMsg updateById(@RequestBody User user) {
        userService.updateById(user);
        return RetMsg.ok();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public RetMsg remove(@PathVariable String id) {
        userService.removeById(id);
        return RetMsg.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public RetMsg batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return RetMsg.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public RetMsg toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return RetMsg.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public RetMsg doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return RetMsg.ok();
    }

}

