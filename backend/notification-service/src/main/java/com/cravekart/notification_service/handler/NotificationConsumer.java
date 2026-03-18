package com.cravekart.notification_service.handler;

import com.cravekart.core.events.notification.OrderConfirmedEvent;
import com.cravekart.core.events.notification.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    public void handlePaymentSuccess(PaymentSuccessEvent event){
        log.info("NotificationConsumer: payment-success received for orderId={}", event.getOrderId());

//        try{
//
//        }
    }

//    public void handleOrderConfirmed(OrderConfirmedEvent event) {
//        log.info("NotificationConsumer: order-confirmed received for orderId = {}", event.getOrderId());
//
//        try{
//            String subject = "Your order is prepared - Order #" + event.getOrderId();
//            String body = buildOrderConfirmedEmail(
//                    event.getCustomerName(),
//                    event.getOrderId(),
//                    event.getRestaurantName(),
//                    event.getTotalPrice(),
//                    event.getEstimatedDeliveryTime()
//            );
//
//
//
//
//        }
//    }

//
//    private String buildOrderConfirmedEmail(String customerName,
//                                            Long orderId,
//                                            String restaurantname,
//                                            String totalPrice,
//                                            String estimatedTime){
//
//    }

}
