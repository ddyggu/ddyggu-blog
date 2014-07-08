package com.ddyggu.controller;

import com.ddyggu.bean.BBSList;
import com.ddyggu.bean.BBSLists;
import com.ddyggu.bean.LoginCheck;
import com.ddyggu.bean.SuperviseSession;
import com.ddyggu.service.BoardService;
import com.ddyggu.service.MemberService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"superviseSession", "member"})
public class AdminController
{

  @Resource(name="memberService")
  private MemberService memberService;

  @Resource(name="boardService")
  private BoardService boardService;

  @RequestMapping({"/admin/supervise"})
  public String admin() {
    return "/admin/login";
  }

  @RequestMapping(value="/admin/superviseCheck", method=RequestMethod.POST)
  public String superviseCheck(@RequestParam String id, @RequestParam String pwd, @RequestParam(required=false) boolean saveId, HttpServletRequest request, HttpServletResponse response, Model model)
  {
    LoginCheck check = this.memberService.supervisorCheck(id, pwd);

    if (!check.isCheck()) {
      model.addAttribute("Check", check);
      return "admin/login";
    }

    model.addAttribute("member", check.getCheckedMember());
    model.addAttribute("superviseSession", new SuperviseSession(true));

    return "/admin/supervise";
  }

  @RequestMapping({"/admin/boardSupervise"})
  public String boardSupervise(Model model)
  {
    List<BBSList> bbsList = this.boardService.getBBSList();
    model.addAttribute("bbsList", bbsList);

    return "/admin/boardSupervise";
  }
  @RequestMapping(value="/admin/boardSupervise", method=RequestMethod.POST)
  @ResponseBody
  public List<BBSList> boardSupervise(@RequestBody BBSLists bbsList, Model model) { this.boardService.updateBBSInformation(bbsList);
    return bbsList; 
  }

  @RequestMapping(value="/admin/addBoard", method=RequestMethod.POST)
  public String addBoard(BBSList bbsList, Model model) {
    this.boardService.insertBBSInformation(bbsList);
    this.boardService.createNewBBS(bbsList);

    List<BBSList> newbbsList = this.boardService.getBBSList();
    model.addAttribute("bbsList", newbbsList);

    return "/admin/boardSupervise";
  }
  
  @RequestMapping(value="/admin/deleteBoard", method=RequestMethod.POST)
  @ResponseBody
  public String deleteBoard(@RequestBody String names, Model model) { 
	
	String[] tempArray = names.split(",");
    
	for (String s : tempArray) {
      String bbsName = s.replaceAll("\\[|\\]|\"", "");

      this.boardService.deleteBBSInformation(bbsName);
      this.boardService.dropTable(bbsName);
    }

    return names;
  }
  
}
