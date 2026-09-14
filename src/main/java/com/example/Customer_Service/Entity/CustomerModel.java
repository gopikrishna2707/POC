package com.example.Customer_Service.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "customers")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;

    @Setter
    private String name;

    @Setter
    private String email;

    @Setter
    private String phoneNumber;

    @Setter
    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void beforeCustomerCreate(){
        this.createdAt = LocalDateTime.now();
        this.customerId = "CUST-" + UUID.randomUUID();
    }

    @PreUpdate
    public void afterUpdateCustomer(){
        this.updatedAt = LocalDateTime.now();
    }
}
