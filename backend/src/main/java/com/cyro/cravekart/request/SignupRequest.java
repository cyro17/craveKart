package com.cyro.cravekart.request;

import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.ContactInfo;
import com.cyro.cravekart.models.customAnnotations.UniqueEmail;
import com.cyro.cravekart.models.customAnnotations.UniqueUsername;
import com.cyro.cravekart.models.enums.USER_ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequest {

  @NotBlank(message = "Username must not be blank")
  @UniqueUsername
  private String username;

  private String firstName;
  private String lastName;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email must not be blank")
  @UniqueEmail
  private String email;

  @NotBlank(message = "Password must not be blank")
  private String password;

  @NotNull(message = "Role must not be null")
  private USER_ROLE role;
  private ContactInfo contactInfo;

}
