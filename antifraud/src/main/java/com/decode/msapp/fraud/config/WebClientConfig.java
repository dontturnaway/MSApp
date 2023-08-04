package com.decode.msapp.fraud.config;

import com.decode.msapp.fraud.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebClientConfig {

    @Value("${app.serviceurl.users}")
    private String userServiceUrl;

    private final WebClient.Builder webClientBuilder;

    @Bean
    public WebClient employeeWebClient() {
        log.info("Setting up employeeWebClient for URL: {}", userServiceUrl);
        return webClientBuilder
                .baseUrl(userServiceUrl)
                .build();
    }

    @Bean
    public UserClient employeeClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(employeeWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(UserClient.class);
    }
}
