package com.decode.msapp.users.controllers;

import com.decode.msapp.users.DTO.*;
import com.decode.msapp.users.exception.UserIsFraudsterExeption;
import com.decode.msapp.users.exception.WrongInputParameters;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.services.UserRegisterService;
import com.decode.msapp.users.services.UserService;
import com.decode.msapp.users.util.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
public class UserController {

    private final UserService userService;
    private final UserRegisterService userRegisterService;
    private final ModelMapper mapper;
    private final UserValidator userValidator;

    @GetMapping()
    public ResponseEntity<List<UserDtoGet>> getAll() {
        log.info("All user list requested");
        var result = userService.findAll().stream()
                .map(e-> mapper.map(e, UserDtoGet.class))
                .collect(Collectors.toCollection(ArrayList::new));
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoGet> getById(@PathVariable("id") int id) {
        var user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No mathces found");
        }
        return new ResponseEntity<>(mapper.map(user, UserDtoGet.class), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/seachByName/{nameSearch}")
    public ResponseEntity<List<UserDtoGet>> getByNameSubstring(@PathVariable("nameSearch") String nameSearch) {
        var users = userService.findByNameSubstring(nameSearch);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No mathces found");
        }
        var result = users.stream()
                .map(e-> mapper.map(e, UserDtoGet.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid UserDtoAdd userDTOAdd,
                                         BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong parameters: " + bindingResult.getFieldErrors());
        }
        var userRegister=mapper.map(userDTOAdd, User.class);
        User user = userService.save(userRegister);
        return ResponseEntity.created(URI.create("")).body("Userid created: " + user.getId());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDtoAdd userDTOAdd,
                                         BindingResult bindingResult) {
        userValidator.validate(userDTOAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Error in parameters " + bindingResult);
        }
        User userRegistered=mapper.map(userDTOAdd, User.class);
        userRegistered = userRegisterService.register(userRegistered); //Controller advice will catch errors
        return ResponseEntity.created(URI.create("")).body("Userid created" + userRegistered.getId());
    }

    @PutMapping()
    public ResponseEntity<String> patch(@RequestBody @Valid UserDtoUpdate userUpdate,
                                        BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong parameters: " + bindingResult.getFieldErrors());
        }
        var updatedUser=mapper.map(userUpdate, User.class);
        userService.update(updatedUser);
        return ResponseEntity.ok().body("Userid updated " + updatedUser.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Valid UserIdDTO userId, BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Error in parameters " + bindingResult);
        }
        userService.delete(userId.getId());
        return ResponseEntity.ok().body("Userid deleted" + userId.getId());
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
