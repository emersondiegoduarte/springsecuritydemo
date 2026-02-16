package com.spring.security.springsecuritydemo.login;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter()
@Setter
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String mobileNumber;
    private String role;
    private Long companyId;
    private String companyName;
    private Instant createdAt;
    private String createdBy;
}
