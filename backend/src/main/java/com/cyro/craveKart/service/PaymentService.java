package com.cyro.craveKart.service;

import com.cyro.craveKart.model.Order;
import com.cyro.craveKart.model.PaymentResponse;

public interface PaymentService {
    public PaymentResponse generatePaymentLink(Order order) throws Exception;

}
