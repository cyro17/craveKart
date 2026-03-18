package com.cyro.cravekart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerDto {
  private Long id;
  private String customerName;
  private String customerEmail;
  private String customerPhone;
  private String customerAddress;
}
