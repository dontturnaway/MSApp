package com.decode.msapp.users.controllers.old;

import com.decode.msapp.users.model.User;
import com.decode.msapp.users.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/")
public class PeopleMicroserviceController {

    private final RestTemplate restTemplate;
    private final UserService userService;

    @Value("${spring.application.name}")
    private String applicationName;

    public PeopleMicroserviceController(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @GetMapping("/requestpeople")
    public ResponseEntity<String> request() {
        log.info("Incoming request at {} for request /request ", applicationName);
        String response = restTemplate.getForObject("http://localhost:8090/responsepeople", String.class);
        return ResponseEntity.ok("[" + applicationName + "]: [response from /request:] " + response);
    }

    @GetMapping("/responsepeople")
    public ResponseEntity<String> response() {
        log.info("Incoming request at {} at /response", applicationName);
        var result = userService.findAll();
        return ResponseEntity.ok("[" + applicationName + "]: [response from /response:] "+ result);
    }

    /* Standart approach for deserializing objects */
    @GetMapping("/requestpeopleobjectstd")
    public ResponseEntity<List<User>> requestObjectStandart() {
        log.info("Incoming request at {} for request /requestobject ", applicationName);
        User[] objects  = restTemplate.
                getForEntity("http://localhost:8090/responsepeopleobject", User[].class).getBody();
        assert objects != null;
        var result= Arrays.stream(objects).toList();
        return ResponseEntity.ok(result);
    }

    /* The more shorthand approach for deserializing objects */
    @GetMapping("/requestpeopleobject")
    public ResponseEntity<List<User>> requestObjectShorthand() {
        log.info("Incoming request at {} for request /requestobject ", applicationName);
        List<User> userList = restTemplate.exchange(
                        "http://localhost:8090/responsepeopleobject",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {}
                ).getBody();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/responsepeopleobject")
    public ResponseEntity<List<User>> responseObject() {
        log.info("Incoming request at {} at /responseobject", applicationName);
        return ResponseEntity.ok(userService.findAll());
    }

}