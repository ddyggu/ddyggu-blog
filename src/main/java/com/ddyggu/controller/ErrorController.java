package com.ddyggu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"member", "session"})
public class ErrorController
{
  @RequestMapping(value={"/error"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String error()
  {
    return "/error";
  }

  @RequestMapping(value={"/error"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String error(@RequestParam String message) {
    return "/error";
  }
}
