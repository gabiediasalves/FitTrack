package com.project.fittrack.entities;

import com.project.fittrack.payload.entities.UserDTO;
import com.project.fittrack.payload.signup.SignupRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotBlank
    @Size(min = 3,max = 120,message = "Name of user must be between 3 and 120 characters length")
    @Column(unique = true)
    private String name;
    @NotNull
    @Column(unique = true)
    @Size(min = 5, max = 120, message = "Username must be between 5 and 120 characters length")
    private String username;
    @NotNull @NotBlank
    @Size(max = 255, message = "User email must have a maximum 255 characters length")
    @Email(message = "User email invalid")
    @Column(unique = true,name = "email")
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Boolean enabled;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @NotNull
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "update_user_id")
    private User userUpdate;

    public UserDTO toDTO(){
        Set<String> roles = getRoles() == null ?
                null : getRoles().stream()
                .map(x -> x.getName().name())
                .collect(Collectors.toSet());
        return new UserDTO(
                getId(),
                getName(),
                getUsername(),
                getEmail(),
                getEnabled(),
                roles);
    }

    public static User fromSignupToEntity(SignupRequest request){
        return new User(
                null,
                request.name(),
                request.username(),
                request.email(),
                request.password(),
                request.enabled(),
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null);
    }
}
