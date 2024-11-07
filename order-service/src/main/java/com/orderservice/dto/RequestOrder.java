package com.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.orderservice.entity.OrderEntity}
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestOrder implements Serializable {
    String productId;
    Integer qty;
    Integer unitPrice;
}