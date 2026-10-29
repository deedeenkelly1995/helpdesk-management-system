package com.khadijahkelly.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import jakarta.servlet.DispatcherType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.http.HttpMethod;

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

        .requestMatchers(       //admin delete tickets
            HttpMethod.DELETE,
            "/api/tickets/*"
        ).hasRole("ADMIN")

        .requestMatchers(       //put rule for technican and admin
            HttpMethod.PUT,
            "/api/tickets/*"
        ).hasAnyRole("TECHNICIAN", "ADMIN")

        .requestMatchers(       //patch rule for technican and admin
            HttpMethod.PATCH,
            "/api/tickets/*/assign"     
        ).hasAnyRole("TECHNICIAN", "ADMIN")

        .requestMatchers("/api/**").authenticated()
        .anyRequest().denyAll()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwt -> jwt
            .jwtAuthenticationConverter(jwtAuthenticationConverter())
        )
    );
    return http.build();
  }

  @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter roles =
            new JwtGrantedAuthoritiesConverter();

        roles.setAuthoritiesClaimName("role");
        roles.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter =
            new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(roles);

        return converter;
    }
}
