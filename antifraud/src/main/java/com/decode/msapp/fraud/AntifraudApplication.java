package com.decode.msapp.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AntifraudApplication {
    public static void main(String[] args) {
        SpringApplication.run(AntifraudApplication.class, args);
        log.info("Fraud has started");
    }
}
