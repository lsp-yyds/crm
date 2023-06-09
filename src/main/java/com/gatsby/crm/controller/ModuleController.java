package com.gatsby.crm.controller;

import com.gatsby.crm.base.BaseController;
import com.gatsby.crm.base.ResultInfo;
import com.gatsby.crm.model.TreeModule;
import com.gatsby.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @PACKAGE_NAME: com.gatsby.crm.controller
 * @NAME: ModuleController
 * @AUTHOR: Jonah
 * @DATE: 2023/6/9
 */

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeModule> queryAllModules(Integer roleId) {
        return moduleService.queryAllModules(roleId);
    }

    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, HttpServletRequest req) {
        if (roleId != null) {
            req.setAttribute("roleId", roleId);
        }

        return "role/grant";
    }
}
