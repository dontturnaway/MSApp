package com.decode.msapp.users.controllers.old;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
@Slf4j
public class MicroserviceTestController {

    private final RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    public MicroserviceTestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/request")
    public ResponseEntity<String> request() {
        log.info("Incoming request at {} for request /request ", applicationName);
        String response = restTemplate.getForObject("http://localhost:8090/response", String.class);
        return ResponseEntity.ok("response from /request + " + response);
    }

    @GetMapping("/response")
    public ResponseEntity<String> response() {
        log.info("Incoming request at {} at /response", applicationName);
        return ResponseEntity.ok("response from /response ");
    }
}