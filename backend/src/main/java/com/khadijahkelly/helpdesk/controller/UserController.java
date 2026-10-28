package com.khadijahkelly.helpdesk.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khadijahkelly.helpdesk.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import com.khadijahkelly.helpdesk.dto.CreateUserRequest;
import com.khadijahkelly.helpdesk.dto.UserResponse;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(
            @Valid @RequestBody CreateUserRequest request) {

        return userService.registerUser(request);
    }
}