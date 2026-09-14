package com.example.Customer_Service.Repos;

import com.example.Customer_Service.Entity.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel,Long> {

    boolean existsByEmail(String email);

    boolean existsByCustomerId(String customerId);

    Optional<CustomerModel> findByCustomerId(String customerId);

    void deleteByCustomerId(String customerId);
}
