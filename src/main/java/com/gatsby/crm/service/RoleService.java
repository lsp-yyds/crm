package com.gatsby.crm.service;

import com.gatsby.crm.base.BaseService;
import com.gatsby.crm.dao.ModuleMapper;
import com.gatsby.crm.dao.PermissionMapper;
import com.gatsby.crm.dao.RoleMapper;
import com.gatsby.crm.utils.AssertUtil;
import com.gatsby.crm.vo.Permission;
import com.gatsby.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.gatsby.crm.service
 * @NAME: RoleService
 * @AUTHOR: Jonah
 * @DATE: 2023/6/7
 */
@Service
public class RoleService extends BaseService<Role, Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleMapper.queryAllRoles(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role) {
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "角色名称不能为空！");
        Role temp = roleMapper.selectByRoleName(role.getRoleName());

        AssertUtil.isTrue(null != temp, "角色名称已存在，请重新输入！");

        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());

        AssertUtil.isTrue(roleMapper.insertSelective(role) < 1, "角色添加失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role) {
        AssertUtil.isTrue(null == role.getId(), "待更新记录不存在！");

        Role temp = roleMapper.selectByPrimaryKey(role.getId());

        AssertUtil.isTrue(null == temp, "待更新记录不存在！");

        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "角色名称不能为空！");

        temp = roleMapper.selectByRoleName(role.getRoleName());

        AssertUtil.isTrue(null != temp && !(temp.getId().equals(role.getId())), "角色名称已存在，请重新输入！");

        role.setUpdateDate(new Date());

        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) < 1, "修改角色失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRole(Integer roleId) {
        AssertUtil.isTrue(null == roleId, "待删除记录不存在！");

        Role role = roleMapper.selectByPrimaryKey(roleId);

        AssertUtil.isTrue(null == role, "待删除记录不存在！");

        role.setIsValid(0);
        role.setUpdateDate(new Date());

        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) < 1, "角色删除失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer roleId, Integer[] mIds) {
        Integer count = permissionMapper.countPermissionByRoleId(roleId);
        if (count > 0) {
            permissionMapper.deletePermissionByRoleId(roleId);
        }

        if (mIds != null && mIds.length > 0) {
            List<Permission> permissionList = new ArrayList<>();

            for (Integer mId : mIds) {
                Permission permission = new Permission();
                
                permission.setModuleId(mId);
                permission.setRoleId(roleId);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mId).getOptValue());
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());

                permissionList.add(permission);
            }

            AssertUtil.isTrue(permissionMapper.insertBatch(permissionList) != permissionList.size(), "角色授权失败！");
        }
    }
}
