package com.example.engproject.service;

import com.example.engproject.constant.SignUpConstant;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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
    public void sendMail(String userEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        String key = createKey();
        message.setTo(userEmail);
        message.setSubject(SignUpConstant.SIGNUP_SEND_MAIL_TITLE);
        message.setText(SignUpConstant.SIGNUP_SEND_MAIL_CONTENT.replaceAll("\\$key", key));
        javaMailSender.send(message);
        redisService.setDataExpire(userEmail,key,60 * 3L);
    }
    /* 인증번호 만들기 */
    private String createKey() {
        Random random = new Random();
        return Integer.toString(random.nextInt((int)Math.pow(10,6)));
    }
}
