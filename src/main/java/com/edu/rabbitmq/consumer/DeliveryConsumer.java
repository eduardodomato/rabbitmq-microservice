package com.edu.rabbitmq.consumer;

import com.edu.rabbitmq.config.MessagingConfig;
import com.edu.rabbitmq.dto.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeliveryConsumer {

    @RabbitListener(queues = MessagingConfig.EDU_QUEUE)
    public void deliverOrder(OrderStatus orderStatus) {

        log.info("Preparing Order {} ", orderStatus.getOrder().getOrderId());

        orderStatus.setStatus("IN TRANSIT");

        log.info("Order delivered: {} ", orderStatus);


    }

}
