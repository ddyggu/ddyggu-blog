package com.ddyggu.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

public class InjectionRequestWrapper extends HttpServletRequestWrapper
{
  private String pattern = "[\\-\"\\']";

  public InjectionRequestWrapper(HttpServletRequest request) {
    super(request);
  }

  private String stripSqlCharacter(String parameter)
  {
    return parameter.replaceAll(this.pattern, "");
  }

  public String[] getParameterValues(String parameter)
  {
    String[] values = super.getParameterValues(parameter);
    if (values == null) {
      return null;
    }
    int count = values.length;
    if (parameter.equals("bbs")) {
      for (int i = 0; i < count; i++) {
        values[i] = stripSqlCharacter(values[i]);
      }
    }
    return values;
  }

  public String getParameter(String parameter)
  {
    String value = super.getParameter(parameter);
    if (parameter.equals("bbs")) {
      value = stripSqlCharacter(value);
    }
    return value;
  }

  public Part getPart(String name)
    throws IOException, ServletException
  {
    Part part = super.getPart(name);

    System.out.println("ContentType : " + part.getContentType());
    System.out.println("Name : " + part.getName());
    System.out.println("Size : " + part.getSize());
    Collection enu = part.getHeaderNames();

    Iterator iterator = enu.iterator();
    while (iterator.hasNext()) {
      System.out.println((String)iterator.next());
    }
    return part;
  }

  public Collection<Part> getParts()
    throws IOException, ServletException
  {
    Collection col = super.getParts();
    Iterator iterator;
    for (Iterator localIterator1 = col.iterator(); localIterator1.hasNext(); 
      iterator.hasNext())
    {
      Part part = (Part)localIterator1.next();
      System.out.println("ContentType(Parts) : " + part.getContentType());
      System.out.println("Name(Parts) : " + part.getName());
      System.out.println("Size(Parts) : " + part.getSize());
      Collection enu = part.getHeaderNames();

      iterator = enu.iterator();
      System.out.println((String)iterator.next());
    }

    return col;
  }
}