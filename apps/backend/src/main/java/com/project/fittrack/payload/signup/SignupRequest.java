package com.project.fittrack.payload.signup;

import java.util.List;

public record SignupRequest(String name,
                            String username,
                            String email,
                            String password,
                            Boolean enabled,
                            List<String> roles){}