package com.edu.rabbitmq.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "orders")
public class Order {

        @Id
        @GeneratedValue
        @UuidGenerator
        @Column(nullable = false, updatable = false)
        private UUID id;
        private String name;
        private int qty;
        private double price;


}
