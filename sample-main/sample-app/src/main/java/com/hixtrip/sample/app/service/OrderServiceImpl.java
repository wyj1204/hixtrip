package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderAddReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public String orderByCommandOderCreateDTO(CommandOderCreateDTO commandOderCreateDTO) {
        //todo... 通过商品id查询商品信息,来获取卖家的id,假设搜索到的卖家id是2
        OrderAddReq orderAddReq= new OrderAddReq();
        orderAddReq.setSkuId(Long.valueOf(commandOderCreateDTO.getSkuId()));
        orderAddReq.setBuyerId(Long.valueOf(commandOderCreateDTO.getUserId()));
        orderAddReq.setCreateUserId(commandOderCreateDTO.getUserId());
        //假设查出来得问卖家id是"1"
        orderAddReq.setSellerId(1L);
        //todo.... 订单号根据业务规则生成...,目前没说明,暂时使用UUid生成
        orderAddReq.setOrderId(UUID.randomUUID().toString());
        //假设查出来的价格是10
        orderAddReq.setOrderAmount(new BigDecimal(10).multiply(new BigDecimal(commandOderCreateDTO.getAmount())));
        orderAddReq.setAmount(Long.valueOf(commandOderCreateDTO.getAmount()));
        //”0“:未支付 ”1“:已支付
        orderAddReq.setPayState("0");
        //”0“:未删除 ”1“:已删除
        orderAddReq.setState("0");
        //”1“：表示进行中
        orderAddReq.setOrderStatus("1");
        orderAddReq.setCreateTime(new Date());
        orderAddReq.setModifyUserId(commandOderCreateDTO.getUserId());
        orderAddReq.setModifyTime(new Date());
        orderDomainService.createOrder(orderAddReq);
        return "ok";
    }
}
