package com.gatsby.crm.service;

import com.gatsby.crm.base.BaseService;
import com.gatsby.crm.dao.ModuleMapper;
import com.gatsby.crm.dao.PermissionMapper;
import com.gatsby.crm.model.TreeModule;
import com.gatsby.crm.vo.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
