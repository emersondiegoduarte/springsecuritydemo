package com.spring.security.springsecuritydemo.login;

import com.spring.security.springsecuritydemo.login.util.JwtUtil;
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

    private final JwtUtil jwtUtil;


    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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

        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),
                loginRequest.password()));
        //Gerenerate Token
        String jwtToken = jwtUtil.generateJwtToken(auth);
        return ResponseEntity.ok(
                new LoginResponse(HttpStatus.OK.getReasonPhrase(),
                        userDTO, jwtToken)
                );

    }
}
