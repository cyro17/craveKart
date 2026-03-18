package com.cravekart.core.events.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSuccessEvent {
    private Long orderId;
    private Long customerId;
    private Long amount;
    private String currency;
    private String stripePaymentIntentId;
}
