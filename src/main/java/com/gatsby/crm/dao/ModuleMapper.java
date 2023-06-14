package com.gatsby.crm.dao;

import com.gatsby.crm.base.BaseMapper;
import com.gatsby.crm.model.TreeModule;
import com.gatsby.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module, Integer> {
    List<TreeModule> queryAllModules();

    List<Module> queryModuleList();

    Module queryModuleByGradeAndModuleName(@Param("grade") Integer grade, @Param("moduleName") String moduleName);

    Module queryModuleByGradeAndUrl(@Param("grade") Integer grade, @Param("url") String url);

    Module queryModuleByOptValue(String optValue);

    Integer queryModuleByParentId(Integer id);
}