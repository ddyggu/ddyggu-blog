package com.ddyggu.interceptor;

import com.ddyggu.bean.LoginCheck;
import com.ddyggu.bean.Member;
import com.ddyggu.bean.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginCheckInterceptor
  implements HandlerInterceptor
{
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    Member member = (Member)request.getSession().getAttribute("member");
    Session customSession = (Session)request.getSession().getAttribute("session");
    if ((member == null) || (customSession == null)) {
      LoginCheck check = new LoginCheck();
      check.setMessage("로그인이 필요한 서비스입니다.");
      request.setAttribute("Check", check);
      request.getRequestDispatcher("/login").forward(request, response);
    }
    return true;
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception
  {
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    throws Exception
  {
  }
}