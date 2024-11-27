package com.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orders_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "pdt_id")
    private String productId;

//    @Column(name = "pdt_name", nullable = false)
//    private String productName;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "usr_id", nullable = false)
    private String userId;
}