package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderAddReq;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.dataobject.OrderBaseDo;
import com.hixtrip.sample.infra.db.mapper.OrderBaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderBaseMapper orderBaseMapper;

    @Override
    public void addNewOrder(OrderAddReq addReq) {
        OrderBaseDo orderBaseDo = new OrderBaseDo();
        BeanUtils.copyProperties(addReq,orderBaseDo);
        orderBaseMapper.insert(orderBaseDo);
    }
}
