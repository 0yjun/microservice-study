package com.orderservice.controller;

import com.orderservice.dto.OrderDto;
import com.orderservice.entity.OrderEntity;
import com.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order-service")
public class OrderContrller {
    private final OrderService orderService;
    private final Environment env;


    public OrderContrller(
            OrderService orderService,
            Environment env
    ) {
        this.orderService = orderService;
        this.env = env;
    }

    @GetMapping("health-check")
    public String status(){
        return  String.format("this port is %s", env.getProperty("local.server.port"));
    };

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders(){
        Iterable<OrderEntity> userList = orderService.getAllCatalogs();
        List<OrderDto> result = new ArrayList<>();
        userList.forEach(v->{
            result.add(new ModelMapper().map(v,OrderDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
