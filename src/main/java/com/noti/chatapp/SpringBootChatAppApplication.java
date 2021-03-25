package com.noti.chatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBootChatAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootChatAppApplication.class, args);
    }

}
