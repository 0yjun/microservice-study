package com.userservice.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    @Column(nullable = false, length = 20)
    private String encryptedPwd;
    @Column(nullable = false)
    private Date createAt;
}
