package com.decode.msapp.users.controllers;

import com.decode.msapp.users.DTO.PersonDTO;
import com.decode.msapp.users.DTO.PersonRegisterDTO;
import com.decode.msapp.users.models.Person;
import com.decode.msapp.users.services.PeopleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor //no need to create constructor
@RequestMapping("/peoplerest")
public class PeopleRESTController {

    private final PeopleService peopleService;

    @GetMapping()
    public List<PersonDTO> getAll() {
        log.info("[GET PERSON CONTROLLER]: " + " all user list requested");
        log.error("[GET PERSON CONTROLLER]: " + " TEST");
        return peopleService.findAllDTO();
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable("id") int id) {
        return peopleService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid PersonRegisterDTO personRegisterDTO,
                                 BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
           log.info("[POST PERSON CONTROLLER]: error, " + bindingResult);
           return ResponseEntity.badRequest().body("Stop write shit");
        }
        Person person = peopleService.save(personRegisterDTO);
        return ResponseEntity.created(URI.
                create("")).body("Userid " + person.getId() + " created");
    }
    /* For post query
    {
    "username":"userREST",
    "yearOfBirth":1900,
    "password":"123",
    "role":"ROLE_USER"
    }
     */


}
