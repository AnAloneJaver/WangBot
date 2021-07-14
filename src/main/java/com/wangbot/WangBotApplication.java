package com.wangbot;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@EnableSimbot
@SpringBootApplication
public class WangBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WangBotApplication.class, args);
    }

}
