package com.decode.msapp.users.controllers;

import com.decode.msapp.users.security.UserDetailsImpl;
import com.decode.msapp.users.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class IndexWebController {

    private final AdminService adminService;

    public IndexWebController(AdminService adminService) {
        this.adminService=adminService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        log.info("User defails fetched " + userDetailsImpl.getPerson().getName());
        return "hello";
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAdminPage() {
        adminService.doAdminStuff();
        return "admin";
    }

}
