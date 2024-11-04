package com.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Responseuser {
    private String email;
    private String name;
    private String userId;
}
