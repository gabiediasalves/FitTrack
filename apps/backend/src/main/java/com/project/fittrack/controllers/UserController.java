package com.project.fittrack.controllers;

import com.project.fittrack.expections.UserNotFoundException;
import com.project.fittrack.payload.entities.UserDTO;
import com.project.fittrack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService service;
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable(name = "username") String username) throws UserNotFoundException {
        return ResponseEntity.ok(service.getUserByUsername(username));
    }
}
