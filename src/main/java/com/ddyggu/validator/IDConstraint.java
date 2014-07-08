package com.ddyggu.validator;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

@NotEmpty(message="id를 입력해 주십시오")
@Pattern(regexp="[a-zA-Z0-9]{6,20}", message="영문과 숫자를 조합 6~20자를 입력해주십시오")
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy={IDConstraintValidator.class})
public @interface IDConstraint
{
  public abstract String message();

  public abstract Class<?>[] groups();

  public abstract Class<?>[] payload();
}