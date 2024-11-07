package com.orderservice.repository;

import com.orderservice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderId(String orderId);
    Iterable<OrderEntity> findByUserId(String userId);
}