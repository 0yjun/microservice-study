package com.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.orderservice.entity.OrderEntity}
 */
@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable{
    private Schema schema;
    private Payload payload;
}