
package com.khadijahkelly.helpdesk.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.khadijahkelly.helpdesk.model.User;
import com.khadijahkelly.helpdesk.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.khadijahkelly.helpdesk.dto.CreateUserRequest;
import com.khadijahkelly.helpdesk.model.Role;
import com.khadijahkelly.helpdesk.dto.UserResponse;
import com.khadijahkelly.helpdesk.dto.LoginRequest;

import com.khadijahkelly.helpdesk.exception.EmailAlreadyExistsException;
import com.khadijahkelly.helpdesk.exception.InvalidCredentialsException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // List all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("User not found"));
    }

    // Get user by email
    public User getUserByEmail(String email) {
      return userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new IllegalArgumentException("User not found"));
    }

    
    // Register User
    public UserResponse registerUser(CreateUserRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        String hashedPassword =
            passwordEncoder.encode(request.getPassword());

        User newUser = new User(
            request.getFirstName(),
            request.getLastName(),
            email,
            hashedPassword,
            Role.USER
        );

        User savedUser = userRepository.save(newUser);

        return new UserResponse(
            savedUser.getId(),
            savedUser.getFirstName(),
            savedUser.getLastName(),
            savedUser.getEmail(),
            savedUser.getRole()
        );
    }

    public User validateLogin(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
        .orElseThrow(() ->
            new InvalidCredentialsException("Invalid credentials"));

        boolean passwordMatches = passwordEncoder.matches(
            request.getPassword(),
            user.getPasswordHash()
        );

        if(!passwordMatches) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        return user;
    }
}

