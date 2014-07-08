package com.ddyggu.bean;

public class Member
{
  private int memberNum;
  private String id;
  private String pwd;
  private String email;
  private String nickName;
  private String actualName;
  private String grade;
  private Integer BirthYear;
  private Integer BirthMonth;
  private Integer BirthDay;
  private String address;
  private String phone;
  private int point;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getPwd()
  {
    return this.pwd;
  }

  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }

  public String getNickName()
  {
    return this.nickName;
  }

  public void setNickName(String nickName)
  {
    this.nickName = nickName;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getActualName()
  {
    return this.actualName;
  }

  public void setActualName(String actualName)
  {
    this.actualName = actualName;
  }

  public String getGrade()
  {
    return this.grade;
  }

  public void setGrade(String grade)
  {
    this.grade = grade;
  }

  public Integer getBirthYear()
  {
    return this.BirthYear;
  }

  public void setBirthYear(Integer birthYear)
  {
    this.BirthYear = birthYear;
  }

  public Integer getBirthMonth()
  {
    return this.BirthMonth;
  }

  public void setBirthMonth(Integer birthMonth)
  {
    this.BirthMonth = birthMonth;
  }

  public Integer getBirthDay()
  {
    return this.BirthDay;
  }

  public void setBirthDay(Integer birthDay)
  {
    this.BirthDay = birthDay;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public int getPoint()
  {
    return this.point;
  }

  public void setPoint(int point)
  {
    this.point = point;
  }

  public int getMemberNum()
  {
    return this.memberNum;
  }

  public void setMemberNum(int memberNum)
  {
    this.memberNum = memberNum;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("num : "); sb.append(this.memberNum); sb.append("\n");
    sb.append("id : "); sb.append(this.id); sb.append("\n");
    sb.append("pwd : "); sb.append(this.pwd); sb.append("\n");
    sb.append("email : "); sb.append(this.email); sb.append("\n");
    sb.append("name : "); sb.append(this.actualName); sb.append("\n");
    sb.append("grade : "); sb.append(this.grade); sb.append("\n");
    sb.append("birthDay : "); sb.append(this.BirthYear); sb.append(this.BirthMonth); sb.append(this.BirthDay); sb.append("\n");
    sb.append("addr : "); sb.append(this.address); sb.append("\n");
    sb.append("phone : "); sb.append(this.phone); sb.append("\n");
    sb.append("point : "); sb.append(this.point); sb.append("\n");
    return sb.toString();
  }
}