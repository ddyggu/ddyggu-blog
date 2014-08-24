package com.ddyggu.bean;

import java.util.List;

public class BBSList
{
  private int boardNum;
  private int pageNum;
  private int LimitCount;
  private int pageCount;
  private int previewCount;
  private int maxCount;
  private String bbsStyle;
  private String bbsName;
  private String replaceBbsName;
  private String pagingTag;
  private Search search;
  private List<BoardList> boardList;

  public BBSList() { }

  public BBSList(String bbsName, int limitCount, int pageCount, int previewCount, String bbsStyle)
  {
    this.bbsName = bbsName;
    this.LimitCount = limitCount;
    this.pageCount = pageCount;
    this.previewCount = previewCount;
    this.bbsStyle = bbsStyle;
  }

  public int getBoardNum() {
    return this.boardNum;
  }
  public void setBoardNum(int boardNum) {
    this.boardNum = boardNum;
  }
  public int getPageNum() {
    return this.pageNum;
  }
  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }
  public int getLimitCount() {
    return this.LimitCount;
  }
  public void setLimitCount(int limitCount) {
    this.LimitCount = limitCount;
  }

  public int getPageCount() {
    return this.pageCount;
  }
  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }
  public String getBbsName() {
    return this.bbsName;
  }
  public void setBbsName(String bbsName) {
    this.bbsName = bbsName;
  }
  public String getPagingTag() {
    return this.pagingTag;
  }
  public void setPagingTag(String pagingTag) {
    this.pagingTag = pagingTag;
  }
  public int getMaxCount() {
    return this.maxCount;
  }
  public void setMaxCount(int maxCount) {
    this.maxCount = maxCount;
  }
  public List<BoardList> getBoardList() {
    return this.boardList;
  }
  public void setBoardList(List<BoardList> boardList) {
    this.boardList = boardList;
  }
  public String getBbsStyle() {
    return this.bbsStyle;
  }
  public void setBbsStyle(String bbsStyle) {
    this.bbsStyle = bbsStyle;
  }
  public int getPreviewCount() {
    return this.previewCount;
  }
  public void setPreviewCount(int previewCount) {
    this.previewCount = previewCount;
  }
  public Search getSearch() {
    return this.search;
  }
  public void setSearch(Search search) {
    this.search = search;
  }

  public String getReplaceBbsName() {
    return this.replaceBbsName;
  }

  public void setReplaceBbsName(String replaceBbsName) {
    this.replaceBbsName = replaceBbsName;
  }
}