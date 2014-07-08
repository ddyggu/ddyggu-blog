package com.ddyggu.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

@NotEmpty(message="이메일을 입력하여 주십시오")
@Pattern(regexp="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", message="올바른 이메일 주소를 입력하여 주십시오")
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={EmailConstraintValidator.class})
public @interface EmailConstraint
{
  public abstract String message();

  public abstract String id();

  public abstract Class<?>[] groups();

  public abstract Class<?>[] payload();
}
