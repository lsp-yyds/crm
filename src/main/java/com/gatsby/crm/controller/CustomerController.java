package com.gatsby.crm.controller;

import com.gatsby.crm.base.BaseController;
import com.gatsby.crm.base.ResultInfo;
import com.gatsby.crm.query.CustomerQuery;
import com.gatsby.crm.service.CustomerService;
import com.gatsby.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.gatsby.crm.controller
 * @NAME: CustomerController
 * @AUTHOR: Jonah
 * @DATE: 2023/6/12
 */

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomerByParams(CustomerQuery customerQuery) {
        return customerService.queryCustomerByParams(customerQuery);
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addCustomer(Customer customer) {
        customerService.addCustomer(customer);

        return success("添加客户信息成功！");
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer) {
        customerService.updateCustomer(customer);

        return success("客户信息修改成功！");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomer(Integer id) {
        customerService.deleteCustomer(id);
        return success("客户信息删除成功！");
    }

    @RequestMapping("index")
    public String index() {
        return "customer/customer";
    }

    @RequestMapping("toAddOrUpdateCustomerPage")
    public String toAddOrUpdateCustomerPage(Integer id, HttpServletRequest req) {
        if (id != null) {
            Customer customer = customerService.selectByPrimaryKey(id);
            req.setAttribute("customer", customer);
        }
        return "customer/add_update";
    }

    @RequestMapping("toOrderInfoPage")
    public String toOrderInfoPage(Integer id, Model model) {
        model.addAttribute("customer", customerService.selectByPrimaryKey(id));
        return "customer/customer_order";
    }
    
}
