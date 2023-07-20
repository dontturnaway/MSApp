package com.decode.msapp.users.controllers;

import com.decode.msapp.users.DTO.UserRegisterDTO;
import com.decode.msapp.users.services.UserRegisterService;
import com.decode.msapp.users.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final PersonValidator personValidator;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") UserRegisterDTO user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
        public String performRegistration(@ModelAttribute("user") @Valid UserRegisterDTO user,
                                          BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(e-> {
                log.error("Error in fields " + e.toString());
            });
            return "/auth/registration";
        }
        personValidator.validate(user, bindingResult);
        userRegisterService.register(user);
        return "redirect:/auth/login";

    }
}
