package com.ddyggu.controller.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import org.junit.Test;

import com.ddyggu.bean.BBSContent;
import com.ddyggu.controller.BoardController;
import com.ddyggu.testUtil.AbstractDispatcherServletTest;

public class boardControllerTest extends AbstractDispatcherServletTest {

	@Test
	public void boardControllertest() throws ServletException, IOException {
		
		// DispatcherServlet 초기화
		this.setRelativeLocations("mybatis-context.xml", "servlet-context.xml");
		// 테스트 대상 Controller
		this.setClasses(BoardController.class);
		
		// 테스트 대상 mapping url, parameters 
		initRequest("/contents")
			.addParameter("bbs", "board")
		    .addParameter("pageNum", "1")
		    .addParameter("boardNum", "1");	
		runService();	
		
		// model 값 확인
		Map<String,Object> model = getModelAndView().getModel();
		BBSContent bbsContent = (BBSContent)model.get("bbsContent");
		assertThat(bbsContent.getBoardList().getBoardTitle(), is("테스트"));
	}

}
