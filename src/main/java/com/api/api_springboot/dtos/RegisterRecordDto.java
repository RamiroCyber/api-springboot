package com.api.api_springboot.dtos;

import com.api.api_springboot.models.user.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RegisterRecordDto(
        @JsonProperty("email")
        @NotBlank String email,
        @JsonProperty("user_name")
        @NotBlank String userName,
        @JsonProperty("password")
        @NotBlank String password,
        @JsonProperty("role")
        UserRole role) {
}
