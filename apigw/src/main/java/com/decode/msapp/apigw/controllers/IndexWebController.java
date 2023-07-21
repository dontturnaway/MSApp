package com.decode.msapp.apigw.controllers;

import com.decode.msapp.apigw.service.AdminService;
//import com.decode.msapp.users.security.UserDetailsImpl;
//import com.decode.msapp.users.services.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/")
public class IndexWebController {

    private final AdminService adminService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAdminPage() {
        adminService.doAdminStuff();
        return "admin";
    }

    //  For future refactoring
//    @GetMapping("/showUserInfo")
//    public String showUserInfo() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
//        log.info("User defails fetched " + userDetailsImpl.getPerson().getName());
//        return "hello";
//    }

}
