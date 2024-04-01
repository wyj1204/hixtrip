package com.hixtrip.sample.domain.payment;

import com.hixtrip.sample.domain.payment.dto.PaymentResult;

public interface PaymentCallback {
    void handle(PaymentResult paymentResult);
}


