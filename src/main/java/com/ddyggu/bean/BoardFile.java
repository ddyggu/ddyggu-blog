package com.ddyggu.bean;

public class BoardFile
{
  private int fileNum;
  private int boardNum;
  private String imageurl;
  private String fileName;
  private long fileSize;
  private String originalUrl;
  private String thumbUrl;
  private String encodedFileName;
  private String encodedUrl;

  public int getFileNum()
  {
    return this.fileNum;
  }

  public void setFileNum(int fileNum)
  {
    this.fileNum = fileNum;
  }

  public int getBoardNum()
  {
    return this.boardNum;
  }

  public void setBoardNum(int boardNum)
  {
    this.boardNum = boardNum;
  }

  public String getImageurl()
  {
    return this.imageurl;
  }

  public void setImageurl(String imageurl)
  {
    this.imageurl = imageurl;
  }

  public String getFileName()
  {
    return this.fileName;
  }

  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  public long getFileSize()
  {
    return this.fileSize;
  }

  public void setFileSize(long fileSize)
  {
    this.fileSize = fileSize;
  }

  public String getOriginalUrl()
  {
    return this.originalUrl;
  }

  public void setOriginalUrl(String originalUrl)
  {
    this.originalUrl = originalUrl;
  }

  public String getThumbUrl()
  {
    return this.thumbUrl;
  }

  public void setThumbUrl(String thumbUrl)
  {
    this.thumbUrl = thumbUrl;
  }

  public String getEncodedFileName()
  {
    return this.encodedFileName;
  }

  public void setEncodedFileName(String encodedFileName)
  {
    this.encodedFileName = encodedFileName;
  }

  public String getEncodedUrl()
  {
    return this.encodedUrl;
  }

  public void setEncodedUrl(String encodedUrl)
  {
    this.encodedUrl = encodedUrl;
  }
}