package com.example.Customer_Service.Mappers;

import com.example.Customer_Service.Dto.CustomerCreateDto;
import com.example.Customer_Service.Entity.CustomerModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerModel updateCustomerMapper(CustomerCreateDto source, CustomerModel target){
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setAddress(source.getAddress());
        return target;
    }

    public CustomerModel toEntity(CustomerCreateDto customerCreateDto){

        CustomerModel customerModel = new CustomerModel();

        customerModel.setName(customerCreateDto.getName());
        customerModel.setEmail(customerCreateDto.getEmail());
        customerModel.setAddress(customerCreateDto.getAddress());
        customerModel.setPhoneNumber(customerCreateDto.getPhoneNumber());

        return customerModel;
    }


    public CustomerCreateDto toDto(CustomerModel model) {

        CustomerCreateDto dto = new CustomerCreateDto();

        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setPhoneNumber(model.getPhoneNumber());
        dto.setAddress(model.getAddress());

        return dto;
    }

}
