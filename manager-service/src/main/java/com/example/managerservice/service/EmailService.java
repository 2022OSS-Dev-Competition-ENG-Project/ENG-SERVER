package com.example.managerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.example.managerservice.constant.EmailConstant.*;

@Service
public class EmailService {


    private RedisService redisService;
    private Environment env;
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(RedisService redisService, Environment env, JavaMailSender javaMailSender) {
        this.redisService = redisService;
        this.env = env;
        this.javaMailSender = javaMailSender;
    }

    /* 이메일 전송 */
    public void sendMail(String userEmail,String key,String type){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setFrom(env.getProperty("secret_settings.address"));
        if(type.equals("EmailCheck")) {
            message.setSubject(SMTP_EMAIL_CHECK_TITLE_MESSAGE);
            message.setText(SMTP_EMAIL_CHECK_MESSAGE.replaceAll("\\$key", key));
            redisService.setDataExpire(userEmail,key,60 * 3L);
        }
        if (type.equals("resetPassword")) {
            message.setSubject(SMTP_PASSWORD_CHANGE_TITLE_MESSAGE);
            message.setText(SMTP_PASSWORD_CHANGE_MESSAGE.replaceAll("\\$key", key));
        }
        javaMailSender.send(message);
    }

}
