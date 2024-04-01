package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;

/**
 * 订单的service层
 */
public interface OrderService {

    /**
     *
     *  生成订单
     *
     * @param commandOderCreateDTO 订单参数
     * @return
     */
    String orderByCommandOderCreateDTO(CommandOderCreateDTO commandOderCreateDTO);
}
