package com.spring.security.springsecuritydemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PathConfigs {

    @Bean(name = "publicPaths")
    public List<String> publicPaths() {
        return List.of("/user");
    }

    @Bean(name = "privatePaths")
    public List<String> privatePaths() {
        return List.of("/admin");
    }

}
