package com.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {
    @NotNull(message="Email cannot be null")
    @Size(min = 2, message = "Email can not be 2 char..")
    private String email;

    @NotNull(message="password cannot be null")
    @Size(min = 8, message = "password over than 8 char..")
    private String password;

}
