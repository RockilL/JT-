package com.example.login6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Login6Application {
    public static void main(String[] args) {
        SpringApplication.run(Login6Application.class, args);
    }
}
