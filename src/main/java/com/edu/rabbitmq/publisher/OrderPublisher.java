package com.edu.rabbitmq.publisher;

import com.edu.rabbitmq.dto.OrderDTO;
import com.edu.rabbitmq.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderPublisher {

    private final OrderService orderService;

    @PostMapping("/{restaurantName}")
    public ResponseEntity<?> saveOrder(@RequestBody OrderDTO order, @PathVariable String restaurantName){

        orderService.save(order, restaurantName);

        return ResponseEntity.ok().build();
    }


}
