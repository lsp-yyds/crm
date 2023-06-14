package com.gatsby.crm.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gatsby.crm.base.BaseService;
import com.gatsby.crm.dao.ModuleMapper;
import com.gatsby.crm.dao.PermissionMapper;
import com.gatsby.crm.model.TreeModule;
import com.gatsby.crm.utils.AssertUtil;
import com.gatsby.crm.vo.Module;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.gatsby.crm.service
 * @NAME: ModuleService
 * @AUTHOR: Jonah
 * @DATE: 2023/6/9
 */

@Service
public class ModuleService extends BaseService<Module, Integer> {

    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    public List<TreeModule> queryAllModules(Integer roleId) {
        List<TreeModule> treeModuleList = moduleMapper.queryAllModules();

        List<Integer> permissionIds = permissionMapper.queryRoleHasModuleIdsByRoleId(roleId);

        if (permissionIds != null && permissionIds.size() > 0) {
            treeModuleList.forEach(treeModule -> {
                if (permissionIds.contains(treeModule.getId())) {
                    treeModule.setChecked(true);
                }
            });
        }

        return treeModuleList;
    }

    public Map<String, Object> queryModuleList() {
        Map<String, Object> map = new HashMap<>();

        List<Module> moduleList = moduleMapper.queryModuleList();

        map.put("code", 0);
        map.put("msg", "");
        map.put("count", moduleList.size());
        map.put("data", moduleList);

        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addModule(Module module) {
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2), "菜单层级不合法！");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "模块名称不能为空！");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndModuleName(grade, module.getModuleName()), "该层级下模块名称已存在！");

        if (grade == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "URL不能为空！");
            AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndUrl(grade, module.getUrl()), "URL不可重复！");
        }

        if (grade == 0) {
            module.setParentOptValue("-1");
        }

        if (grade != 0) {
            AssertUtil.isTrue(null == module.getParentId(), "父级菜单不能为空！");
            AssertUtil.isTrue(null == moduleMapper.selectByPrimaryKey(module.getParentId()), "请指定正确的父级菜单！");
        }

        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "权限码不能为空！");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByOptValue(module.getOptValue()), "权限码已存在！");

        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());

        AssertUtil.isTrue(moduleMapper.insertSelective(module) < 1, "添加资源失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module) {
        AssertUtil.isTrue(null == module.getId(), "待更新记录不存在！");
        Module temp = moduleMapper.selectByPrimaryKey(module.getId());
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");

        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2), "菜单层级不合法！");

        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "模块名称不能为空！");

        temp = moduleMapper.queryModuleByGradeAndModuleName(grade, module.getModuleName());
        if (temp != null) {
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())), "该层级下菜单名已存在！");
        }

        if (grade == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "菜单URL不能为空！");
            temp = moduleMapper.queryModuleByGradeAndUrl(grade, module.getUrl());
            if (temp != null) {
                AssertUtil.isTrue(!(temp.getId().equals(module.getId())), "该层级下菜单URL已存在");
            }
        }

        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "权限码不能为空！");
        temp = moduleMapper.queryModuleByOptValue(module.getOptValue());

        if (temp != null) {
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())), "权限码已存在！");
        }

        module.setUpdateDate(new Date());

        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module) < 1, "修改资源失败！");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModule(Integer id) {
        
        AssertUtil.isTrue(null == id, "待删除记录不存在！");

        Module temp = moduleMapper.selectByPrimaryKey(id);

        AssertUtil.isTrue(null == temp, "待删除记录不存在！");

        Integer count = moduleMapper.queryModuleByParentId(id);
        AssertUtil.isTrue(count > 0, "该资源存在子记录不可删除！");

        count = permissionMapper.countPermissionByModuleId(id);
        if (count > 0) {
            permissionMapper.deletePermissionByModuleId(id);
        }

        temp.setIsValid((byte) 0);
        temp.setUpdateDate(new Date());

        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(temp) < 1, "删除资源失败！");
    }
}
