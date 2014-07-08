package com.ddyggu.mail;

import java.io.Serializable;

public interface MailService extends Serializable {
	
  public void sendMail(String paramString1, String paramString2, String paramString3, String paramString4);
  public void sendMails(String paramString1, String paramString2, String paramString3, String paramString4, String[] paramArrayOfString);

}
