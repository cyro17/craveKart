package com.cyro.cravekart.service.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceUtil {

  private final JavaMailSender mailSender;

  public void sendMail(String to, String subject, String body) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setTo(to);
    mailMessage.setSubject(subject);
    mailMessage.setText(body);

    mailSender.send(mailMessage);
  }

  public String generateRandomToken() {
    return UUID.randomUUID().toString();
  }

  public Date calculateExpiryDate() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MINUTE, 10);
    return cal.getTime();
  }
}
