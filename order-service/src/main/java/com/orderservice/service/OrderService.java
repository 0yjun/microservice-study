package com.orderservice.service;

import com.orderservice.dto.RequestOrder;
import com.orderservice.dto.OrderDto;
import com.orderservice.entity.OrderEntity;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Iterable<OrderEntity> getAllCatalogs();

    OrderDto getOrderByOrderId(String orderId);

    Iterable<OrderEntity> getOrdersByUserId(String userId);
    OrderDto createOrder(OrderDto orderDto);
}
