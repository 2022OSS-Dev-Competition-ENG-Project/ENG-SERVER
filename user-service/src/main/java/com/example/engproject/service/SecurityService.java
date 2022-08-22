package com.example.engproject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class SecurityService {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private  static String SECRET_KEY;
    //로그인 서비스 던질때 같
    public String createToken(String ResponseId , long expTime, String phoneNum, String UserName){
        if(expTime <= 0){
            throw new RuntimeException("만료시간이 0보다 커야합니다.");
        }
        String userPhoneNum = String.valueOf(phoneNum);
        SECRET_KEY = "dkawmasrsdghfsehbsrgnbf" + userPhoneNum + "dasdawezxvbzsdfqefqwrasdfadgfmd";

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key singingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(ResponseId)
                .claim("name", UserName)
                .signWith(singingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    //토큰검증하는 메서드를 boolean~~
    public boolean getSubject(String token, String phoneNum){
        String userPhoneNum = String.valueOf(phoneNum);
        SECRET_KEY = "dkawmasrsdghfsehbsrgnbf" + userPhoneNum + "dasdawezxvbzsdfqefqwrasdfadgfmd";
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return true;
    }
}