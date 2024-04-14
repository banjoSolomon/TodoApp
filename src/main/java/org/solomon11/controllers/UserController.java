package org.solomon11.controllers;

import org.solomon11.dto.RegisterRequest;
import org.solomon11.repository.Users;
import org.solomon11.response.ApiResponse;
import org.solomon11.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class TodoController {
    @Autowired
    private UserService users;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            var result = users.register(request);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }


    }

}
