package com.decode.msapp.users.controllers;

import com.decode.msapp.users.DTO.UserDTO;
import com.decode.msapp.users.DTO.UserRegisterDTO;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRESTController {

    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAll() {
        log.info("All user list requested");
        var result = userService.findAll().stream()
                .map(e-> mapper.map(e, UserDTO.class))
                .collect(Collectors.toCollection(ArrayList::new));
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") int id) {
        if(id < 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"A new user cannot already have an ID");
        }
        var person = userService.findById(id);
        return new ResponseEntity<>(mapper.map(person, UserDTO.class), HttpStatusCode.valueOf(200));
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid UserRegisterDTO userRegisterDTO,
                                 BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
           log.info("Error in parameters, " + bindingResult);
           return ResponseEntity.badRequest().body("Stop write shit");
        }
        var userRegister=mapper.map(userRegisterDTO, User.class);
        User user = userService.save(userRegister);
        return ResponseEntity.created(URI.
                create("")).body("Userid " + user.getId() + " created");
    }

    @PutMapping()
    public ResponseEntity<String> patch(@RequestBody @Valid UserRegisterDTO userRegisterDTO,
                                         BindingResult bindingResult)  {
        if (userRegisterDTO.getId() == null) {
            return ResponseEntity.badRequest().body("ID to update is missed");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Error in parameters " + bindingResult);
        }
        var updatedUser=mapper.map(userRegisterDTO, User.class);
        userService.update(updatedUser);
        return ResponseEntity.ok().body("Userid " + updatedUser.getId() + " updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> patch(@PathVariable("id") int id)  {
        if (id < 1) {
            return ResponseEntity.badRequest().body("ID to update is missed");
        }
        userService.delete(id);
        return ResponseEntity.ok().body("Userid " + id + " deleted");
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
