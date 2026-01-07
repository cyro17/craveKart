package com.cyro.craveKart.service;

//import com.stripe.exception.StripeException;
import com.cyro.craveKart.model.Order;
import com.cyro.craveKart.model.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
	
	public PaymentResponse generatePaymentLink(Order order) throws StripeException;

}
