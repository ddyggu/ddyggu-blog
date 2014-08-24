package com.ddyggu.controller;

import com.ddyggu.service.BoardService;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"member"})
public class MenuController
{

  @Resource(name="boardService")
  private BoardService boardService;

  @RequestMapping({"/"})
  public String home(Model model)
  {
    model.addAttribute("BoardPreview", this.boardService.getPreviewOfBoardList("board"));
    model.addAttribute("JavaPreview", this.boardService.getPreviewOfBoardList("java"));
    model.addAttribute("JsPreview", this.boardService.getPreviewOfBoardList("javascript"));
    model.addAttribute("OraclePreview", this.boardService.getPreviewOfBoardList("oracle"));
    return "home";
  }
  @RequestMapping({"/login"})
  public String login() { return "member/login"; }
  @RequestMapping({"/sitemap"})
  public String sitemap() { return "/sitemap"; } 
  @RequestMapping({"/underCons"})
  public String underConstruct() { return "underCons"; }
  @RequestMapping({"/emailform"})
  public String emailform() { return "member/emailform"; } 
  @RequestMapping({"/Modify"})
  public String modify() { return "member/memModify"; }
  @RequestMapping({"/testfile"})
  public String testFile() { return "testFile"; }
  
  
  @ExceptionHandler({IllegalStateException.class})
  public ModelAndView IllegalStateExceptionHandler(IllegalStateException ex) {
    ex.printStackTrace();
    return new ModelAndView("/error").addObject("message", "IllegalStateException이 발생하였습니다.");
  }
}