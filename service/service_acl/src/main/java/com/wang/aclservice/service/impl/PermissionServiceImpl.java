package com.wang.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.aclservice.entity.Permission;
import com.wang.aclservice.entity.RolePermission;
import com.wang.aclservice.entity.User;
import com.wang.aclservice.helper.MenuHelper;
import com.wang.aclservice.helper.PermissionHelper;
import com.wang.aclservice.mapper.PermissionMapper;
import com.wang.aclservice.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.aclservice.service.RolePermissionService;
import com.wang.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    // 查询所有权限菜单
    @Override
    public List<Permission> queryAllPermission() {
        // 查询权限表中所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        // 对权限表数据进行封装
        List<Permission> result = bulidPermission(permissionList);

        return result;
    }

    /**
     * 使用递归方法创建权限菜单
     * @param permissionList
     * @return
     */
    private static List<Permission> bulidPermission(List<Permission> permissionList) {
        // 创建list集合，用于数据最终封装
        List<Permission> finalNode = new ArrayList<>();

        // 把所有菜单list集合遍历，得到顶层菜单
        for (Permission permissionNode : permissionList) {
            // pid=0 为顶层菜单，即递归入口点
            if ("0".equals(permissionNode.getPid())) {
                // 设置顶层菜单的level是1
                permissionNode.setLevel(1);
                // 从顶层开始递归查询所有子菜单
                finalNode.add(findChildren(permissionNode, permissionList));
            }
        }
        return finalNode;
    }

    /**
     * 递归查找子节点
     * @param permissionNode 需要递归查找的节点
     * @param permissionList 全部权限菜单列表
     * @return 目标子节点对应的全部子节点
     */
    private static Permission findChildren(Permission permissionNode, List<Permission> permissionList) {
        // 初始化节点的子菜单对象
        permissionNode.setChildren(new ArrayList<Permission>());

        // 遍历查找节点对应的所有子节点
        for (Permission it : permissionList) {
            // 判断id值和pid值是否相同
            if(permissionNode.getId().equals(it.getPid())) {
                // level值递增+1
                int level = permissionNode.getLevel() + 1;
                it.setLevel(level);
                // 如果children为空，进行初始化操作
                if (permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                // 把查询出来的子菜单放到父菜单里面，然后继续递归查找子子菜单
                permissionNode.getChildren().add(findChildren(it, permissionList));
            }
        }
        return permissionNode;
    }

    // 根据角色id获取权限菜单
    @Override
    public List<Permission> queryPermissionById(String roleId) {
        // 获取所有权限列表
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("CAST(id AS SIGNED)");
        List<Permission> allPermissionList = baseMapper.selectList(wrapper);

        // 根据角色id获取角色权限
        QueryWrapper<RolePermission> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_id",roleId);
        List<RolePermission> rolePermissionList = rolePermissionService.list(wrapper1);

        // 设置需要查询的权限菜单
        for (Permission permission : allPermissionList) {
            for (RolePermission rolePermission : rolePermissionList) {
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }

        List<Permission> permissionList = bulidPermission(allPermissionList);
        return permissionList;
    }

    // 给角色分配权限
    @Override
    public void assignRolePermission(String roleId, String[] permissionIds) {
        // 创建list集合，用于封装添加数据
        ArrayList<RolePermission> rolePermissionsList = new ArrayList<>();
        // 遍历所有权限菜单数组
        for (String perId : permissionIds) {
            // 封装角色权限对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            rolePermissionsList.add(rolePermission);
        }
        // 添加到角色权限菜单关系表
        rolePermissionService.saveBatch(rolePermissionsList);
    }

    // 根据id递归删除权限菜单
    @Override
    public void removePermissionById(String id) {
        // 创建list集合，用于封装所有需要删除的id值
        ArrayList<String> idList = new ArrayList<>();
        // 查找目标id对应的所有子菜单的id值
        this.selectChildListById(id, idList);
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        // 查询菜单里面子菜单id
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Permission> childrenList = baseMapper.selectList(wrapper);

        // 把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childrenList.stream().forEach(item -> {
            // 把id值添加到idList中
            idList.add(item.getId());
            // 继续往下递归查询
            this.selectChildListById(item.getId(), idList);
        });
    }

    // 根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String userId) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(userId)) {
            // 如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(userId);
        }
        return selectPermissionValueList;
    }

    // 根据用户id获取用户菜单
    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            // 如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MenuHelper.bulid(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

}
