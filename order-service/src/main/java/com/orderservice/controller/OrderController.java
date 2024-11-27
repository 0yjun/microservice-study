package com.orderservice.controller;

import com.orderservice.dto.OrderDto;
import com.orderservice.dto.RequestOrder;
import com.orderservice.dto.ResponseOrder;
import com.orderservice.entity.OrderEntity;
import com.orderservice.messagequeue.KafkaProducer;
import com.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
public class OrderController {
    private final OrderService orderService;
    private final Environment env;

    private final KafkaProducer kafkaProducer;


    public OrderController(
            OrderService orderService,
            Environment env,
            KafkaProducer kafkaProducer
    ) {
        this.orderService = orderService;
        this.env = env;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/health-check")
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

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(
            @PathVariable("userId") String userId
    ){
       Iterable<OrderEntity> orderList= orderService.getOrdersByUserId(userId);
       List<ResponseOrder> result = new ArrayList<>();
       orderList.forEach(v->{
           result.add(new ModelMapper().map(v, ResponseOrder.class));
       });
       return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(
            @PathVariable("userId") String userId,
            @RequestBody RequestOrder orderDetails
    ){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createOrder = orderService.createOrder(orderDto);

        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);


        /* send this order from kafka*/
        kafkaProducer.send("example-catalog-topic",orderDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseOrder);
    }
}
