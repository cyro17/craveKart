package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PriceBreakdown {

  private BigDecimal subtotal;
  private BigDecimal tax;
  private BigDecimal deliveryFee;
  private BigDecimal restaurantCharge;
  private BigDecimal discount;
  private BigDecimal platformFee;
  private BigDecimal total;


}
