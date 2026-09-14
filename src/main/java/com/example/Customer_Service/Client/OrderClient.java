package com.example.Customer_Service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "Order-service",
        fallbackFactory = OrderClientFallbackFactory.class
)
public interface OrderClient {
    @DeleteMapping("/oms/order/{customerId}")
    void deleteOrdersByCustomerId(@PathVariable("customerId") String customerId);
}
