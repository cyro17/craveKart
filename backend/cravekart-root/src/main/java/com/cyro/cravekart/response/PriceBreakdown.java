package com.cyro.cravekart.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceBreakdown {
  private BigDecimal subtotal;
  private BigDecimal tax;
  private BigDecimal deliveryFee;
  private BigDecimal restaurantCharge;
  private BigDecimal discount;
  private BigDecimal platformFee;
  private BigDecimal total;
}
