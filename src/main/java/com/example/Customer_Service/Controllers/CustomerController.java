package com.example.Customer_Service.Controllers;
import com.example.Customer_Service.Services.CustomerService;
import com.example.Customer_Service.Dto.CustomerCreateDto;
import com.example.Customer_Service.Entity.CustomerModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oms")
public class CustomerController {

    /**
     * constructor-based dependency injection of CustomerService
     * to use perform customer-related operations
     */
    private final CustomerService customerService;
    /**
     *
     * @param customer entity used to create customer in db.
     * @return customer object with Http 201 status.
     */
    @PostMapping("/customer")
    public ResponseEntity<CustomerModel> createCustomer( @Valid @RequestBody CustomerCreateDto customer){
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(customerService.createCustomer(customer));
    }
    /**
     *
     * @return all the customers in list
     */
    @GetMapping("/customer")
    public ResponseEntity<Page<CustomerModel>> getCustomers(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<CustomerModel> customerPage = customerService.getCustomers(pageable);
        return ResponseEntity.ok(customerPage);
    }

    /**
     * Deletes a customer by its unique identifier.
     * @param customerId unique ID of customer to be delete
     * @return ResponseEntity with HTTP 204 (No Content) status
     */
    @DeleteMapping("customer/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId){
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * update customer details by its id
     * @param customerId
     * @return unique ID of customer to be update
     */
    @PutMapping("customer/{customerId}")
    public ResponseEntity<CustomerCreateDto> updateCustomerDetails
            (@Valid @RequestBody CustomerCreateDto customer, @PathVariable String customerId){
        return ResponseEntity.ok(customerService.updateCustomer(customer, customerId));
    }

    /**
     * @param customerId used to get customer details
     * @return customer details
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

}
