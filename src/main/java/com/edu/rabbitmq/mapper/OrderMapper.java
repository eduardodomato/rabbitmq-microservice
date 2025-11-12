package com.edu.rabbitmq.mapper;

import com.edu.rabbitmq.dto.OrderDTO;
import com.edu.rabbitmq.entity.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {
    public OrderDTO mapOrderToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId(order.getId().toString());
        orderDTO.setName(order.getName());
        orderDTO.setQty(order.getQty());
        orderDTO.setPrice(order.getPrice());

        return orderDTO;

    }

    public Order mapDtoToOrder(OrderDTO orderDTO){
        Order order = new Order();
        // Don't set ID manually - let Hibernate generate it via @GeneratedValue
        // If orderId exists in DTO and we need to preserve it, we can set it after entity is persisted
        order.setName(orderDTO.getName());
        order.setQty(orderDTO.getQty());
        order.setPrice(orderDTO.getPrice());
        return order;
    }
}
