package com.ddyggu.controller;

import com.ddyggu.bean.BBSContent;
import com.ddyggu.bean.BBSList;
import com.ddyggu.bean.BoardFile;
import com.ddyggu.bean.BoardList;
import com.ddyggu.bean.Comment;
import com.ddyggu.bean.EncodedFile;
import com.ddyggu.bean.Member;
import com.ddyggu.bean.Search;
import com.ddyggu.exception.NameNotFoundException;
import com.ddyggu.service.BoardService;
import com.ddyggu.util.StringUtility;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"member", "session", "boardList", "bbsContent"})
public class BoardController
{

  @Resource(name="transactionManager")
  private DataSourceTransactionManager txManager;

  @Resource(name="boardService")
  private BoardService boardService;
  
  @Resource(name="StringUtility")
  private StringUtility stringUtil;
  
  @RequestMapping("/boardWrite")
  public String boardWrite(@RequestParam String bbs, @RequestParam(required=false) boolean isReply, @RequestParam(required=false) Integer boardNum, @RequestParam(required=false) Integer pageNum, HttpSession session, Model model)
  {
    BBSContent bbsContent = this.boardService.getBBSContent(bbs, pageNum);
    String viewURL = "Style/" + bbsContent.getBbsStyle() + "/boardWrite";

    if (isReply) {
      bbsContent.setReply(true);
      model.addAttribute("bbsContent", bbsContent);
      model.addAttribute("boardList", this.boardService.getBoard(bbs, boardNum.intValue()));
      return viewURL;
    }

    model.addAttribute("bbsContent", bbsContent);
    model.addAttribute("boardList", new BoardList());
    return viewURL;
  }

  @RequestMapping(value="/writeCheck", method=RequestMethod.POST)
  public String boardWrite(@ModelAttribute @Valid BoardList boardList, BindingResult result, @ModelAttribute("bbsContent") BBSContent bbsContent, @ModelAttribute Member member, @RequestParam(required=false) String content, @RequestParam String bbs, HttpServletRequest request, @RequestParam(required=false, value="attach_image_num") String fileNums, @RequestParam(required=false, value="attach_file") String attachFile, @RequestParam(required=false) Integer OriginalNum, RedirectAttributes redirectAttributes, Model model)
  {
    if (result.hasErrors()) {
      model.addAttribute("bbsContent", bbsContent);
      model.addAttribute("preservedContent", content);
      model.addAttribute("preservedTitle", boardList.getBoardTitle());
      return "Style/" + bbsContent.getBbsStyle() + "/boardWrite";
    }

    boardList.setBoardWriter(member.getNickName());
    boardList.setMemberNum(member.getMemberNum());
    boardList.setBoardContents(content);

    if (OriginalNum != null) {
      this.boardService.insertReply(bbs, boardList, OriginalNum.intValue(), fileNums);
      redirectAttributes.addFlashAttribute("message", "write success");
      return "redirect:/boardlist?bbs=" + bbs;
    }

    this.boardService.insertBoard(bbs, boardList, fileNums);
    redirectAttributes.addFlashAttribute("message", "write success");

    return "redirect:/boardlist?bbs=" + bbs;
  }

  @RequestMapping("/boardPass")
  public String boardModify(@RequestParam int boardNum, @RequestParam int pageNum, 
		  								@RequestParam String bbs, @RequestParam String action, 
		  								@ModelAttribute Member member, Model model) {
	  
    BoardList boardList = this.boardService.getBoard(bbs, boardNum);
    BBSContent bbsContent = this.boardService.getBBSContent(bbs, new Object[] { Integer.valueOf(pageNum) });

    model.addAttribute("bbsContent", bbsContent);
    model.addAttribute("boardList", boardList);
    model.addAttribute("action", action);

    return "Style/" + bbsContent.getBbsStyle() + "/boardPass";
  }

  @RequestMapping(value="/boardPass", method=RequestMethod.POST)
  public String boardModify(@ModelAttribute Member member, @RequestParam int boardNum, 
		  								 @RequestParam int pageNum, @RequestParam String action, 
		  								 @RequestParam String bbs, RedirectAttributes redirectAttributes, 
		  								 @RequestParam("password") String receivedPassword, Model model) {
    
	BBSContent bbsContent = boardService.getBBSContent(bbs, pageNum);
    BoardList boardList = boardService.getBoard(bbs, boardNum);
    String password = boardList.getBoardPass();
    
    if (!password.equals(receivedPassword)) {
      model.addAttribute("message", "비밀번호가 맞지 않습니다.");
      model.addAttribute("bbsContent", bbsContent);
      model.addAttribute("boardList", boardList);
      return "Style/" + bbsContent.getBbsStyle() + "/boardPass";
    }

    if (action.equals("delete")) {
    	boardService.deleteBoard(bbs, boardList);
        redirectAttributes.addFlashAttribute("message", "delete success");
        return "redirect:/boardlist?bbs=" + bbs + "&pageNum=" + pageNum;
    }

    model.addAttribute("bbsContent", bbsContent);
    model.addAttribute("boardList", boardList);
    return "Style/" + bbsContent.getBbsStyle() + "/boardModify";
  }

  @RequestMapping(value="/modifyCheck", method=RequestMethod.POST)
  public String boardModify(@ModelAttribute @Valid BoardList boardList, BindingResult result, 
		  								 @ModelAttribute Member member, @RequestParam(required=false) String content, 
		  								 @ModelAttribute("bbsContent") BBSContent bbsContent, 
		  								 @RequestParam int pageNum, @RequestParam String bbs, 
		  								 @RequestParam(required=false, value="attach_image_num") String fileNums, 
		  								 @RequestParam(required=false, value="attach_file") String attachFile, 
		  								 HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
	  
    if (result.hasErrors()) {
      model.addAttribute("bbsContent", bbsContent);
      model.addAttribute("boardList", boardList);
      return "Style/" + bbsContent.getBbsStyle() + "/boardModify";
    }if ((content == null) || (content.equals("<p><br></p>"))) {
      model.addAttribute("message", "내용을 입력하여 주십시오");
      model.addAttribute("bbsContent", bbsContent);
      model.addAttribute("boardList", boardList);
      return "Style/" + bbsContent.getBbsStyle() + "/boardModify";
    }

    boardList.setBoardContents(content);
    boardService.updateBoard(bbs, boardList, fileNums);
    redirectAttributes.addFlashAttribute("message", "modify success");
    return "redirect:/boardlist?bbs=" + bbs + "&pageNum=" + pageNum;
  }

  @RequestMapping(value="/fileWrite", method=RequestMethod.POST, produces="text/plain;charset=utf-8")
  @ResponseBody
  public Map<String, Object> fileWrite(MultipartHttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    Iterator<String> iterator = request.getFileNames();
    String bbs = (String)request.getAttribute("bbs");

    for (int i = 1; 
      iterator.hasNext(); 
      i++)
    {
      MultipartFile file = request.getFile((String)iterator.next());
      String uploadPath = stringUtil.getAbsoluteUploadPath();
      String fileName = file.getOriginalFilename();
      String encodedName = stringUtil.encodedFileName(fileName);
      String encodedURL = stringUtil.getRelativePath() + encodedName;
      long fileSize = file.getSize();

      if (stringUtil.isIllegalExtension(fileName)) {
        response.setStatus(400);
        map.put("Exception", "허용되는 확장자가 아닙니다.");
      }

      EncodedFile encodedFile = null;
      try {
        encodedFile = boardService.insertFilenameOnDB(bbs, fileName, fileSize, encodedName, encodedURL);
        this.boardService.locateFileOnServer(file, uploadPath, encodedName);
      }
      catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        response.setStatus(400);
        map.put("Exception", "서버 오류가 발생하였습니다. 불편을 드려 죄송합니다. 문제가 지속될 경우 관리자에게 연락하여 통보하여 주십시오");
        return map;
      } catch (UncategorizedSQLException se) {
        se.printStackTrace();
        response.setStatus(400);
        map.put("Exception", "서버 오류가 발생하였습니다. 불편을 드려 죄송합니다. 문제가 지속될 경우 관리자에게 연락하여 통보하여 주십시오");
        return map;
      }
      map.put("encodedFile" + i, encodedFile);
    }
    return map;
  }
  
  @RequestMapping(value="/fileRead", method=RequestMethod.GET, produces="text/plain;charset=utf-8")
  @ResponseBody
  public Map<String, Object> fileRead(@RequestParam String bbs, @RequestParam int boardNum, HttpServletResponse response) {
    List<BoardFile> fileList = boardService.getBoardFileList(bbs, boardNum);
    Map<String, Object> fileMap = new LinkedHashMap<String,Object>();
    int i = 1;
    for (Iterator<BoardFile> localIterator = fileList.iterator(); localIterator.hasNext(); i++)
    {
      BoardFile boardfile = (BoardFile)localIterator.next();
      EncodedFile encodedFile = new EncodedFile();
      String encodedURL = boardfile.getEncodedUrl();

      encodedFile.setFileNum(boardfile.getFileNum());
      encodedFile.setImageurl(encodedURL);
      encodedFile.setFilename(boardfile.getFileName());
      encodedFile.setFilesize(boardfile.getFileSize());
      encodedFile.setOriginalurl(encodedURL);
      encodedFile.setThumburl(encodedURL);

      fileMap.put("data" + i, encodedFile);
    }

    return fileMap;
  }
  
  @RequestMapping(value="/commentWrite", method=RequestMethod.POST, produces="text/plain;charset=utf-8")
  @ResponseBody
  public Comment commentWrite(@RequestBody Comment comment) { 
	boardService.updateCommentCountPlus(comment);
    return boardService.insertAndGetComment(comment); 
   }

  @RequestMapping(value="/commentDelete", method=RequestMethod.POST)
  public @ResponseBody boolean commentDelete(@RequestBody Comment comment) {
    boardService.updateCommentCountMinus(comment);
    boardService.deleteComment(comment.getCommentNum());
    return true;
  }

  @RequestMapping("/boardlist")
  public String boardList(@RequestParam String bbs, @RequestParam(required=false) Integer pageNum, 
		  							@RequestParam(required=false) String searchType, 
		  							@RequestParam(required=false) String keyword, Model model) {
   
	if (pageNum == null) pageNum = Integer.valueOf(1);

    Search search = new Search(searchType, keyword);
    BBSList bbsList = boardService.getBBSList(bbs, pageNum, search);

    model.addAttribute("bbsList", bbsList);

    return "Style/" + bbsList.getBbsStyle() + "/boardList";
  }

  @RequestMapping({"/contents"})
  public String boardContents(@RequestParam String bbs, @RequestParam(required=false) Integer pageNum, 
		  									 @RequestParam int boardNum, @RequestParam(required=false) String searchType, 
		  									 @RequestParam(required=false) String keyword, Model model) {
	  
    if (pageNum == null) pageNum = Integer.valueOf(1);

    Search search = new Search(searchType, keyword);
    BBSContent bbsContent = boardService.getBBSContent(bbs, new Object[] { pageNum, Integer.valueOf(boardNum), search });

    model.addAttribute("bbsContent", bbsContent);

    return "Style/" + bbsContent.getBbsStyle() + "/boardContents";
    
  }

  @RequestMapping({"/boardlistAjax"})
  public String boardListAjax(HttpServletRequest request, Model model)
  {
    System.out.println("진입테스트" + request.getParameter("pageNum"));

    return null;
  }

  @RequestMapping({"/replyInsert"})
  public String rInsert(HttpServletRequest request, Model model)
  {
    return null;
  }

  @ExceptionHandler({HttpSessionRequiredException.class})
  public ModelAndView handleHttpSessionRequiredException(HttpSessionRequiredException e) {
    e.printStackTrace();
    return new ModelAndView("/error").addObject("message", "Http Session이 만료되었습니다. 잘못된 접근을 시도하신 경우 해당 문제가 발생할 수 있으며,<br/> 문제가 지속될 경우 관리자에게 연락하여 주십시오");
  }

  @ExceptionHandler({UncategorizedSQLException.class})
  public ModelAndView handleUncategorizedSQLException(UncategorizedSQLException e) {
    e.printStackTrace();
    return new ModelAndView("/error").addObject("message", "데이터베이스 서버 오류가 발생하였습니다.<br/> 문제가 지속될 경우 관리자에게 연락하여 주십시오");
  }
  @ExceptionHandler({NameNotFoundException.class})
  public ModelAndView NameNotFoundException(NameNotFoundException e) {
    e.printStackTrace();
    return new ModelAndView("/error").addObject("message", "해당 게시판을 찾을 수 없습니다. 잘못된 접근을 시도하신 경우 해당 문제가 발생할 수 있으며,<br/> 문제가 지속될 경우 관리자에게 연락하여 주십시오");
  }
}