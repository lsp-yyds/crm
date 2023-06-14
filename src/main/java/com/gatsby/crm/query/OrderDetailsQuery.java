package com.gatsby.crm.query;

import com.gatsby.crm.base.BaseQuery;

/**
 * @PACKAGE_NAME: com.gatsby.crm.query
 * @NAME: OrderDetailsQuery
 * @AUTHOR: Jonah
 * @DATE: 2023/6/12
 */
public class OrderDetailsQuery extends BaseQuery {
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
