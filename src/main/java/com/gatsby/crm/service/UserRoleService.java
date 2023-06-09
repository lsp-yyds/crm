package com.gatsby.crm.service;

import com.gatsby.crm.base.BaseService;
import com.gatsby.crm.dao.UserRoleMapper;
import com.gatsby.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: com.gatsby.crm.service
 * @NAME: UserRoleService
 * @AUTHOR: Jonah
 * @DATE: 2023/6/9
 */

@Service
public class UserRoleService extends BaseService<UserRole, Integer> {

    @Resource
    private UserRoleMapper userRoleMapper;


}
