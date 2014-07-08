package com.ddyggu.interceptor;

import com.ddyggu.bean.BoardList;
import com.ddyggu.bean.Member;
import com.ddyggu.bean.Session;
import com.ddyggu.service.BoardService;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class WriterCheckInterceptor
  implements HandlerInterceptor
{

  @Autowired
  private BoardService boardService;

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    Member member = (Member)request.getSession().getAttribute("member");
    Session session = (Session)request.getSession().getAttribute("session");

    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    String bbs = request.getParameter("bbs");

    BoardList boardList = this.boardService.getBoard(bbs, boardNum);

    if ((member == null) || (session == null)) {
      request.setAttribute("message", "잘못된 접근입니다.");
      request.getRequestDispatcher("/").forward(request, response);
      return false;
    }

    int writerNum = boardList.getMemberNum();
    int memberNum = member.getMemberNum();

    if (writerNum != memberNum) {
      request.setAttribute("message", "잘못된 접근입니다.");
      request.getRequestDispatcher("/").forward(request, response);
      return false;
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