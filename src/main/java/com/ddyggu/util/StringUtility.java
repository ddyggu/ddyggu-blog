package com.ddyggu.util;

public  interface StringUtility {
	
  public  String getRelativePath();

  public  String getAbsoluteUploadPath();

  public  String encodedFileName(String paramString);

  public  String buildString(Object paramObject1, Object paramObject2);

  public  boolean validTableName(String paramString);

  public  boolean isIllegalExtension(String paramString);

}