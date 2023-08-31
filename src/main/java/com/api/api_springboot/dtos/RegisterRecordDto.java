package com.api.api_springboot.dtos;

import com.api.api_springboot.models.user.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterRecordDto(@NotBlank String email, @NotBlank String userName, @NotBlank String password, @NotBlank UserRole role) {
}
