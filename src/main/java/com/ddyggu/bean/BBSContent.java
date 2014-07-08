package com.ddyggu.bean;

import java.util.List;

public class BBSContent {
	
  private int pageNum;
  private String bbsName;
  private String bbsStyle;
  private BoardList boardList;
  private Search search;
  private List<Comment> commentList;
  private boolean isReply;

  public int getPageNum() {
    return this.pageNum;
  }
  
  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }
  public String getBbsName() {
    return this.bbsName;
  }
  public void setBbsName(String bbsName) {
    this.bbsName = bbsName;
  }
  public String getBbsStyle() {
    return this.bbsStyle;
  }
  public void setBbsStyle(String bbsStyle) {
    this.bbsStyle = bbsStyle;
  }
  public boolean getIsReply() {
    return this.isReply;
  }
  public void setReply(boolean isReply) {
    this.isReply = isReply;
  }
  public BoardList getBoardList() {
    return this.boardList;
  }
  public void setBoardList(BoardList boardList) {
    this.boardList = boardList;
  }
  public List<Comment> getCommentList() {
    return this.commentList;
  }
  public void setComment(List<Comment> commentList) {
    this.commentList = commentList;
  }
  public Search getSearch() {
    return this.search;
  }
  public void setSearch(Search search) {
    this.search = search;
  }
}
