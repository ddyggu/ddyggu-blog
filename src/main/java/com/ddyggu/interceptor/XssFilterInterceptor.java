package com.ddyggu.interceptor;

import com.nhncorp.lucy.security.xss.XssFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class XssFilterInterceptor
  implements HandlerInterceptor
{
  private static final XssFilter filter = XssFilter.getInstance("lucy-xss-superset.xml");

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    return true;
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception
  {
    modelAndView.addObject("filter", filter);
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    throws Exception
  {
  }
}