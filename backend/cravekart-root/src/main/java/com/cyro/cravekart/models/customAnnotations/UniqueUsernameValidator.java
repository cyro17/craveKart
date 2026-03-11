package com.cyro.cravekart.models.customAnnotations;

import com.cyro.cravekart.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements
    ConstraintValidator<UniqueUsername, String> {
  private final UserRepository userRepository;

  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    if(username == null) {
      return true;
    }
    return !userRepository.existsByUsername(username);
  }
}
