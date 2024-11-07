package com.orderservice.service;

import com.orderservice.dto.RequestOrder;
import com.orderservice.dto.OrderDto;
import com.orderservice.entity.OrderEntity;
import com.orderservice.repository.OrderRepository;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Iterable<OrderEntity> getAllCatalogs() {
        Iterable<OrderEntity> orderList = orderRepository.findAll();
        return orderList;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId)
                .orElseThrow(()->new NotFoundException("order entitiy not found"));
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);
        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity =mapper.map(orderDto, OrderEntity.class);
        orderRepository.save(orderEntity);
        OrderDto returnValue = mapper.map(orderEntity, OrderDto.class);
        return returnValue;
    }
}
