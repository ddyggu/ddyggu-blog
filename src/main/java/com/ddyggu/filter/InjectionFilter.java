package com.ddyggu.filter;

import com.ddyggu.util.StringUtility;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class InjectionFilter implements Filter {

  @Resource(name="StringUtility")
  private StringUtility stringUtil;

  public void init(FilterConfig filterConfig) throws ServletException {
	  
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    chain.doFilter(new InjectionRequestWrapper((HttpServletRequest)request), response);
    
  }

  public void destroy() {
  
  }
}