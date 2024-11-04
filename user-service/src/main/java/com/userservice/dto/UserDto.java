package com.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String email;
    private String pwd;
    private String name;
    private String userId;
    private String createAt;
    private String encryptedPwd;
    private List<ResponseOrder> orders;
}
