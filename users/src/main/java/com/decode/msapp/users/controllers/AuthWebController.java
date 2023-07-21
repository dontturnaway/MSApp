package com.decode.msapp.users.controllers;

import com.decode.msapp.users.DTO.UserDtoAdd;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.services.UserRegisterService;
import com.decode.msapp.users.util.UserValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthWebController {

    private final UserRegisterService userRegisterService;
    private final UserValidator userValidator;
    private final ModelMapper mapper;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") UserDtoAdd user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
        public String performRegistration(@ModelAttribute("user") @Valid UserDtoAdd userDTOAdd,
                                          BindingResult bindingResult) throws Exception {
        userValidator.validate(userDTOAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(e-> {
                log.error("Error in fields " + e.toString());
            });
            return "/auth/registration";
        }
        User userToRegister=mapper.map(userDTOAdd, User.class);
        userRegisterService.register(userToRegister);
        return "redirect:/auth/login";

    }
}
