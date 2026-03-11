package com.cyro.cravekart;

import com.cyro.cravekart.service.impl.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MailServiceTest {


  @Autowired
  private MailService mailService;


  @Test
  public void sendMail(){
    mailService.send("cyroaxr@gmail.com", "test", "mail service activated");
    log.info("mail sent successfully");
  }

}
