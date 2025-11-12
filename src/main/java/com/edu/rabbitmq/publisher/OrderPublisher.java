package com.edu.rabbitmq.publisher;

import com.edu.rabbitmq.dto.OrderDTO;
import com.edu.rabbitmq.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderPublisher {

    private final OrderService orderService;

    @PostMapping("/{restaurantName}")
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTO order, @PathVariable String restaurantName){

        OrderDTO createdOrder = orderService.save(order, restaurantName);

        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable UUID orderId, @RequestBody OrderDTO orderDto){
        
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDto);
        
        return ResponseEntity.ok(updatedOrder);
    }

}
