package com.ddyggu.validator;

import com.ddyggu.bean.Member;
import com.ddyggu.service.MemberService;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MemberValidator
  implements Validator
{

  @Resource(name="memberService")
  private MemberService memberService;

  public boolean supports(Class<?> cl)
  {
    return Member.class.isAssignableFrom(cl);
  }

  public void validate(Object target, Errors errors)
  {
    Member member = (Member)target;
    String pwd = member.getPwd();
    String phone = member.getPhone();
    String name = member.getActualName();
    String address = member.getAddress();
    Integer point = Integer.valueOf(member.getPoint());

    if (point.intValue() == 0)
    {
      Map errorMessage = this.memberService.ValidateId(member);
      if (errorMessage.size() > 0) {
        Iterator errorKey = errorMessage.keySet().iterator();
        errors.rejectValue("id", "member.id." + (String)errorKey.next());
      }

    }

    if ((pwd == null) || (pwd.equals(""))) {
      errors.rejectValue("pwd", "member.pwd.required");
    }

    if (!Pattern.matches("(?=.*\\d)(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*[~`!@#$%\\^\\&\\*\\(\\)\\-\\_\\+\\=\\|\\\\\\{\\}\\[\\]\\:\\;\\<\\>\\,\\.\\?\\/\\'\"]).{8,20}", pwd)) {
      errors.rejectValue("pwd", "member.pwd.notValid");
    }

    Map NickErrorMessage = this.memberService.ValidateNickName(member);
    if (NickErrorMessage.size() > 0) {
      Iterator errorKey = NickErrorMessage.keySet().iterator();
      errors.rejectValue("nickName", "member.nickName." + (String)errorKey.next());
    }

    Map EmailErrorMessage = this.memberService.ValidateEmail(member);
    if (EmailErrorMessage.size() > 0) {
      Iterator errorKey = EmailErrorMessage.keySet().iterator();
      errors.rejectValue("email", "member.email." + (String)errorKey.next());
    }

    if ((!name.equals("")) && 
      (!Pattern.matches(".{1,20}", name))) {
      errors.rejectValue("actualName", "member.name.tooLong");
    }

    if ((!address.equals("")) && 
      (!Pattern.matches(".{1,50}", address))) {
      errors.rejectValue("address", "member.address.tooLong");
    }

    if ((!member.getPhone().equals("")) && 
      (!Pattern.matches("[\\d\\-]{1,15}", phone)))
      errors.rejectValue("phone", "member.phone.notNumber");
  }
}