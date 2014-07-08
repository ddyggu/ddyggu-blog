package com.ddyggu.controller;

import com.ddyggu.bean.LoginCheck;
import com.ddyggu.bean.Session;
import com.ddyggu.service.MemberService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes({"member", "session"})
public class LoginController
{

  @Autowired
  private MemberService memberService;

  @RequestMapping(value={"/loginCheck"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String memberInfo(@RequestParam String id, @RequestParam String pwd, @RequestParam(required=false) boolean saveId, HttpServletRequest request, HttpServletResponse response, Model model)
  {
    LoginCheck check = this.memberService.loginCheck(id, pwd);

    if (!check.isCheck()) {
      model.addAttribute("Check", check);
      return "member/login";
    }

    Session session = new Session(true);

    model.addAttribute("member", check.getCheckedMember());
    model.addAttribute("session", session);

    if (saveId) { response.addCookie(new Cookie("id", id));
    } else {
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          String cookieName = cookie.getName();
          if (cookieName.equals("id")) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
          }
        }
      }
    }
    return "redirect:/";
  }

  @RequestMapping({"/logout"})
  public String logout(SessionStatus status)
  {
    status.setComplete();
    return "redirect:/";
  }
}