package com.spring.security.springsecuritydemo.login;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class LoginController {

    @GetMapping("/user")
    public String userEndpoint(Authentication auth) {
        return "Hello USER: " + auth.getName();
    }


}
