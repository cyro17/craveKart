package com.cyro.cravekart.models.customAnnotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.FIELD})
public @interface UniqueEmail {
  String message() default "email already exists";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
