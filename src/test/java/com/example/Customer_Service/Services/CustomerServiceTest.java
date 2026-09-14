package com.example.Customer_Service.Services;
import com.example.Customer_Service.CustomerExceptions.CustomException;
import com.example.Customer_Service.CustomerExceptions.NotFoundException;
import com.example.Customer_Service.Dto.CustomerCreateDto;
import com.example.Customer_Service.Entity.CustomerModel;
import com.example.Customer_Service.Mappers.CustomerMapper;
import com.example.Customer_Service.Repos.CustomerRepository;
import com.example.Customer_Service.Utils.ValidateCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {


    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private ValidateCustomer validateCustomer;

    private CustomerCreateDto customerDto;
    private CustomerModel customerModel;


    @BeforeEach
    void setUp() {
        customerDto = new CustomerCreateDto();
        customerDto.setEmail("test@gmail.com");

        customerModel = new CustomerModel();
        customerModel.setEmail("test@gmail.com");
    }

    @Test
    void shouldCreateCustomerSuccessfully() {

        when(customerRepository.existsByEmail(customerDto.getEmail()))
                .thenReturn(false);

        when(customerMapper.toEntity(customerDto))
                .thenReturn(customerModel);

        when(customerRepository.save(customerModel))
                .thenReturn(customerModel);

        CustomerModel result = customerService.createCustomer(customerDto);

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
    }

    /**
     * Email already exists
     */
    @Test
    void shouldThrowExceptionIfEmailAlreadyExists() {

        when(customerRepository.existsByEmail(customerDto.getEmail()))
                .thenReturn(true);

        assertThrows(CustomException.class, () ->
                customerService.createCustomer(customerDto));
    }


    /**
     * get all customers
     */
    @Test
    void shouldReturnPagedCustomers() {

        Pageable pageable = mock(Pageable.class);
        Page<CustomerModel> page = mock(Page.class);

        when(customerRepository.findAll(pageable))
                .thenReturn(page);

        Page<CustomerModel> result = customerService.getCustomers(pageable);

        assertEquals(page, result);
    }

    /**
     * delete customer
     */
    @Test
    void shouldDeleteCustomerSuccessfully() {

        String customerId = "CUST-123";

        when(customerRepository.findByCustomerId(customerId))
                .thenReturn(Optional.of(customerModel));

        customerService.deleteCustomerById(customerId);

        verify(customerRepository).delete(customerModel);
        verify(validateCustomer).deleteCustomerOrders(customerId);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFoundForDelete() {

        String customerId = "CUST-123";

        when(customerRepository.findByCustomerId(customerId))
                .thenReturn(Optional.empty());

        assertThrows(CustomException.class, () ->
                customerService.deleteCustomerById(customerId));
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {

        String customerId = "CUST-123";

        when(customerRepository.findByCustomerId(customerId))
                .thenReturn(Optional.of(customerModel));

        when(customerMapper.updateCustomerMapper(customerDto, customerModel))
                .thenReturn(customerModel);

        when(customerRepository.save(customerModel))
                .thenReturn(customerModel);

        when(customerMapper.toDto(customerModel))
                .thenReturn(customerDto);

        CustomerCreateDto result =
                customerService.updateCustomer(customerDto, customerId);

        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingCustomer() {

        String customerId = "CUST-123";

        when(customerRepository.findByCustomerId(customerId))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                customerService.updateCustomer(customerDto, customerId));
    }

    @Test
    void shouldReturnCustomerById() {

        String customerId = "CUST-123";

        when(customerRepository.findByCustomerId(customerId))
                .thenReturn(Optional.of(customerModel));

        CustomerModel result =
                customerService.getCustomerById(customerId);

        assertEquals(customerModel, result);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        String customerId = "CUST-123";

        when(customerRepository.findByCustomerId(customerId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                customerService.getCustomerById(customerId));
    }
}
