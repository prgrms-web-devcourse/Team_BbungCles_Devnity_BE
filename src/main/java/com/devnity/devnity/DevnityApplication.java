package com.devnity.devnity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)   // security 잠깐 끄기
public class DevnityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevnityApplication.class, args);
    }

}
