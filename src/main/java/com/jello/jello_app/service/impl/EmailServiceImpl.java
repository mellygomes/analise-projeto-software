package com.jello.jello_app.service.impl;

import com.jello.jello_app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;
    @Value("${spring.mail.verify.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public void sendNewAccountEmail(String name, String emailTo, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
    }

    @Override
    public void sendPasswordResetEmail(String name, String emailTo, String token) {

    }
}
