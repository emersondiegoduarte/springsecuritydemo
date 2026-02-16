package com.spring.security.springsecuritydemo.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class LoginController {

    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        var userDTO = new UserDTO();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),
                loginRequest.password()));
        return ResponseEntity.ok(
                new LoginResponse(HttpStatus.OK.getReasonPhrase(),
                        userDTO, null)
                );

    }
}
