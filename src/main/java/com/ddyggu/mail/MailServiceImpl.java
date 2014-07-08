package com.ddyggu.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl
  implements MailService
{

  @Autowired
  private JavaMailSender mailSender;

  public void sendMail(String subject, String text, String fromUser, String toUser)
  {
    MimeMessage message = this.mailSender.createMimeMessage();
    try
    {
      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

      messageHelper.setSubject(subject);
      messageHelper.setTo(toUser);
      messageHelper.setFrom(fromUser);
      messageHelper.setText(text, true);
      this.mailSender.send(message);
    }
    catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public void sendMails(String subject, String text, String fromUser, String toUser, String[] toCC)
  {
    MimeMessage message = this.mailSender.createMimeMessage();
    try
    {
      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

      messageHelper.setSubject(subject);
      messageHelper.setTo(toUser);
      messageHelper.setFrom(fromUser);
      messageHelper.setCc(toCC);
      messageHelper.setText(text, true);
      this.mailSender.send(message);
    }
    catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}