package com.ddyggu.util.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ddyggu.bean.BBSList;
import com.ddyggu.util.Paging;

public class pagingTest {

	private BBSList bbsList = new BBSList();
	
	@Before
	public void prepareTest() {
		bbsList.setBbsName("board");
		bbsList.setBbsStyle("orange");
		bbsList.setLimitCount(15); // 한 페이지당 보여줄 게시물 개수
		bbsList.setPageCount(10); // 이동 가능한 페이지그룹 개수 (페이지 네비게이션)
		bbsList.setMaxCount(165);  // 전체 게시물 개수
		bbsList.setPageNum(3);   // 요청받은 페이지 번호
	}
	
	@Test
	public void test() {
		Paging paging = new Paging(bbsList);
		
		System.out.println(paging.makePageGroup());
	}

}
