package com.decode.msapp.users.controllers.old;

import com.decode.msapp.users.models.Person;
import com.decode.msapp.users.services.PeopleService;
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
    private final PeopleService peopleService;

    @Value("${spring.application.name}")
    private String applicationName;

    public PeopleMicroserviceController(RestTemplate restTemplate, PeopleService peopleService) {
        this.restTemplate = restTemplate;
        this.peopleService = peopleService;
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
        var result = peopleService.findAll();
        return ResponseEntity.ok("[" + applicationName + "]: [response from /response:] "+ result);
    }

    /* Standart approach for deserializing objects */
    @GetMapping("/requestpeopleobjectstd")
    public ResponseEntity<List<Person>> requestObjectStandart() {
        log.info("Incoming request at {} for request /requestobject ", applicationName);
        Person[] objects  = restTemplate.
                getForEntity("http://localhost:8090/responsepeopleobject", Person[].class).getBody();
        assert objects != null;
        var result= Arrays.stream(objects).toList();
        return ResponseEntity.ok(result);
    }

    /* The more shorthand approach for deserializing objects */
    @GetMapping("/requestpeopleobject")
    public ResponseEntity<List<Person>> requestObjectShorthand() {
        log.info("Incoming request at {} for request /requestobject ", applicationName);
        List<Person> personList = restTemplate.exchange(
                        "http://localhost:8090/responsepeopleobject",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Person>>() {}
                ).getBody();
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/responsepeopleobject")
    public ResponseEntity<List<Person>> responseObject() {
        log.info("Incoming request at {} at /responseobject", applicationName);
        return ResponseEntity.ok(peopleService.findAll());
    }

}