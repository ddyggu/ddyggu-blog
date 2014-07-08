package com.ddyggu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;

public class StringUtilityImpl
  implements StringUtility
{

  @Autowired(required=false)
  private ServletContext servletContext;
  private String RelativePath = "/Resources/Upload";

  public void setServletContext(ServletContext context)
  {
    this.servletContext = context;
  }

  public String getRelativePath()
  {
    return this.RelativePath;
  }

  public String getAbsoluteUploadPath()
  {
    String contextPath = this.servletContext.getRealPath(this.RelativePath);
    StringBuilder sb = new StringBuilder();
    sb.append(contextPath);
    return sb.toString();
  }

  public String encodedFileName(String fileName)
  {
    String slicedName = fileName.substring(0, fileName.length() - 4);
    String slicedExtension = fileName.substring(fileName.length() - 4, fileName.length());

    String encodedName = null;
    try {
      encodedName = URLEncoder.encode(slicedName, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    String notDuplicatedName = buildString("file", Long.valueOf(System.currentTimeMillis()));

    String finalName = buildString("/", notDuplicatedName);
    return buildString(finalName, slicedExtension);
  }

  public String buildString(Object key, Object value)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(key.toString());
    sb.append(value.toString());
    return sb.toString();
  }

  public boolean isIllegalExtension(String fileName)
  {
    List allowExtension = new ArrayList(Arrays.asList(new String[] { "jpg", "jpeg", "png", "gif" }));
    int namelength = fileName.length();
    String extension = fileName.substring(namelength - 3, namelength);
    return !allowExtension.contains(extension);
  }

  public boolean validTableName(String bbs)
  {
    return bbs.matches("(?!.*[\\-\\'\"]).*");
  }
}