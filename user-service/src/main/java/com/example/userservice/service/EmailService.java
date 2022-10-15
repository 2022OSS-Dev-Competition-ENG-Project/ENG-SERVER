package com.example.userservice.service;

import com.example.userservice.constant.EmailConstant;
import com.example.userservice.constant.SignUpConstant;
import org.apache.ibatis.mapping.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    public EmailService(JavaMailSender javaMailSender, RedisService redisService) {
        this.javaMailSender = javaMailSender;
        this.redisService = redisService;
    }
    private JavaMailSender javaMailSender;
    private RedisService redisService;



    /* 이메일 인증 코드 발송 */
    public void sendMail(String userEmail, String key, String type){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        if(type.equals("EmailCheck")) {
            message.setSubject(EmailConstant.SMTP_EMAIL_CHECK_TITLE_MESSAGE);
            message.setText(EmailConstant.SMTP_EMAIL_CODE_CHECK_CONTENT.replaceAll("\\$key", key));
            message.setText("\n 안증번호는 [" + key + "]입니다.");
            javaMailSender.send(message);
            redisService.setDataExpire(userEmail, key, 60 * 3L);
        }
        if(type.equals("ChangePassword")) {
            message.setSubject(EmailConstant.SMTP_PASSWORD_CHANGE_TITLE_MESSAGE);
            message.setText(EmailConstant.SMTP_PASSWORD_CHANGE_MESSAGE);
            message.setText("\n 임시 비밀번호는 [" + key + "]입니다.");
            javaMailSender.send(message);
            redisService.setDataExpire(userEmail, key, 60 * 3L);
        }
    }
}
