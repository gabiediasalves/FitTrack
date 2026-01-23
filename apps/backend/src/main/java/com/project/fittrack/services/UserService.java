package com.project.fittrack.services;

import com.project.fittrack.entities.Role;
import com.project.fittrack.entities.User;
import com.project.fittrack.enums.AppRole;
import com.project.fittrack.expections.UserNotFoundException;
import com.project.fittrack.payload.entities.UserDTO;
import com.project.fittrack.payload.signup.SignupRequest;
import com.project.fittrack.repositories.RoleRepository;
import com.project.fittrack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> signupUser(SignupRequest request) throws UserNotFoundException {
        if(userRepository.existsByUsernameIgnoreCase(request.username()))
            return new ResponseEntity<>("Username " + request.username() + " is already in use", HttpStatus.CONFLICT);
        if(userRepository.existsByEmailIgnoreCase(request.email()))
            return new ResponseEntity<>("Email " + request.email() + " is already in use", HttpStatus.CONFLICT);

        User newUser = User.fromSignupToEntity(request);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = request.roles().stream()
                .map(x -> {
                    AppRole appRole = AppRole.valueOf(x);
                    return roleRepository.findByName(appRole);
                })
                .collect(Collectors.toSet());
        newUser.setRoles(roles);
        newUser = userRepository.save(newUser);
        return new ResponseEntity<>(newUser.toDTO(),HttpStatus.CREATED);
    }

    public UserDTO getUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username \"" + username + "\" not found"));
        return user.toDTO();
    }
}
