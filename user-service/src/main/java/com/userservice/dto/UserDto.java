package com.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String pwd;
    private String name;
    private String userId;
    private String createAt;
    private String encryptedPwd;
}
