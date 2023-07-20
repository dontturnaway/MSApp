package com.decode.msapp.users.controllers;

import com.decode.msapp.users.DTO.UserDTO;
import com.decode.msapp.users.DTO.UserRegisterDTO;
import com.decode.msapp.users.models.User;
import com.decode.msapp.users.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRESTController {

    private final UserService userService;

    @GetMapping()
    public List<UserDTO> getAll() {
        log.info("[GET PERSON CONTROLLER]: " + " all user list requested");
        return userService.findAllDTO();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid UserRegisterDTO personRegisterDTO,
                                 BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
           log.info("Error in parameters, " + bindingResult);
           return ResponseEntity.badRequest().body("Stop write shit");
        }
        User user = userService.save(personRegisterDTO);
        return ResponseEntity.created(URI.
                create("")).body("Userid " + user.getId() + " created");
    }
    /* For post query
    {
    "name":"userREST",
    "yearOfBirth":1900,
    "password":"123",
    "role":"ROLE_USER"
    }
     */


}
