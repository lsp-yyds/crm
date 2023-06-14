package com.gatsby.crm.dao;

import com.gatsby.crm.base.BaseMapper;
import com.gatsby.crm.vo.Customer;

public interface CustomerMapper extends BaseMapper<Customer, Integer> {
    Customer queryCustomerByName(String name);
}