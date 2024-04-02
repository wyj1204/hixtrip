package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderAddReq;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
   private OrderRepository orderRepository;
    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(CommandOderCreateDTO commandOderCreateDTO) {
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
        //需要你在infra实现, 自行定义出入参
        orderRepository.addNewOrder(orderAddReq);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }
}
