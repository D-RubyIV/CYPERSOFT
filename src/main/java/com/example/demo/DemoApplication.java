package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.example.demo.controller",
                "com.example.demo.api",
                "com.example.demo.config",
                "com.example.demo.exception",
                "com.example.demo.model",
                "com.example.demo.service",
                "com.example.demo.repository",

        })
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
