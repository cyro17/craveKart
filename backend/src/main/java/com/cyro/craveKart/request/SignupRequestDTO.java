package com.cyro.cravekart.request;

import com.cyro.cravekart.models.enums.USER_ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequestDTO {

  @NotBlank(message = "Username must not be blank")
  private String username;

  private String firstName;
  private String lastName;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email must not be blank")
  private String email;

  @NotBlank(message = "Password must not be blank")
  private String password;

  @NotNull(message = "Role must not be null")
  private USER_ROLE role;

}
