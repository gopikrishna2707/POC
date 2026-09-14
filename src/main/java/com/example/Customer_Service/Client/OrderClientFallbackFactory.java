package com.example.Customer_Service.Client;
import com.example.Customer_Service.CustomerExceptions.NotFoundException;
import com.example.Customer_Service.CustomerExceptions.ServiceUnavailableException;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderClientFallbackFactory implements FallbackFactory<OrderClient> {

    @Override
    public OrderClient create(Throwable cause) {
        return new OrderClient() {
            @Override
            public void deleteOrdersByCustomerId(String customerId) {
                if(cause instanceof FeignException.NotFound){
                    throw new NotFoundException("User is not found at customer client");
                }
                else {
                    throw new ServiceUnavailableException("order service is not available right now");
                }
            }
        };
    }
}
