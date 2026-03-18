package com.cyro.cravekart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@Slf4j
public class CraveKartApplication {

  public static void main(String[] args) {

    SpringApplication.run(CraveKartApplication.class, args);
    log.info("************{}***************", System.getenv("JWT_SECRET"));
  }
}
