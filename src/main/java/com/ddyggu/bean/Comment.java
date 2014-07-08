package com.ddyggu.bean;

import java.sql.Timestamp;

public class Comment
{
  private int boardNum;
  private int CommentNum;
  private String CommentContents;
  private Timestamp CommentDate;
  private String formattedDate;
  private int WriterNum;
  private String CommentWriter;
  private String bbsName;

  public int getBoardNum()
  {
    return this.boardNum;
  }
  public void setBoardNum(int boardNum) {
    this.boardNum = boardNum;
  }
  public int getCommentNum() {
    return this.CommentNum;
  }
  public void setCommentNum(int commentNum) {
    this.CommentNum = commentNum;
  }
  public String getCommentContents() {
    return this.CommentContents;
  }
  public void setCommentContents(String commentContents) {
    this.CommentContents = commentContents;
  }

  public String getCommentWriter() {
    return this.CommentWriter;
  }
  public void setCommentWriter(String commentWriter) {
    this.CommentWriter = commentWriter;
  }
  public String getBbsName() {
    return this.bbsName;
  }
  public void setBbsName(String bbsName) {
    this.bbsName = bbsName;
  }
  public Timestamp getCommentDate() {
    return this.CommentDate;
  }
  public void setCommentDate(Timestamp commentDate) {
    this.CommentDate = commentDate;
  }
  public String getFormattedDate() {
    return this.formattedDate;
  }
  public void setFormattedDate(String formattedDate) {
    this.formattedDate = formattedDate;
  }
  public int getWriterNum() {
	return WriterNum;
  }
  public void setWriterNum(int writerNum) {
	WriterNum = writerNum;
  }
}
