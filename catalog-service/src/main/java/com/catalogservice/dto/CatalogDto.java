package com.catalogservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogDto implements Serializable {
    private String productId;
    private Integer qty;
    private Integer stock;
    private Integer unitPrice;
    private String orderId;
    private String userId;
}
