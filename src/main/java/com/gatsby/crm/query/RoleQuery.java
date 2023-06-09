package com.gatsby.crm.query;

import com.gatsby.crm.base.BaseQuery;

/**
 * @PACKAGE_NAME: com.gatsby.crm.query
 * @NAME: RoleQuery
 * @AUTHOR: Jonah
 * @DATE: 2023/6/9
 */
public class RoleQuery extends BaseQuery {
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
