package com.jello.jello_app.service.impl;

import com.jello.jello_app.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendNewAccountEmail(String name, String emailTo, String token) {

    }

    @Override
    public void sendPasswordResetEmail(String name, String emailTo, String token) {

    }
}
