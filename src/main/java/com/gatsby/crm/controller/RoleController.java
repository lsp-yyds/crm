package com.gatsby.crm.controller;

import com.gatsby.crm.base.BaseController;
import com.gatsby.crm.base.ResultInfo;
import com.gatsby.crm.query.RoleQuery;
import com.gatsby.crm.service.RoleService;
import com.gatsby.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.gatsby.crm.controller
 * @NAME: RoleController
 * @AUTHOR: Jonah
 * @DATE: 2023/6/7
 */

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleService.queryAllRoles(userId);
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> selectByParams(RoleQuery roleQuery) {
        return roleService.queryByParamsForTable(roleQuery);
    }

    @RequestMapping("index")
    public String index() {
        return "role/role";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addRole(Role role) {
        roleService.addRole(role);
        return success("角色添加成功！");
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role) {
        roleService.updateRole(role);
        return success("角色修改成功！");
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer id) {
        roleService.deleteRole(id);
        return success("角色删除成功！");
    }


    @RequestMapping("addOrUpdateRolePage")
    public String addOrUpdateRolePage(Integer id, HttpServletRequest req) {
        if (id != null) {
            Role role = roleService.selectByPrimaryKey(id);
            req.setAttribute("role", role);
        }

        return "role/add_update";
    }

    @PostMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId, Integer[] mIds) {
        
        roleService.addGrant(roleId, mIds);

        return success("角色授权成功！");
    }

}
