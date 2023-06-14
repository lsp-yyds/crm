package com.gatsby.crm.query;

import com.gatsby.crm.base.BaseQuery;

/**
 * @PACKAGE_NAME: com.gatsby.crm.query
 * @NAME: CustomerQuery
 * @AUTHOR: Jonah
 * @DATE: 2023/6/12
 */
public class CustomerQuery extends BaseQuery {
    private String customerName;
    private String customerNo;
    private String level;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
