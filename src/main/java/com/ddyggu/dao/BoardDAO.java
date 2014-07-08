package com.ddyggu.dao;

import com.ddyggu.bean.BBSContent;
import com.ddyggu.bean.BBSList;
import com.ddyggu.bean.BoardFile;
import com.ddyggu.bean.BoardList;
import com.ddyggu.bean.Comment;
import java.util.List;
import java.util.Map;

public interface BoardDAO
{
  public void createNewBBS(BBSList paramBBSList);

  public List<BoardList> getBoardList(Map<String, Object> paramMap);

  public List<BoardList> getSearchedBoardList(Map<String, Object> paramMap);

  public List<BoardList> getPreviewBoardList(BBSList paramBBSList);

  public int getTotalRowCount(Map<String, Object> paramMap);

  public Integer getMaxGroupNum();

  public Integer getLastInsert();

  public List<BBSList> getBBSListAll();

  public BBSList getBBSList(String paramString);

  public BBSContent getBBSContent(String paramString);

  public BoardList getBoard(Map<String, Object> paramMap);

  public Comment getComment(int paramInt);

  public List<Comment> getCommentList(Map<String, Object> paramMap);

  public List<BoardFile> getBoardFileList(Map<String, Object> paramMap);

  public List<BoardList> getGroupBoardList(Map<String, Object> paramMap);
  
  public void insertBoard(Map<String, Object> paramMap);

  public void insertBBSinformation(BBSList paramBBSList);

  public void insertReply(Map<String, Object> paramMap);

  public void insertFileOnDB(Map<String, Object> paramMap);

  public void insertComment(Comment paramComment);

  public void updateGroupNum(Map<String, Object> paramMap);

  public void updateBoardNum(Map<String, Object> paramMap);

  public void updateBoardViews(Map<String, Object> paramMap);

  public void updateBoard(Map<String, Object> paramMap);

  public void updateBBSInformation(BBSList paramBBSList);

  public void updateCommentCountPlus(Comment paramComment);

  public void updateCommentCountMinus(Comment paramComment);

  public void deleteComment(int paramInt);

  public void deleteRelateComment(int paramInt);

  public void deleteBBSinformation(BBSList paramBBSList);

  public void deleteBoard(Map<String, Object> paramMap);

  public void deleteBoardFile(int paramInt);

  public void dropTable(BBSList paramBBSList);
}