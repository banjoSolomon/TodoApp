package org.solomon11.controllers;

import org.solomon11.dto.*;
import org.solomon11.response.ApiResponse;
import org.solomon11.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
public class UserController {
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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            var result = users.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/todo_list")
    public ResponseEntity<?> createTodolist(@RequestBody TodolistRequest todolistRequest) {
        try {
            var result = users.createTodolist(todolistRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/edit_todolist")
    public ResponseEntity<?> editTodoList(@RequestBody EditTodolistRequest editTodolistRequest) {
        try {
            var result = users.editTodoListWith(editTodolistRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete-todolist")
    public ResponseEntity<?> deleteTodoList(@RequestBody DeleteTodolistRequest deleteTodolistRequest) {
        try {
            var result = users.deleteTodoTaskWith(deleteTodolistRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PatchMapping("/View-All")
    public ResponseEntity<?> viewAllTodoList(@RequestBody TodolistRequest todolistRequest) {
        try {
            var result = users.viewAllTodoList(todolistRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/Mark-Task")
    public ResponseEntity<?> markTaskStatus(@RequestBody MarkTaskRequest markTaskRequest) {
        try {
            var result = users.markTaskStatus(markTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/Mark-Task-As-Priority")
    public ResponseEntity<?> markTaskAsPriority(@RequestBody MarkTaskPriorityRequest markTaskPriorityRequest) {
        try {
            var result = users.markTaskAsPriority(markTaskPriorityRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        try {
            var result = users.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }


}