package com.ddyggu.bean;

public class Search
{
  private String searchType;
  private String keyword;

  public Search(String searchType, String keyword)
  {
    this.searchType = searchType;
    this.keyword = keyword;
  }

  public String getSearchType() {
    return this.searchType;
  }
  public void setSearchType(String searchType) {
    this.searchType = searchType;
  }
  public String getKeyword() {
    return this.keyword;
  }
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
}
