package com.decode.msapp.users.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class SessionPropagationController {

    @Secured("ROLE_USER")
    @GetMapping("/propagation")
    public String getUserParameter(HttpServletRequest request) {
        log.info("Received inbound parameter: {}", request.getHeader("loggedInUser"));
        log.info("Received inbound parameter: {}", request.getHeader("loggedInRole"));

        var usr = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        log.info("Set context User to {}", usr);
        role.forEach(e -> log.info("Set context Role to {}",e.getAuthority()));
        return "OK";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/propagation_admin")
    public String getUserParameter() {
        return "OK";
    }


}
