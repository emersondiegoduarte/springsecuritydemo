package com.spring.security.springsecuritydemo.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity
public class SecurityConfig {

    @Qualifier("publicPaths")
    private final List<String> publicPaths;

    @Qualifier("privatePaths")
    private final List<String> privatePaths;

    public SecurityConfig(List<String> publicPaths, List<String> privatePaths) {
        this.publicPaths = publicPaths;
        this.privatePaths = privatePaths;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> {
                    publicPaths.forEach(path -> requests.requestMatchers(path).permitAll());
                    privatePaths.forEach(path -> requests.requestMatchers(path)
                            .hasRole("ADMIN"));
                    requests.anyRequest().denyAll();
                })
                .cors(corsConfigurationSource -> corsConfigurationSource.configurationSource(corsConfigurationSource()))
                .addFilterBefore(new JwtValidationTokenFilter(publicPaths), BasicAuthenticationFilter.class)
                .formLogin(flc -> flc.disable())
                    .httpBasic(htp -> htp.disable())
                    .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setAllowedMethods(List.of("*"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider autheticationProvider){
        return new ProviderManager(autheticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
