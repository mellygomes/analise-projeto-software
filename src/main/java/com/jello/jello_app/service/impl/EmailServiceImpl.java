package com.jello.jello_app.service.impl;

import com.jello.jello_app.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New user account verification";
    public static final String PASSSWORD_RESET_REQUEST = "Reset password request";

    private final JavaMailSender sender;
    @Value("${spring.mail.verify.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public void sendNewAccountEmail(String name, String emailTo, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromMail);
            message.setTo(emailTo);
            message.setText(getEmailMessage(name, host, token));
            sender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendPasswordResetEmail(String name, String emailTo, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(PASSSWORD_RESET_REQUEST);
            message.setFrom(fromMail);
            message.setTo(emailTo);
            message.setText(getResetPasswordMessage(name, host, token));
            sender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
