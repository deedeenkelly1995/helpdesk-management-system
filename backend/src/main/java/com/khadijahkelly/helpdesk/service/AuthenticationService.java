package com.khadijahkelly.helpdesk.service;

import com.khadijahkelly.helpdesk.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.khadijahkelly.helpdesk.dto.LoginRequest;
import com.khadijahkelly.helpdesk.dto.UserResponse;
import com.khadijahkelly.helpdesk.model.User;
import com.khadijahkelly.helpdesk.exception.InvalidCredentialsException;
import com.khadijahkelly.helpdesk.dto.LoginResponse;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
}
    //Log in
    public LoginResponse login(LoginRequest request) {

      String email = request.getEmail()
          .trim()
          .toLowerCase();
    
    User user = userRepository.findByEmail(email)
      .orElseThrow(() ->
          new InvalidCredentialsException("Invalid email or password")
      );

      if (!passwordEncoder.matches(
        request.getPassword(),
        user.getPasswordHash()
    )) {

    throw new InvalidCredentialsException(
        "Invalid email or password"
    );
  }

  // Generate JWT only after password verification succeeds
  String token = jwtService.generateToken(user);

      UserResponse userResponse = new UserResponse(
      user.getId(),
      user.getFirstName(),
      user.getLastName(),
      user.getEmail(),
      user.getRole()
  );

  return new LoginResponse(
      token,
      "Bearer",
      userResponse
    );
  }
}