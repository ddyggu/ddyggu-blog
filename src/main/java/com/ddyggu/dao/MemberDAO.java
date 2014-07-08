package com.ddyggu.dao;

import com.ddyggu.bean.Member;

public  interface MemberDAO {
	
  public  Member getMemberById(String paramString);

  public  Member getMemberByEmail(String paramString);

  public  Member getMemberByNickName(String paramString);

  public  String getGradeByPoint(int paramInt);

  public  void memInsert(Member paramMember);

  public  void memUpdate(Member paramMember);

  public  void deleteAllMembers();

  public  void deleteOneMember(Member paramMember);
  
}