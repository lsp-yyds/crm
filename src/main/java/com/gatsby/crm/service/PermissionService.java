package com.gatsby.crm.service;

import com.gatsby.crm.base.BaseService;
import com.gatsby.crm.dao.PermissionMapper;
import com.gatsby.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @PACKAGE_NAME: com.gatsby.crm.service
 * @NAME: PermissionService
 * @AUTHOR: Jonah
 * @DATE: 2023/6/10
 */

@Service
public class PermissionService extends BaseService<Permission, Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 通过查询用户拥有的角色，角色拥有的资源，得到用户拥有的资源列表
     *
     * @param userId
     * @return
     */
    public List<String> queryUserHasPermissionByUserId(Integer userId) {
        return permissionMapper.queryUserHasPermissionByUserId(userId);
    }
}
