package com.project.fittrack.controllers;

import com.project.fittrack.entities.Role;
import com.project.fittrack.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleRepository repository;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Role> roles = repository.findAll();
        return ResponseEntity.ok(roles);
    }
}
