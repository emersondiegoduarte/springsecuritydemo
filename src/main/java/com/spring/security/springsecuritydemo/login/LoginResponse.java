package com.spring.security.springsecuritydemo.login;

public record LoginResponse(String message, UserDTO userDTO, String token) {
}
