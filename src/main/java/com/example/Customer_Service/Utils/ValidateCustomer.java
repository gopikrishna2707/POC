package com.example.Customer_Service.Utils;
import com.example.Customer_Service.Client.OrderClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateCustomer {

    private final OrderClient orderClient;

    public void deleteCustomerOrders(String customerId) {
        try {
            orderClient.deleteOrdersByCustomerId(customerId);
        }
        catch(FeignException.NotFound ex){
            throw new RuntimeException("user not found");
        }
    }
}
