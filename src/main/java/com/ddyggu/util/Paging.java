package com.ddyggu.util;

import com.ddyggu.bean.BBSList;
import com.ddyggu.bean.Search;
import org.apache.log4j.Logger;

public class Paging
{
  private Logger logger = Logger.getLogger(getClass());
  private int maxNum;
  private int pageNum;
  private int limitCount;
  private int pageCount;
  private String bbs;
  private String searchType;
  private String keyword;

  public Paging(BBSList bbsList) {
    this.maxNum = bbsList.getMaxCount();
    this.pageNum = bbsList.getPageNum();
    this.limitCount = bbsList.getLimitCount();
    this.pageCount = bbsList.getPageCount();
    this.bbs = bbsList.getBbsName();

    Search search = bbsList.getSearch();

     if (search != null) {
    	this.searchType = search.getSearchType();
      	this.keyword = search.getKeyword();
    }
  }

  public String makePageGroup()
  {
    int totalPage = maxNum % limitCount > 0 ? maxNum / limitCount + 1 : maxNum / limitCount;

    int currentGroup = pageNum % pageCount > 0 ? pageNum / pageCount + 1 : pageNum / pageCount;
    
    return makeHtml(currentGroup, totalPage, bbs);
  }

  private String makeHtml(int currentGroup, int totalPage, String bbs)
  {
    StringBuffer sb = new StringBuffer();

    int start = currentGroup * pageCount - (pageCount - 1);

    int end = currentGroup * pageCount >= totalPage ? totalPage : currentGroup * pageCount;

    if (start != 1)
    {
      if (this.keyword == null) {
        sb.append("<a href='/boardlist?bbs=" + bbs + "&pageNum=" + (start - 1) + "' class='previous'>");
      }
      else {
        sb.append("<a href='/boardlist?bbs=" + bbs + "&pageNum=" + (start - 1) + "&searchType=" + this.searchType + "&keyword=" + this.keyword + "' class='previous'>");
      }
      sb.append("이전");
      sb.append("</a>");
    }

    for (int i = start; i <= end; i++) {
      if (this.pageNum != i)
      {
        if (this.keyword == null) {
          sb.append("<a href='/boardlist?bbs=" + bbs + "&pageNum=" + i + "'>");
        }
        else {
          sb.append("<a href='/boardlist?bbs=" + bbs + "&pageNum=" + i + "&searchType=" + this.searchType + "&keyword=" + this.keyword + "'>");
        }
        sb.append(i);
        sb.append("</a>");
      }
      else {
        sb.append("<span style='color:#1aabff;'>");
        sb.append(i);
        sb.append("</span>");
      }

      if ((i == end) && (i < totalPage))
      {
        if (this.keyword == null)
          sb.append("<a href='/boardlist?bbs=" + bbs + "&pageNum=" + (end + 1) + "' class='next'>");
        else {
          sb.append("<a href='/boardlist?bbs=" + bbs + "&pageNum=" + (end + 1) + "&searchType=" + this.searchType + "keyword=" + this.keyword + "' class='next'>");
        }
        sb.append("다음");
        sb.append("</a>");
      }
    }

    return sb.toString();
  }
}