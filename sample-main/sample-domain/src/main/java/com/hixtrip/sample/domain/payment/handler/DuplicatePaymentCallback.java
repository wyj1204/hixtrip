package com.hixtrip.sample.domain.payment.handler;

import com.hixtrip.sample.domain.payment.PaymentCallback;
import com.hixtrip.sample.domain.payment.dto.PaymentResult;
import org.springframework.stereotype.Service;

@Service(value = "duplicate")
public class DuplicatePaymentCallback implements PaymentCallback {
    @Override
    public void handle(PaymentResult paymentResult) {
        System.out.println("重复支付，处理逻辑...");
        // 处理重复支付的逻辑，如记录日志、发送通知等
    }
}
