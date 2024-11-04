package com.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Requestuser {
    @NotNull(message="Email cannot be null")
    @Size(min = 2, message = "Email can not be ..")
    private String email;
    @NotNull(message="pwd cannot be null")
    @Size(min = 8, message = "pwd can not be ..")
    private String pwd;
    @NotNull(message="name cannot be null")
    @Size(min = 2, message = "name can not be ..")
    private String name;
}
