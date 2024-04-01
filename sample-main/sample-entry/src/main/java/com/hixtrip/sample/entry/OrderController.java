package com.hixtrip.sample.entry;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.payment.PaymentCallback;
import com.hixtrip.sample.domain.payment.dto.PaymentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * todo 这是你要实现的
 */
@RestController
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    //自动注入对应的实现类
    @Resource
    private Map<String, PaymentCallback> paymentCallbackMap;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public String order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) {
        //登录信息可以在这里模拟
        var userId = "";
        //下单...
        String result = orderService.orderByCommandOderCreateDTO(commandOderCreateDTO);
        return result;
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        if (StringUtils.isNotBlank(commandPayDTO.getPayStatus()) && paymentCallbackMap.containsKey(commandPayDTO.getPayStatus())) {
            //根据类型不同调用不同的方法
            paymentCallbackMap.get(commandPayDTO.getPayStatus()).handle(new PaymentResult());
        }else{
            log.warn("当前传入的支付状态不正确或者当前状态没有对应的处理方法,请查看后尝试");
        }
        return "";
    }

}
