package com.ddyggu.exception;

public class NameNotFoundException extends RuntimeException
{
  String bbsName;

  public NameNotFoundException(String bbsName)
  {
    this.bbsName = bbsName;
    System.err.println(bbsName + "에 해당되는 DB Table이 존재하지 않습니다. Table 이름을 다시 확인하여 주십시오");
  }
}