package com.ddyggu.filter;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.owasp.esapi.ESAPI;

public class XSSRequestWrapper extends HttpServletRequestWrapper
{
  private static Pattern[] patterns = { 
    Pattern.compile("<script>(.*?)</script>", 2), 
    Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42), 
    Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42), 
    Pattern.compile("</script>", 2), 
    Pattern.compile("<script(.*?)>", 42), 
    Pattern.compile("eval\\((.*?)\\)", 42), 
    Pattern.compile("expression\\((.*?)\\)", 42), 
    Pattern.compile("javascript:", 2), 
    Pattern.compile("vbscript:", 2), 
    Pattern.compile("onload(.*?)=", 42) };

  public XSSRequestWrapper(HttpServletRequest request)
  {
    super(request);
  }

  public String[] getParameterValues(String parameter)
  {
    String[] values = super.getParameterValues(parameter);
    if (values == null) {
      return null;
    }
    int count = values.length;
    String[] encodedValues = new String[count];
    for (int i = 0; i < count; i++) {
      encodedValues[i] = stripXSS(values[i]);
    }
    return encodedValues;
  }

  public String getParameter(String parameter)
  {
    String value = super.getParameter(parameter);
    return stripXSS(value);
  }

  public String getHeader(String name)
  {
    String value = super.getHeader(name);
    return stripXSS(value);
  }

  private String stripXSS(String value) {
    if (value != null) {
      value = ESAPI.encoder().canonicalize(value);

      value = value.replaceAll("", "");

      for (Pattern scriptPattern : patterns) {
        if (scriptPattern.matcher(value).matches()) {
          System.out.println("script language matched");
          value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
      }
    }

    return value;
  }
}