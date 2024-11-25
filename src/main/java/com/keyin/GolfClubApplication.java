package com.keyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GolfClubApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.keyin.GolfClubApplication.class, args);
    }
}