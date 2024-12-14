package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.config.OtpGenerator;
import com.mobile.buddybound.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private void sendEmail(String to, String subject, String body) throws MailException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        try {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            mailSender.send(mimeMessage);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendOtpEmail(String to, String userName, String otpCode) {

        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("otpCode", otpCode);
        context.setVariable("validityMinutes", 10);

        String htmlContent = templateEngine.process("otp-template.html", context);
        try {
            this.sendEmail(to, "BuddyBound: Reset password request", htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
