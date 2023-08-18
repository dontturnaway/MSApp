package com.decode.msapp.apigw.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebClientConfig {

    @Value("${app.baseurl}")
    private String baseUrl;

    private final WebClient.Builder webClientBuilder;

    @Bean
    public WebClient webClient() {
        log.info("Setting up employeeWebClient for URL: {}", baseUrl);
        return webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }
}
