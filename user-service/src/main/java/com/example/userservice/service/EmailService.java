package com.example.userservice.service;

import com.example.userservice.constant.EmailConstant;
import com.example.userservice.constant.SignUpConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class EmailService {

    @Autowired
    public EmailService(JavaMailSender javaMailSender, RedisService redisService, Environment env) {
        this.javaMailSender = javaMailSender;
        this.redisService = redisService;
        this.env = env;
    }
    private JavaMailSender javaMailSender;
    private RedisService redisService;
    private Environment env;



    /* 이메일 인증 코드 발송 */
    public void sendMail(String userEmail, String key, String type){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setFrom(env.getProperty("secret_settings.address"));
        if(type.equals("EmailCheck")) {
            message.setSubject(EmailConstant.SMTP_EMAIL_CHECK_TITLE_MESSAGE);
            message.setText(EmailConstant.SMTP_EMAIL_CODE_CHECK_CONTENT);
            message.setText("\n 안증번호는 [" + key + "]입니다.");
            redisService.deleteData(userEmail);
            redisService.setDataExpire(userEmail, key, 180);
        }
        if(type.equals("ChangePassword")) {
            message.setSubject(EmailConstant.SMTP_PASSWORD_CHANGE_TITLE_MESSAGE);
            message.setText(EmailConstant.SMTP_PASSWORD_CHANGE_MESSAGE);
            message.setText("\n 임시 비밀번호는 [" + key + "]입니다.");
        }
        javaMailSender.send(message);
    }
}
