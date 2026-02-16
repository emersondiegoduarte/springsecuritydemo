package com.spring.security.springsecuritydemo.login;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class LoginController {

    @GetMapping("/user")
    //@PreAuthorize("hasRole('USER')")
    public String userEndpoint() {
        return "Hello USER: ";
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "Hello ADMIN: ";
    }
}
