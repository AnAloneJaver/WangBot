package com.wangbot;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author God
 */
@ComponentScan({"com.wangbot.**"})
@MapperScan("com.wangbot.**.dao")
@EnableSimbot
@SpringBootApplication
public class WangBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WangBotApplication.class, args);
    }

}
