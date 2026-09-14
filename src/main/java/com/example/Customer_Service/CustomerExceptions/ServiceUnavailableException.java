package com.example.Customer_Service.CustomerExceptions;

public class ServiceUnavailableException extends RuntimeException {
        public ServiceUnavailableException(String message){
            super(message);
        }
}
