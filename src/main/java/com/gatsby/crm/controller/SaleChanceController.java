package com.gatsby.crm.controller;

import com.gatsby.crm.annotation.RequiredPermission;
import com.gatsby.crm.base.BaseController;
import com.gatsby.crm.base.ResultInfo;
import com.gatsby.crm.enums.StateStatus;
import com.gatsby.crm.query.SaleChanceQuery;
import com.gatsby.crm.service.SaleChanceService;
import com.gatsby.crm.utils.CookieUtil;
import com.gatsby.crm.utils.LoginUserUtil;
import com.gatsby.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.gatsby.crm.controller
 * @NAME: SaleChanceController
 * @AUTHOR: Jonah
 * @DATE: 2023/6/5
 */

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @RequiredPermission(code = "1010")
    @RequestMapping("index")
    public String index() {
        return "saleChance/sale_chance";
    }

    @RequiredPermission(code = "101002")
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(HttpServletRequest req, SaleChance saleChance) {
        String userName = CookieUtil.getCookieValue(req, "userName");
        saleChance.setCreateMan(userName);

        saleChanceService.addSaleChance(saleChance);
        return success("营销机会数据添加成功！");
    }

    @RequiredPermission(code = "101003")
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids) {
        saleChanceService.deleteSaleChance(ids);
        return success("营销机会删除成功！");
    }

    @RequiredPermission(code = "101004")
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance) {
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功！");
    }

    @RequiredPermission(code = "101001")
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams(HttpServletRequest req, SaleChanceQuery saleChanceQuery, Integer flag) {
        if (flag != null && flag == 1) {
            saleChanceQuery.setState(StateStatus.STATED.getType());
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
            saleChanceQuery.setAssignMan(userId);
        }
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(HttpServletRequest req, Integer id) {
        System.out.println(id);

        if (id != null) {
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            req.setAttribute("saleChance", saleChance);
        }

        return "saleChance/add_update";
    }

    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id, Integer devResult) {

        saleChanceService.updateSaleChanceDevResult(id, devResult);

        return success("开发状态更新成功");
    }
}
