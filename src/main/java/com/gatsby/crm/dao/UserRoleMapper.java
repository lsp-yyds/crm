package com.gatsby.crm.dao;

import com.gatsby.crm.base.BaseMapper;
import com.gatsby.crm.vo.User;
import com.gatsby.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole, Integer> {

    Integer countUserRoleByUserId(Integer userId);

    Integer deleteUserRoleByUserId(Integer userId);
}