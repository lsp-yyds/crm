package com.gatsby.crm.service;

import com.gatsby.crm.base.BaseService;
import com.gatsby.crm.dao.CustomerMapper;
import com.gatsby.crm.query.CustomerQuery;
import com.gatsby.crm.query.SaleChanceQuery;
import com.gatsby.crm.utils.AssertUtil;
import com.gatsby.crm.utils.PhoneUtil;
import com.gatsby.crm.vo.Customer;
import com.gatsby.crm.vo.SaleChance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.management.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.gatsby.crm.service
 * @NAME: CustomerService
 * @AUTHOR: Jonah
 * @DATE: 2023/6/12
 */

@Service
public class CustomerService extends BaseService<Customer, Integer> {

    @Resource
    private CustomerMapper customerMapper;

    public Map<String, Object> queryCustomerByParams(CustomerQuery customerQuery) {
        Map<String, Object> map = new HashMap<>();

        PageHelper.startPage(customerQuery.getPage(), customerQuery.getLimit());

        PageInfo<Customer> pageInfo = new PageInfo<>(customerMapper.selectByParams(customerQuery));

        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());

        map.put("data", pageInfo.getList());

        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addCustomer(Customer customer) {
        checkCustomerParams(customer.getName(), customer.getFr(), customer.getPhone());
        Customer temp = customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null != temp, "客户对象已存在，请重新输入！");

        customer.setIsValid(1);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());

        String khno = "KH" + System.currentTimeMillis();
        customer.setKhno(khno);

        AssertUtil.isTrue(customerMapper.insertSelective(customer) < 1, "添加客户信息失败！");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer) {

        AssertUtil.isTrue(null == customer.getId(), "待更新记录不存在！");

        Customer temp = customerMapper.selectByPrimaryKey(customer.getId());
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");

        checkCustomerParams(customer.getName(), customer.getFr(), customer.getPhone());
        temp = customerMapper.queryCustomerByName(customer.getName());

        AssertUtil.isTrue(null != temp && !(temp.getId()).equals(customer.getId()), "客户名称已存在，请重新输入！");
        customer.setUpdateDate(new Date());

        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer) < 1, "修改客户信息失败！");
    }

    private void checkCustomerParams(String name, String fr, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(name), "客户名称不能为空！");

        AssertUtil.isTrue(StringUtils.isBlank(fr), "法人代表不能为空！");

        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号码不能为空！");

        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "手机号格式不正确！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomer(Integer id) {
        AssertUtil.isTrue(null == id, "待删除记录不存在！");

        Customer customer = customerMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == customer, "待删除记录不存在！");

        customer.setIsValid(0);
        customer.setUpdateDate(new Date());

        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer) < 1, "客户信息删除失败！");
    }
}
