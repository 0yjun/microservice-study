package com.userservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

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
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String encryptedPwd;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;
}
