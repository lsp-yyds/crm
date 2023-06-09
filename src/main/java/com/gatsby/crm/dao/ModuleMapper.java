package com.gatsby.crm.dao;

import com.gatsby.crm.base.BaseMapper;
import com.gatsby.crm.model.TreeModule;
import com.gatsby.crm.vo.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module, Integer> {
    List<TreeModule> queryAllModules();
}