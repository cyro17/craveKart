package com.cyro.cravekart.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {
  private String mail;

  @NotBlank(message = " mobile number must not be blank")
  private String mobile;

  private String twitter;
  private String instagram;
}
