package com.cravekart.notification_service.service;

import com.cravekart.notification_service.dto.NotificationEvent;
import com.cravekart.notification_service.dto.NotificationResult;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final JavaMailSender mailSender;
    private final ApplicationEventPublisher applicationEventPublisher;



    public NotificationResult send(NotificationEvent event){
        log.info("***********Sending email notification to {}*********", event.getEmail());
        MimeMessage message = mailSender.createMimeMessage();

        try{

            MimeMessageHelper helper = new MimeMessageHelper(
                    message, true
            );

            helper.setFrom("cyrodev17@gmail.com");
            helper.setTo(event.getEmail());
            helper.setSubject(event.getSubject());
            helper.setText(event.getBody(), false);
            mailSender.send(message);

            String messageId = UUID.randomUUID().toString();
            log.info("Email sent to {} | subject : {}, messageId: {}",
                    event.getEmail(),
                    event.getSubject(),
                    messageId
                    );

            return NotificationResult.success("EMAIL",
                    event.getEmail(),
                    messageId);

        } catch (MessagingException  e) {
            log.error("Failed to send email to {}: {}", event.getEmail(), e.getMessage());
            return NotificationResult.failure("EMAIL", event.getEmail(), e.getMessage());
        }
    }


}
