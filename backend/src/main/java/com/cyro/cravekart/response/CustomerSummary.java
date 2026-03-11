package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSummary {

  private Long customerId;
  private String name;
}
