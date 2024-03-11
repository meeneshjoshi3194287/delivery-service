package com.nagp.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class  AuthRequest {
    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "Please provide the username")
    private String username;
    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "Please provide the password")
    private String password;
}