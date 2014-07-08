package com.ddyggu.bean;

public class EncodedFile
{
  private String imageurl;
  private String filename;
  private long filesize;
  private String originalurl;
  private String thumburl;
  private long fileNum;

  public EncodedFile()
  {
  }

  public EncodedFile(String imageurl, String filename, long filesize, String originalurl, String thumburl, long fileNum)
  {
    this.imageurl = imageurl;
    this.filename = filename;
    this.filesize = filesize;
    this.originalurl = originalurl;
    this.thumburl = thumburl;
    setFileNum(fileNum);
  }

  public String getImageurl() {
    return this.imageurl;
  }
  public void setImageurl(String imageurl) {
    this.imageurl = imageurl;
  }
  public String getFilename() {
    return this.filename;
  }
  public void setFilename(String filename) {
    this.filename = filename;
  }
  public long getFilesize() {
    return this.filesize;
  }
  public void setFilesize(long filesize) {
    this.filesize = filesize;
  }
  public String getOriginalurl() {
    return this.originalurl;
  }
  public void setOriginalurl(String originalurl) {
    this.originalurl = originalurl;
  }
  public String getThumburl() {
    return this.thumburl;
  }
  public void setThumburl(String thumburl) {
    this.thumburl = thumburl;
  }

  public long getFileNum() {
    return this.fileNum;
  }

  public void setFileNum(long fileNum) {
    this.fileNum = fileNum;
  }

  public String toString() {
    return this.filename;
  }
}