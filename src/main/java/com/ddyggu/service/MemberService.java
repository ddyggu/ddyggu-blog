package com.ddyggu.service;

import com.ddyggu.bean.LoginCheck;
import com.ddyggu.bean.Member;
import com.ddyggu.dao.MemberDAO;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MemberService
{
  private MemberDAO memberDao;

  public void setMemberDAO(MemberDAO memberDao)
  {
    this.memberDao = memberDao;
  }

  public Member getMemberById(String id) {
    return this.memberDao.getMemberById(id);
  }

  public void memInsert(Member mb) {
    this.memberDao.memInsert(mb);
  }

  public Member getMemberByEmail(String email) {
    return this.memberDao.getMemberByEmail(email);
  }

  public Member getMemberByNickName(String nickName) {
    return this.memberDao.getMemberByNickName(nickName);
  }

  public String getGradeByPoint(int point) {
    return this.memberDao.getGradeByPoint(point);
  }

  public void memUpdate(Member mb) {
    this.memberDao.memUpdate(mb);
  }

  public void deleteAllMembers() {
    throw new UnsupportedOperationException();
  }

  public void deleteOneMember(Member mb) {
    this.memberDao.deleteOneMember(mb);
  }

  public Map<String, String> ValidateId(Member member) {
    Map errorMessage = new HashMap();
    String id = member.getId();

    if (id.equals(""))
      errorMessage.put("required", "체크 할 아이디를 입력하십시오");
    else if (!Pattern.matches("(?=.*[a-z])(?!.*\\s)(?=.*\\d).{6,20}", id))
      errorMessage.put("notValid", "영문+숫자 6~20자리를 입력해 주십시오");
    else if (Pattern.matches("(?=.*[~`!@#$%\\^\\&\\*\\(\\)\\-\\_\\+\\=\\|\\\\\\{\\}\\[\\]\\:\\;\\<\\>\\,\\.\\?\\/\\'\"]).*", id)) {
      errorMessage.put("specialChar", "특수문자는 사용할 수 없습니다.");
    }

    Member newMember = new Member();
    try {
      newMember = this.memberDao.getMemberById(id);
    } catch (Exception e) {
      errorMessage.put("Exception", e.getClass().getSimpleName());
    }
    if (newMember != null) errorMessage.put("duplicate", "이미 사용중인 아이디입니다");

    return errorMessage;
  }

  public Map<String, String> ValidateNickName(Member member) {
    Map errorMessage = new HashMap();

    String nickName = member.getNickName();
    String id = member.getId();

    if (nickName.matches("\\s(.*)"))
      errorMessage.put("startWhiteSpace", "공백으로 시작할 수 없습니다.");
    else if (nickName.matches("(.*)\\s"))
      errorMessage.put("endWhiteSpace", "공백으로 끝날 수 없습니다.");
    else if (!nickName.matches(".{1,10}")) {
      errorMessage.put("notValid", "닉네임은 1~10자리 이내로 정해주십시오");
    }

    Member InquiredMember = getMemberByNickName(nickName);

    if (InquiredMember != null)
    {
      if (id != null)
      {
        if (!id.equals(InquiredMember.getId())) {
          errorMessage.put("duplicate", "이미 사용중인 닉네임입니다.");
        }
      }
      else if (id == null)
      {
        errorMessage.put("duplicate", "이미 사용중인 닉네임입니다.");
      }
    }

    return errorMessage;
  }

  public Map<String, String> ValidateEmail(Member member) {
    Map errorMessage = new HashMap();

    String id = member.getId();
    String email = member.getEmail();

    if (email.equals(""))
      errorMessage.put("required", "체크 할 이메일을 입력하십시오");
    else if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", email)) {
      errorMessage.put("notValid", "형식에 맞게 입력해 주십시오");
    }

    Member InquiredMember = getMemberByEmail(email);
    if (InquiredMember != null) {
      if (id != null) {
        if (!id.equals(InquiredMember.getId()))
          errorMessage.put("duplicate", "이미 사용중인 이메일입니다.");
      }
      else if (id == null) {
        errorMessage.put("duplicate", "이미 사용중인 이메일입니다.");
      }
    }

    return errorMessage;
  }

  public LoginCheck loginCheck(String id, String pwd) {
    LoginCheck check = new LoginCheck();
    Member member = getMemberById(id);

    if (!Pattern.matches("(?=.*[a-z])(?=.*\\d).{6,20}", id)) {
      check.setCheck(false);
      check.setMessage("아이디는 영문과 숫자를 조합 6~20자를 입력해 주십시오");
    } else if (member == null) {
      check.setCheck(false);
      check.setMessage("가입된 아이디가 아닙니다.");
    } else if (!member.getPwd().equals(pwd)) {
      check.setCheck(false);
      check.setMessage("패스워드가 일치하지 않습니다.");
    } else {
      check.setCheck(true);
      check.setCheckedMember(member);
    }

    return check;
  }

  public LoginCheck supervisorCheck(String id, String pwd) {
    LoginCheck check = loginCheck(id, pwd);

    if (!check.isCheck()) {
      return check;
    }

    Member member = check.getCheckedMember();
    String grade = member.getGrade();

    if (!grade.equals("SUPERVISOR")) {
      check.setCheck(false);
      check.setMessage("관리자 권한을 가진 아이디가 아닙니다.");
    }

    return check;
  }
}