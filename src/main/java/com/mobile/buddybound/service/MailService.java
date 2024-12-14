package com.mobile.buddybound.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendOtpEmail(String to, String userName, String otpCode);
}
