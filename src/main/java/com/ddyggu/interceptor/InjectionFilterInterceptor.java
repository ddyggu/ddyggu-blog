package com.ddyggu.interceptor;

import com.ddyggu.util.StringUtility;
import java.io.PrintStream;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class InjectionFilterInterceptor
  implements HandlerInterceptor
{

  @Resource(name="StringUtility")
  private StringUtility stringUtil;

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    String bbs = request.getParameter("bbs");
    boolean isValid = this.stringUtil.validTableName(bbs);
    if (isValid) {
      return true;
    }
    System.out.println("SQL Injection Detected");
    request.setAttribute("message", "Sql Injection Detected");
    request.getRequestDispatcher("/").forward(request, response);
    return false;
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
