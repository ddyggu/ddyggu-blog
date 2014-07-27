package com.ddyggu.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.nhncorp.lucy.security.xss.XssFilter;

public class xssfilterTest {

	@Test
	public void test() throws Exception {
		XssFilter filter = XssFilter.getInstance("lucy-xss-superset.xml");
		String dirty = "<span style='font-size: 9pt;'><p><br></p>" +
							"<p><br></p><span style='font-family: Gulim,굴림,AppleGothic,sans-serif; font-size: 11pt;'><p><br></p>" +
							"<p><br></p><span style='font-family: Gulim,굴림,AppleGothic,sans-serif; font-size: 9pt;'><p><br></p><strong><p><br></p>" +
							"<p><br></p></strong></span></span></span><p><br></p>";
		String clean = filter.doFilter(dirty);
		System.out.println(clean);
		
		System.out.println("---------------------------------------------------------------");
		
		String dirty2 = "<span style='font-size: 9pt;'><p></span>";
		String clean2 = filter.doFilter(dirty2);
		System.out.println(clean2);
		
		System.out.println("---------------------------------------------------------------");
		
		String dirty3 = "<strong><p></strong>";
		String clean3 = filter.doFilter(dirty3);
		System.out.println(clean3);
		
	}

}
