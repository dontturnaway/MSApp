package com.decode.msapp.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/* Needed for remote calls via RestTemplate*/
@Configuration
public class RestTempate {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//
//    @Autowired(required=false)
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }

}
