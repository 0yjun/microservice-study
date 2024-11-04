package com.catalogservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;

@Data
public class CatalogDto {
    private String productId;
    private Integer qty;
    private Integer stock;
    private Integer unitPrice;
    private String orderId;
    private String userId;
}
