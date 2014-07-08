package com.ddyggu.validator;

import com.ddyggu.bean.Member;
import com.ddyggu.service.MemberService;
import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IDConstraintValidator
  implements ConstraintValidator<IDConstraint, String>
{

  @Resource(name="memberService")
  private MemberService memberService;

  public void initialize(IDConstraint constraintAnnotation)
  {
  }

  public boolean isValid(String id, ConstraintValidatorContext context)
  {
    Member member = this.memberService.getMemberById(id);
    if (member != null) {
      return false;
    }
    return true;
  }
}