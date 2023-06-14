package com.gatsby.crm.controller;

import com.gatsby.crm.base.BaseController;
import com.gatsby.crm.query.CustomerOrderQuery;
import com.gatsby.crm.service.CustomerOrderService;
import com.gatsby.crm.vo.CustomerOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.gatsby.crm.controller
 * @NAME: CustomerOrderController
 * @AUTHOR: Jonah
 * @DATE: 2023/6/12
 */

@RequestMapping("order")
@Controller
public class CustomerOrderController extends BaseController {

    @Resource
    private CustomerOrderService customerOrderService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomerOrderByParams(CustomerOrderQuery customerOrderQuery) {

        return customerOrderService.queryCustomerOrderByParams(customerOrderQuery);
    }

    @RequestMapping("toOrderDetailPage")
    public String toOrderDetailPage(Integer orderId, Model model) {
        Map<String, Object> map = customerOrderService.queryOrderById(orderId);

        model.addAttribute("order", map);

        return "customer/customer_order_detail";
    }
}
