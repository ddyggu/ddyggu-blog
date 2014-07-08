package com.ddyggu.controller;

import com.ddyggu.bean.Member;
import com.ddyggu.mail.MailService;
import com.ddyggu.service.MemberService;
import com.ddyggu.validator.MemberValidator;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"member", "session"})
public class JoinController
{
  private Logger logger = Logger.getLogger(getClass());

  @Resource(name="memberService")
  private MemberService memberService;

  @Autowired
  private MailService mailService;

  @Resource(name="memberValidator")
  private MemberValidator memberValidator;

  @InitBinder
  public void initBinder(WebDataBinder dataBinder) { Object targetObject = dataBinder.getTarget();
    if (targetObject != null) {
      Class<?> clazz = targetObject.getClass();
      if ((clazz != null) && 
        (this.memberValidator.supports(clazz)))
        dataBinder.setValidator(this.memberValidator);
    } }

  @RequestMapping(value={"/idCheck"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"text/plain;charset=utf-8"})
  @ResponseBody
  public Map<String, String> IdCheck(@RequestBody Member member, HttpServletResponse response)
  {
    Map<String, String> errorMessage = this.memberService.ValidateId(member);

    if (errorMessage.size() > 0) {
      response.setStatus(400);
      return errorMessage;
    }
    return null;
  }

  @RequestMapping(value={"/nickCheck"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"text/plain;charset=utf-8"})
  @ResponseBody
  public Map<String, String> nickCheck(@RequestBody Member member, HttpServletResponse response) {
    Map<String, String> errorMessage = this.memberService.ValidateNickName(member);

    if (errorMessage.size() > 0) {
      response.setStatus(400);
      return errorMessage;
    }
    return null;
  }

  @RequestMapping(value={"/emailCheck"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"text/plain;charset=utf-8"})
  @ResponseBody
  public Map<String, String> emailCheck(@RequestBody Member member, HttpServletResponse response) {
    Map<String, String> errorMessage = this.memberService.ValidateEmail(member);

    if (errorMessage.size() > 0) {
      response.setStatus(400);
      return errorMessage;
    }
    return null;
  }

  @RequestMapping(value={"/memJoin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String join(Model model)
  {
    Member member = new Member();
    model.addAttribute("member", member);
    return "member/join";
  }

  @RequestMapping(value={"/memJoin"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"text/plain;charset=utf-8"})
  public String memJoin(@ModelAttribute @Valid Member member, BindingResult result, Model model)
  {
    if (result.hasErrors()) {
      return "member/join";
    }
    member.setPoint(100);
    this.memberService.memInsert(member);

    member.setGrade(this.memberService.getGradeByPoint(member.getPoint()));
    model.addAttribute("member", member);
    return "member/joinConfirm";
  }

  @RequestMapping(value={"/memModify"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String memModify(@ModelAttribute @Valid Member member, BindingResult result, Model model)
  {
    if (result.hasErrors()) {
      return "member/memModify";
    }
    this.memberService.memUpdate(member);

    member.setGrade(this.memberService.getGradeByPoint(member.getPoint()));
    model.addAttribute("member", member);
    return "member/joinConfirm";
  }

  @RequestMapping(value={"/joinConfirm"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String joinConfirm(@ModelAttribute Member member, Model model) {
    model.addAttribute(member);
    return "member/joinConfirm";
  }
  @RequestMapping(value={"/forgot"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, String> forgot(@RequestParam String email, HttpServletResponse response, Model model) { this.logger.info("이메일 요청 : " + email);
    Map<String, String> errorMsg = new HashMap<String, String>();

    Member member = this.memberService.getMemberByEmail(email);
    if (member == null) {
      response.setStatus(400);
      errorMsg.put("Duplicate", "가입된 이메일 주소가 아닙니다.");
      return errorMsg;
    }

    this.mailService.sendMail("ddyggu's blog : 아이디 및 비밀번호 확인", 
      "<p>ID : " + member.getId() + "</p><br/><p>PASSWORD : " + member.getPwd() + "</p>", 
      "ddyggu@gmail.com", email);
    return null; }

  @RequestMapping({"/Deactivate"})
  public String Deactivate()
  {
    return "member/Deactivate";
  }

  @RequestMapping(value={"/Deactivate"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String Deactivate(@ModelAttribute Member member, @RequestParam String password, Model model, SessionStatus session) {
    if (!member.getPwd().equals(password)) {
      model.addAttribute("message", "비밀번호가 맞지 않습니다.");
      model.addAttribute("returnUrl", "/Deactivate");
      return "alertAndRedirect";
    }
    session.setComplete();
    this.memberService.deleteOneMember(member);
    model.addAttribute("message", "탈퇴가 완료되었습니다.");
    model.addAttribute("returnUrl", "/");
    return "alertAndRedirect";
  }

  @ExceptionHandler({HttpSessionRequiredException.class})
  public ModelAndView handleHttpSessionRequiredException(HttpSessionRequiredException e)
  {
    e.printStackTrace();
    return new ModelAndView("/error").addObject("message", "Http Session이 만료되었습니다.");
  }
  @ExceptionHandler({MailSendException.class})
  @ResponseBody
  public Map<String, String> MailFailedHandler(MailSendException e, HttpServletResponse response) { this.logger.error(e.getMessage());
    response.setStatus(400);
    Map<String, String> errorMsg = new HashMap<String, String>();
    errorMsg.put("InvalidAddress", "발송에 실패하였습니다. 주소를 다시 한번 확인하여 주십시오");
    return errorMsg; }

  @ExceptionHandler({DataAccessException.class})
  public ModelAndView DataAccessExceptionHandler(DataAccessException e)
  {
    e.printStackTrace();
    return new ModelAndView("/error").addObject("message", "DataAccessException이 발생하였습니다.");
  }

  @ExceptionHandler({IllegalStateException.class})
  public ModelAndView IllegalStateExceptionHandler(IllegalStateException e) {
    e.printStackTrace();
    return new ModelAndView("/error").addObject("message", "IllegalStateException이 발생하였습니다.");
  }
}