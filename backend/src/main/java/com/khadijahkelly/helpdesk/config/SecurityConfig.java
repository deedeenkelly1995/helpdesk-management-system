package com.khadijahkelly.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http) throws Exception {

        http
          .csrf(csrf -> csrf
              .csrfTokenRepository(
                  CookieCsrfTokenRepository.withHttpOnlyFalse()
              )
              .csrfTokenRequestHandler(
                  new CsrfTokenRequestAttributeHandler()
              )
          )
            .authorizeHttpRequests(auth -> auth
        .dispatcherTypeMatchers(                    //corrected the 409 error for duplicate email
            DispatcherType.ERROR
        ).permitAll()
        .requestMatchers(
            "/api/users/register",
            "/api/auth/csrf",
            "/api/auth/login"
        ).permitAll()
        .requestMatchers("/api/**").authenticated()
        .anyRequest().denyAll()
    );
    return http.build();
  }
}
