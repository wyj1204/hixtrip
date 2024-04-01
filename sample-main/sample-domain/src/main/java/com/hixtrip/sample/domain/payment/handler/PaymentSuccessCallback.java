package com.hixtrip.sample.domain.payment.handler;

import com.hixtrip.sample.domain.payment.PaymentCallback;
import com.hixtrip.sample.domain.payment.dto.PaymentResult;
import org.springframework.stereotype.Service;

@Service(value = "success")
public class PaymentSuccessCallback implements PaymentCallback {
    @Override
    public void handle(PaymentResult paymentResult) {
        System.out.println("支付成功，处理逻辑...");
        // 处理支付成功的逻辑，如更新订单状态、发送通知等
    }
}
