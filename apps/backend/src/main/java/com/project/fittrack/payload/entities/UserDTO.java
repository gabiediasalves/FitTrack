package com.project.fittrack.payload.entities;

import com.project.fittrack.entities.User;

import java.util.Set;

public record UserDTO(Long id, String name, String username, String email, Boolean enabled, Set<String> roles) { }
