package com.cyro.cravekart.models.customAnnotations;

import com.cyro.cravekart.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements
    ConstraintValidator<UniqueEmail, String> {
    private final UserRepository userRepository;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
    if(email == null)
      return false;
    return  !userRepository.existsByEmail(email);
  }
}
