package com.cesar.bracine.model.user.dto;

import com.cesar.bracine.model.user.UserRole;

public record UserResponse(String id, String username, String email, UserRole role) {
}
