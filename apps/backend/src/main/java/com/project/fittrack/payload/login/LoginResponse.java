package com.project.fittrack.payload.login;

import java.util.List;

public record LoginResponse(String token, String username, List<String> roles) {
}
