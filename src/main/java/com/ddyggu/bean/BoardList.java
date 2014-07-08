package com.ddyggu.bean;

import java.sql.Timestamp;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

public class BoardList
{
  private int boardNum;

  @NotEmpty(message="제목을 입력해 주십시오")
  @Pattern(regexp=".{0,100}", message="제목이 너무 길어서 저장할 수 없습니다.")
  private String boardTitle;
  private String boardContents;
  private int commentCount;
  private int memberNum;
  private String boardWriter;
  private Timestamp boardDate;
  private int boardViews;
  private int groupNum;
  private int step;
  private int level;

  @NotEmpty(message="비밀번호를 입력해 주십시오")
  @Pattern(regexp=".{0,20}", message="비밀번호는 20자 이내로 지정하여 주십시오")
  private String boardPass;
  private String formattedDate;

  public int getBoardNum()
  {
    return this.boardNum;
  }

  public void setBoardNum(int boardNum)
  {
    this.boardNum = boardNum;
  }

  public String getBoardTitle()
  {
    return this.boardTitle;
  }

  public void setBoardTitle(String boardTitle)
  {
    this.boardTitle = boardTitle;
  }

  public String getBoardContents()
  {
    return this.boardContents;
  }

  public void setBoardContents(String boardContents)
  {
    this.boardContents = boardContents;
  }

  public String getBoardWriter()
  {
    return this.boardWriter;
  }

  public void setBoardWriter(String boardWriter)
  {
    this.boardWriter = boardWriter;
  }

  public Timestamp getBoardDate()
  {
    return this.boardDate;
  }

  public void setBoardDate(Timestamp boardDate)
  {
    this.boardDate = boardDate;
  }

  public int getBoardViews()
  {
    return this.boardViews;
  }

  public void setBoardViews(int boardViews)
  {
    this.boardViews = boardViews;
  }

  public int getGroupNum()
  {
    return this.groupNum;
  }

  public void setGroupNum(int groupNum)
  {
    this.groupNum = groupNum;
  }

  public int getStep()
  {
    return this.step;
  }

  public void setStep(int step)
  {
    this.step = step;
  }

  public int getLevel()
  {
    return this.level;
  }

  public void setLevel(int level)
  {
    this.level = level;
  }

  public String getBoardPass()
  {
    return this.boardPass;
  }

  public void setBoardPass(String boardPass)
  {
    this.boardPass = boardPass;
  }

  public int getCommentCount()
  {
    return this.commentCount;
  }

  public void setCommentCount(int commentCount)
  {
    this.commentCount = commentCount;
  }

  public String getFormattedDate()
  {
    return this.formattedDate;
  }

  public void setFormattedDate(String formattedDate)
  {
    this.formattedDate = formattedDate;
  }

  public int getMemberNum()
  {
    return this.memberNum;
  }

  public void setMemberNum(int memberNum)
  {
    this.memberNum = memberNum;
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("boardNum : "); sb.append(getBoardNum()); sb.append("\n");
    sb.append("boardTitle : "); sb.append(getBoardTitle()); sb.append("\n");
    sb.append("boardContents : "); sb.append(getBoardContents()); sb.append("\n");
    sb.append("commentCount : "); sb.append(this.commentCount); sb.append("\n");
    sb.append("boardWriter : "); sb.append(getBoardWriter()); sb.append("\n");
    sb.append("boardDate : "); sb.append(getBoardDate()); sb.append("\n");
    sb.append("boardViews : "); sb.append(getBoardViews()); sb.append("\n");
    sb.append("gorupNum : "); sb.append(getGroupNum()); sb.append("\n");
    sb.append("step : "); sb.append(getStep()); sb.append("\n");
    sb.append("level : "); sb.append(getLevel()); sb.append("\n");
    sb.append("boardPass : "); sb.append(getBoardPass()); sb.append("\n");
    return sb.toString();
  }
}