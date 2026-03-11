package com.cyro.cravekart.controllers.dev;

import com.cyro.cravekart.models.PaymentResponse;
import com.cyro.cravekart.request.PaymentRequest;
//import com.cyro.cravekart.service.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

//  private final PaymentService paymentService;
//
//  @GetMapping("/check")
//  public ResponseEntity<String> check() {
//    return new ResponseEntity<>("OK",  HttpStatus.OK);
//  }
//
//  @PostMapping("/checkout")
//  public ResponseEntity<PaymentResponse> checkout(@RequestBody PaymentRequest paymentRequest) {
//    PaymentResponse paymentResponse = paymentService.checkoutProducts(paymentRequest);
//    return ResponseEntity.ok().body(paymentResponse);
//  }
}
