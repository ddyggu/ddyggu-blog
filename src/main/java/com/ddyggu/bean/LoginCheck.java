package com.ddyggu.bean;

public class LoginCheck
{
  private boolean isCheck;
  private Member checkedMember;
  private String message;

  public boolean isCheck()
  {
    return this.isCheck;
  }
  public void setCheck(boolean isCheck) {
    this.isCheck = isCheck;
  }
  public String getMessage() {
    return this.message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public Member getCheckedMember() {
    return this.checkedMember;
  }
  public void setCheckedMember(Member checkedMember) {
    this.checkedMember = checkedMember;
  }
}