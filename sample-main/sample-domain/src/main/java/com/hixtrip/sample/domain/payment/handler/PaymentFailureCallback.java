package com.hixtrip.sample.domain.payment.handler;

import com.hixtrip.sample.domain.payment.PaymentCallback;
import com.hixtrip.sample.domain.payment.dto.PaymentResult;
import org.springframework.stereotype.Service;

@Service(value = "failure")
public class PaymentFailureCallback implements PaymentCallback {
    @Override
    public void handle(PaymentResult paymentResult) {
        System.out.println("支付失败，处理逻辑...");
        // 处理支付失败的逻辑，如重试支付、发送通知等
    }
}
