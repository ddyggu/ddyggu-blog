package com.ddyggu.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component("EncryptionEncoding")
public class EncryptionEncoding
{
  private MessageDigest digest = null;

  public String SHA1(String password)
  {
    try
    {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      digest.update(password.getBytes());
      byte[] messageDigest = digest.digest();

      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < messageDigest.length; i++)
        hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
      return hexString.toString();
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String MD5(String password)
  {
    try
    {
      this.digest = MessageDigest.getInstance("MD5");
      byte[] md = this.digest.digest(password.getBytes());
      StringBuffer hexString = new StringBuffer();

      for (int i = 0; i < md.length; i++) {
        hexString.append(Integer.toString((md[i] & 0xF0) >> 4, 16));
        hexString.append(Integer.toString(md[i] & 0xF, 16));
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String key()
  {
    return "ab_booktv_abcd0912345678";
  }

  public Key getKey(String keyValue)
    throws Exception
  {
    DESedeKeySpec desKeySpec = new DESedeKeySpec(keyValue.getBytes());
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
    Key key = keyFactory.generateSecret(desKeySpec);
    return key;
  }

  public String TripleDesEncoding(String ID)
    throws Exception
  {
    if ((ID == null) || (ID.length() == 0)) {
      return "";
    }
    String instance = key().length() == 24 ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
    Cipher cipher = Cipher.getInstance(instance);
    cipher.init(1, getKey(key()));
    String amalgam = ID;

    byte[] inputBytes1 = amalgam.getBytes("UTF8");
    byte[] outputBytes1 = cipher.doFinal(inputBytes1);

    Base64.encodeBase64(outputBytes1);

    String outputStr1 = new String(Base64.encodeBase64(outputBytes1));
    return outputStr1;
  }

  public String TripleDesDecoding(String codedID)
    throws Exception
  {
    if ((codedID == null) || (codedID.length() == 0)) {
      return "";
    }
    String instance = key().length() == 24 ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
    Cipher cipher = Cipher.getInstance(instance);
    cipher.init(2, getKey(key()));

    byte[] inputBytes1 = Base64.decodeBase64(codedID);
    byte[] outputBytes2 = cipher.doFinal(inputBytes1);

    String strResult = new String(outputBytes2, "UTF8");
    return strResult;
  }
}