package com.ddyggu.interceptor;

import com.ddyggu.bean.LoginCheck;
import com.ddyggu.bean.SuperviseSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AdminCheckInterceptor implements HandlerInterceptor {
	
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
  {
   
	  SuperviseSession supervise = (SuperviseSession)request.getSession().getAttribute("superviseSession");

    if (supervise == null) {
      LoginCheck check = new LoginCheck();
      check.setMessage("관리자 세션이 확인되지 않습니다.");
      request.setAttribute("Check", check);
      request.getRequestDispatcher("/admin/supervise").forward(request, response);
      return false;
    }

    if (!supervise.isMaintainSession()) {
      LoginCheck check = new LoginCheck();
      check.setMessage("관리자 세션이 확인되지 않습니다.");
      request.setAttribute("Check", check);
      request.getRequestDispatcher("/admin/supervise").forward(request, response);
      return false;
    }

    return true;
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception {
  
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    throws Exception {
  
  }

}
