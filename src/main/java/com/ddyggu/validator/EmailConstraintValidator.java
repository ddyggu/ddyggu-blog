package com.ddyggu.validator;

import com.ddyggu.bean.Member;
import com.ddyggu.service.MemberService;
import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator
  implements ConstraintValidator<EmailConstraint, String>
{

  @Resource(name="memberService")
  private MemberService memberService;

  public void initialize(EmailConstraint constraintAnnotation)
  {
  }

  public boolean isValid(String email, ConstraintValidatorContext context)
  {
    Member InquiredMember = this.memberService.getMemberByEmail(email);
    if (InquiredMember == null) {
      return true;
    }
    return false;
  }
}
