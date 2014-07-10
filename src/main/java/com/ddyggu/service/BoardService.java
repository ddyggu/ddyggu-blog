package com.ddyggu.service;

import com.ddyggu.bean.BBSContent;
import com.ddyggu.bean.BBSList;
import com.ddyggu.bean.BBSLists;
import com.ddyggu.bean.BoardFile;
import com.ddyggu.bean.BoardList;
import com.ddyggu.bean.Comment;
import com.ddyggu.bean.EncodedFile;
import com.ddyggu.bean.Search;
import com.ddyggu.dao.BoardDAO;
import com.ddyggu.exception.NameNotFoundException;
import com.ddyggu.util.Paging;
import com.ddyggu.util.StringUtility;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.multipart.MultipartFile;

public class BoardService {
  
  @Resource(name="BoardDAO")
  private BoardDAO boardDao;
  
  @Resource(name="StringUtility")
  private StringUtility stringUtil;
  
  private static final String listFormat = "yyyy-MM-dd";
  private static final String commentFormat = "yyyy-MM-dd aaa hh:mm:ss";

  private Integer getLastInsert() {
    Integer boardNum = this.boardDao.getLastInsert();
    return boardNum;
  }

  private String getAbsolutePath() {
	  return stringUtil.getAbsoluteUploadPath();
  }
  
  private void updateBoardNum(String bbs, int boardNum, String fileNums) {
    Map<String, Object> paramMap = new HashMap<String, Object>();

    	if (fileNums != null) {
    		List<String> nums = new ArrayList<String>(Arrays.asList(fileNums.split(",")));
    		paramMap.put("bbs", bbs);
    		paramMap.put("boardNum", Integer.valueOf(boardNum));
    		paramMap.put("fileNums", nums);
    		this.boardDao.updateBoardNum(paramMap);
    	}
  }

  private BBSList getBoardList(BBSList bbsList)
  {
    String bbs = bbsList.getBbsName();
    int pageNum = bbsList.getPageNum();
    int LimitCount = bbsList.getLimitCount();
    Search searchKeyword = bbsList.getSearch();

    int LimitStart = (pageNum - 1) * LimitCount;
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbs);
    paramMap.put("LimitStart", Integer.valueOf(LimitStart));
    paramMap.put("LimitCount", Integer.valueOf(LimitCount));
    paramMap.put("searchKeyword", searchKeyword);
    List<BoardList> boardList = this.boardDao.getBoardList(paramMap);

    for (BoardList board : boardList)
    {
      String formattedDate = dateFormatChange(listFormat, board.getBoardDate());
      board.setFormattedDate(formattedDate);

      highLightingKeyword(board, searchKeyword);
    }

    bbsList.setMaxCount(getTotalRowCount(bbs, searchKeyword));
    Paging paging = new Paging(bbsList);

    bbsList.setPagingTag(paging.makePageGroup());
    bbsList.setBoardList(boardList);

    return bbsList;
  }

  private BBSList getBBSList(String bbs)
  {
    BBSList bbsList = this.boardDao.getBBSList(bbs);
    if (bbsList == null) {
      throw new NameNotFoundException(bbs);
    }
    return bbsList;
  }

  private BBSContent getBBSContent(String bbs)
  {
    BBSContent bbsContent = this.boardDao.getBBSContent(bbs);
    if (bbs == null) {
      throw new NameNotFoundException(bbs);
    }
    return bbsContent;
  }

  private void deleteOneBoard(String bbs, BoardList board) {
	    Map<String,Object> paramMap = new HashMap<String,Object>();
	    paramMap.put("bbs", bbs);
	    paramMap.put("boardList", board);
	    this.boardDao.deleteBoard(paramMap);
  }
  
  private void highLightingKeyword(BoardList boardList, Search searchKeyword)
  {
    if (searchKeyword != null) {
      String searchType = searchKeyword.getSearchType();
      String keyword = searchKeyword.getKeyword();

      if (searchType.equals("title")) {
        String title = boardList.getBoardTitle();
        String LightedTitle = title.replace(keyword, "<span style='color:#ff6000; font-weight:bold'>" + keyword + "</span>");
        boardList.setBoardTitle(LightedTitle);
      }

      if (searchType.equals("content")) {
        String Content = boardList.getBoardContents();
        String LightedContent = Content.replace(keyword, "<span style='color:#ff6000; font-weight:bold'>" + keyword + "</span>");
        boardList.setBoardContents(LightedContent);
      }

      if (searchType.equals("titleAndContent")) {
        String title = boardList.getBoardTitle();
        String Content = boardList.getBoardContents();

        String LightedTitle = title.replace(keyword, "<span style='color:#ff6000; font-weight:bold'>" + keyword + "</span>");
        String LightedContent = Content.replace(keyword, "<span style='color:#ff6000; font-weight:bold'>" + keyword + "</span>");

        boardList.setBoardTitle(LightedTitle);
        boardList.setBoardContents(LightedContent);
      }

      if (searchType.equals("writer")) {
        String writer = boardList.getBoardWriter();
        String LightedWriter = writer.replace(writer, "<span style='color:#ff6000; font-weight:bold'>" + writer + "</span>");
        boardList.setBoardWriter(LightedWriter);
      }
    }
  }

  public String dateFormatChange(String pattern, Timestamp writeDate)
  {
    SimpleDateFormat simpleFormat = new SimpleDateFormat(pattern);
    return simpleFormat.format(writeDate);
  }

  public void setBoardDAO(BoardDAO boardDao)
  {
    this.boardDao = boardDao;
  }

  public Integer getMaxGroupNum() {
    Integer groupNum = this.boardDao.getMaxGroupNum();
    if (groupNum == null) groupNum = Integer.valueOf(1);
    return groupNum;
  }

  public List<Comment> getCommentList(String bbs, int boardNum)
  {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbsName", bbs);
    paramMap.put("boardNum", Integer.valueOf(boardNum));

    List<Comment> commentList = this.boardDao.getCommentList(paramMap);

    for (Comment comment : commentList) {
      Timestamp commentDate = comment.getCommentDate();
      String formattedDate = dateFormatChange("yyyy-MM-dd aaa hh:mm:ss", commentDate);
      comment.setFormattedDate(formattedDate);
    }

    return commentList;
  }

  public int getTotalRowCount(String bbs, Search search)
  {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbs);
    paramMap.put("searchKeyword", search);
    return this.boardDao.getTotalRowCount(paramMap);
  }

  public List<Comment> getReply(int bnum) {
    return null;
  }

  public BoardList getBoard(String bbs, int boardNum)
  {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbs);
    paramMap.put("boardNum", Integer.valueOf(boardNum));
    BoardList boardList = this.boardDao.getBoard(paramMap);

    return boardList;
  }

  public List<BoardFile> getBoardFileList(String bbs, int boardNum)
  {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbs);
    paramMap.put("boardNum", Integer.valueOf(boardNum));
    return this.boardDao.getBoardFileList(paramMap);
  }

  public List<BBSList> getBBSList()
  {
    return this.boardDao.getBBSListAll();
  }

  public BBSList getBBSList(String bbs, Integer pageNum, Search search)
  {
    BBSList bbsList = getBBSList(bbs);

    if (search.getKeyword() != null) {
      bbsList.setSearch(search);
    }

    bbsList.setPageNum(pageNum.intValue());

    BBSList populatedModel = getBoardList(bbsList);

    return populatedModel;
  }

  public BBSList getPreviewOfBoardList(String bbs)
  {
    BBSList bbsList = getBBSList(bbs);
    List<BoardList> boardList = this.boardDao.getPreviewBoardList(bbsList);

    for (BoardList board : boardList) {
      String formattedDate = dateFormatChange(commentFormat, board.getBoardDate());
      board.setFormattedDate(formattedDate);
    }

    bbsList.setBoardList(boardList);
    return bbsList;
  }

  public BBSContent getBBSContent(String bbs, Object... parameters)
  {
    BBSContent bbsContent = getBBSContent(bbs);

    if (parameters.length >= 1) {
      bbsContent.setPageNum(((Integer)parameters[0]).intValue());
    }

    if (parameters.length >= 2) {
      BoardList boardList = getBoard(bbs, ((Integer)parameters[1]).intValue());
      List<Comment> commentList = getCommentList(bbs, ((Integer)parameters[1]).intValue());

      String formattedDate = dateFormatChange("yyyy-MM-dd aaa hh:mm:ss", boardList.getBoardDate());
      boardList.setFormattedDate(formattedDate);

      Search search = null;

      if (parameters.length == 3) {
        search = (Search)parameters[2];

        if (search.getKeyword() != null) {
          highLightingKeyword(boardList, search);
          bbsContent.setSearch(search);
        }
      }

      bbsContent.setBoardList(boardList);
      bbsContent.setComment(commentList);

      updateBoardViews(bbsContent, boardList);
    }

    return bbsContent;
  }

  public EncodedFile insertFilenameOnDB(String bbs, String fileName, long fileSize, String encodedFileName, String encodedURL)
    throws DataIntegrityViolationException, UncategorizedSQLException
  {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("fileName", fileName);
    paramMap.put("fileSize", Long.valueOf(fileSize));
    paramMap.put("encodedFileName", encodedFileName);
    paramMap.put("encodedURL", encodedURL);
    paramMap.put("bbs", bbs);
    this.boardDao.insertFileOnDB(paramMap);

    EncodedFile encodedFile = new EncodedFile();
    encodedFile.setImageurl(encodedURL);
    encodedFile.setFilename(fileName);
    encodedFile.setFilesize(fileSize);
    encodedFile.setOriginalurl(encodedURL);
    encodedFile.setThumburl(encodedURL);
    encodedFile.setFileNum(getLastInsert().intValue());
    return encodedFile;
  }

  public void insertReply(String bbs, BoardList boardList, int originalNum, String fileNums)
  {
    BoardList OriginBoard = getBoard(bbs, originalNum);
    boardList.setGroupNum(OriginBoard.getGroupNum());
    boardList.setLevel(OriginBoard.getLevel() + 1);
    boardList.setStep(OriginBoard.getStep() + 1);

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbs);
    paramMap.put("boardList", boardList);
    this.boardDao.insertReply(paramMap);
    int boardNum = getLastInsert().intValue();
    updateBoardNum(bbs, boardNum, fileNums);
  }

  public void insertBoard(String bbs, BoardList boardList, String fileNums)
  {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbs);
    paramMap.put("boardList", boardList);
    this.boardDao.insertBoard(paramMap);

    int boardNum = getLastInsert().intValue();
    paramMap.clear();
    paramMap.put("bbs", bbs);
    paramMap.put("boardNum", Integer.valueOf(boardNum));
    this.boardDao.updateGroupNum(paramMap);

    updateBoardNum(bbs, boardNum, fileNums);
  }

  public Comment insertAndGetComment(Comment comment) {
    boardDao.insertComment(comment);
    int commentNum = this.boardDao.getLastInsert().intValue();

    Comment InquiredComment = this.boardDao.getComment(commentNum);
    Timestamp time = InquiredComment.getCommentDate();
    String formattedDate = dateFormatChange(commentFormat, time);
    InquiredComment.setFormattedDate(formattedDate);

    return InquiredComment;
  }

  public void updateBoard(String bbs, BoardList boardList, String fileNums) {
	  Map<String, Object> paramMap = new HashMap<String, Object>();
	  paramMap.put("bbs", bbs);
	  paramMap.put("boardList", boardList);
	  this.boardDao.updateBoard(paramMap);
	  int boardNum = boardList.getBoardNum();
	  CheckIdleFileAndUpdate(bbs, boardNum, fileNums, getAbsolutePath());
  }

  public void insertBBSInformation(BBSList bbsList) {
	  this.boardDao.insertBBSinformation(bbsList);
  }

  public void updateBBSInformation(BBSLists bbsList)
  {
    for (BBSList bbs : bbsList)
      this.boardDao.updateBBSInformation(bbs);
  }

  public void updateBoardViews(BBSContent bbsContent, BoardList boardList)
  {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbsContent.getBbsName());
    paramMap.put("boardNum", Integer.valueOf(boardList.getBoardNum()));
    boardDao.updateBoardViews(paramMap);
  }

  public void updateCommentCountPlus(Comment comment)
  {
    boardDao.updateCommentCountPlus(comment);
  }

  public void updateCommentCountMinus(Comment comment)
  {
    boardDao.updateCommentCountMinus(comment);
  }

  public void deleteFileOnServer(String filePath, String fileName) {
    File uploadFile = new File(filePath, fileName);
    uploadFile.delete();
  }

  public void deleteBoard(String bbs, BoardList boardList)
  {  
    int level = boardList.getLevel();
    int step = boardList.getStep();

    if ((level == 0) && (step == 0)) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("bbs", bbs);
      paramMap.put("groupNum", Integer.valueOf(boardList.getBoardNum()));

      List<BoardList> needDeleteList = this.boardDao.getGroupBoardList(paramMap);

      for (BoardList board : needDeleteList) {
        deleteOneBoard(bbs, board);
        deleteFile(bbs, board.getBoardNum(), getAbsolutePath());
        deleteRelateComment(board.getBoardNum());
      }
    }
    else
    {
      deleteOneBoard(bbs, boardList);
      deleteFile(bbs, boardList.getBoardNum(), getAbsolutePath());
      deleteRelateComment(boardList.getBoardNum());
    }
  }

  public void deleteFile(String bbs, int boardNum, String contextPath)
  {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbs);
    paramMap.put("boardNum", Integer.valueOf(boardNum));
    List<BoardFile> boardFile = this.boardDao.getBoardFileList(paramMap);
    for (BoardFile file : boardFile) {
      deleteFileOnServer(contextPath, file.getEncodedFileName());
      this.boardDao.deleteBoardFile(file.getFileNum());
    }
  }

  public void deleteComment(int commentNum)
  {
    this.boardDao.deleteComment(commentNum);
  }

  public void deleteRelateComment(int boardNum)
  {
    this.boardDao.deleteRelateComment(boardNum);
  }

  public void deleteBBSInformation(String bbs)
  {
    BBSList bbsList = new BBSList();
    bbsList.setBbsName(bbs);
    this.boardDao.deleteBBSinformation(bbsList);
  }

  public void CheckIdleFileAndUpdate(String bbs, int boardNum, String fileNums, String contextPath) {
    
	Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bbs", bbs);
    paramMap.put("boardNum", Integer.valueOf(boardNum));

    if (fileNums == null) {
      List<BoardFile> FirstFileList = this.boardDao.getBoardFileList(paramMap);
      if (FirstFileList != null) {
        for (BoardFile file : FirstFileList) {
          deleteFileOnServer(contextPath, file.getEncodedFileName());
          this.boardDao.deleteBoardFile(file.getFileNum());
        }
      }
    }

    if (fileNums != null)
    {
      List<BoardFile> FirstFileList = this.boardDao.getBoardFileList(paramMap);

      List<Integer> newFileNums = new ArrayList<Integer>();

      String[] fileNum = fileNums.split(",");
      for (String num : fileNum) { 
    	  newFileNums.add(Integer.valueOf(Integer.parseInt(num)));
      }
      
      List<BoardFile> garbageFile = new ArrayList<BoardFile>();

      for (BoardFile file : FirstFileList) {
        if (!newFileNums.contains(Integer.valueOf(file.getFileNum()))) {
          garbageFile.add(file);
        }

      }

      if (garbageFile.size() > 0) {
        for (BoardFile file : garbageFile) {
          deleteFileOnServer(contextPath, file.getEncodedFileName());
          this.boardDao.deleteBoardFile(file.getFileNum());
        }

      }

      updateBoardNum(bbs, boardNum, fileNums);
    }
  }

  public void locateFileOnServer(MultipartFile file, String filePath, String fileName) {
    File uploadPath = new File(filePath, fileName);
    try {
      file.transferTo(uploadPath);
    } catch (IllegalStateException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public void createNewBBS(BBSList bbsList) {
	  this.boardDao.createNewBBS(bbsList);
  }

  public void dropTable(String bbs) {
    BBSList bbsList = new BBSList();
    bbsList.setBbsName(bbs);
    this.boardDao.dropTable(bbsList);
  }

}