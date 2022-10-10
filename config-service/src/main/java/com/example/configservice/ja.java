package com.example.configservice;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class ja {

    @Autowired
    Environment environment;

    @GetMapping("/path")
    public String path(){
        return environment.getProperty("path.text");
    }
}
