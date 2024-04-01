package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderAddReq;

/**
 *
 */
public interface OrderRepository {
    void addNewOrder(OrderAddReq addReq);
}
