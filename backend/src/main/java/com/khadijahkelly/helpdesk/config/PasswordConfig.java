package com.khadijahkelly.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
  
  @Bean
  public PasswordEncoder passwordEncoder() {    // hashing and verifying passwords
    return new BCryptPasswordEncoder();         // create salted password hash using BCrypt
  }

}
