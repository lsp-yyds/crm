package com.gatsby.crm.controller;

import com.gatsby.crm.base.BaseController;
import com.gatsby.crm.service.UserRoleService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: com.gatsby.crm.controller
 * @NAME: UserRoleController
 * @AUTHOR: Jonah
 * @DATE: 2023/6/9
 */

@Controller
public class UserRoleController extends BaseController {

    @Resource
    private UserRoleService userRoleService;

    
}
