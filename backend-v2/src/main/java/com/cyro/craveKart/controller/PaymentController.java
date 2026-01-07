package com.cyro.craveKart.controller;

import com.cyro.craveKart.exceptions.OrderException;
import com.cyro.craveKart.model.Order;
import com.cyro.craveKart.service.OrderServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.exception.StripeException;
import com.cyro.craveKart.model.PaymentResponse;
import com.cyro.craveKart.service.PaymentService;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private OrderServiceImplementation orderService;

	@PostMapping("/{orderId}/payment")
	public ResponseEntity<PaymentResponse> generatePaymentLink(@PathVariable Long orderId)
            throws StripeException, OrderException {

		Order order = orderService.findOrderById(orderId);
		PaymentResponse res = paymentService.generatePaymentLink(order);

		return new ResponseEntity<PaymentResponse>(res,HttpStatus.ACCEPTED);
	}

}
