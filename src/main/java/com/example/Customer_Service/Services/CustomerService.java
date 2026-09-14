package com.example.Customer_Service.Services;
import com.example.Customer_Service.Client.OrderClient;
import com.example.Customer_Service.CustomerExceptions.CustomException;
import com.example.Customer_Service.CustomerExceptions.NotFoundException;
import com.example.Customer_Service.Repos.CustomerRepository;
import com.example.Customer_Service.Dto.CustomerCreateDto;
import com.example.Customer_Service.Entity.CustomerModel;
import com.example.Customer_Service.Mappers.CustomerMapper;
import com.example.Customer_Service.Utils.ValidateCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    /**
     * injection dependency
     */
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ValidateCustomer validateCustomer;
    private final OrderClient orderClient;

    /**
     *
     * @param customer have all the inputs
     * @return customer details with unique ID
     */
    public CustomerModel createCustomer(CustomerCreateDto customer){

        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new CustomException("Email already exist");
        }
        CustomerModel customerModel = customerMapper.toEntity(customer);

        return customerRepository.save(customerModel);
    }

    /**
     *
     * @return all the customers in List in sorting order od ID
     */
    public Page<CustomerModel> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    /**
     * @param customerId used to delete customer By ID and call order service
     */
    public void deleteCustomerById(String customerId) {

        CustomerModel customerModel = customerRepository.
                findByCustomerId(customerId)
                .orElseThrow(() -> new CustomException("user is not found.please try again..."));

        orderClient.deleteOrdersByCustomerId(customerId);

        customerRepository.deleteById(customerModel.getId());
    }

    /**
     *
     * @param customer will have updating customer details
     * @param customerId used to fetch customer details and update
     * @return updated customer details
     */
    public CustomerCreateDto updateCustomer(CustomerCreateDto customer, String customerId) {

        CustomerModel existingCustomer = customerRepository
                .findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        CustomerModel updateDetails = customerMapper.updateCustomerMapper(customer, existingCustomer);

        CustomerModel savedDetails = customerRepository.save(updateDetails);

        return customerMapper.toDto(savedDetails);
    }
    /**
     *
     * @param customerId used to fetch customer
     * @return customer details
     */
    public CustomerModel getCustomerById(String customerId) {

        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("user is not found from customer service"));
    }
}
