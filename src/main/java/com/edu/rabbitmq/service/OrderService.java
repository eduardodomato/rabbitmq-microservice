package com.edu.rabbitmq.service;

import com.edu.rabbitmq.config.MessagingConfig;
import com.edu.rabbitmq.dto.OrderDTO;
import com.edu.rabbitmq.dto.OrderStatus;
import com.edu.rabbitmq.mapper.OrderMapper;
import com.edu.rabbitmq.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RabbitTemplate template;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public void save(OrderDTO orderDto, String restaurantName) {

        orderDto.setOrderId(UUID.randomUUID().toString());
        OrderStatus orderStatus = new OrderStatus(orderDto, "IN PROGRESS", "Order sent to UberEats");
        //Publish to RabbitMQ
        template.convertAndSend(MessagingConfig.EDU_EXCHANGE, MessagingConfig.EDU_ROUTING_KEY, orderStatus);

        //Save order to DB
        orderRepository.save(orderMapper.mapDtoToOrder(orderDto));

    }
}
