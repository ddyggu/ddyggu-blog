package com.ddyggu.util;

import java.beans.PropertyEditorSupport;

public class EncryptionEditor extends PropertyEditorSupport {
  
	public String getAsText() {
    EncryptionEncoding ee = new EncryptionEncoding();
    try {
      String value = (String)getValue();
      return ee.TripleDesDecoding(value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void setAsText(String text) {
    EncryptionEncoding ee = new EncryptionEncoding();
    try {
      setValue(ee.TripleDesEncoding(text));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
