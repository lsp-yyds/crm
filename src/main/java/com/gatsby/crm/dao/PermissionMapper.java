package com.gatsby.crm.dao;

import com.gatsby.crm.base.BaseMapper;
import com.gatsby.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission, Integer> {
    Integer countPermissionByRoleId(Integer roleId);

    void deletePermissionByRoleId(Integer roleId);

    List<Integer> queryRoleHasModuleIdsByRoleId(Integer roleId);
}