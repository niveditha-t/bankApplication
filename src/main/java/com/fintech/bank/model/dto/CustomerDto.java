package com.fintech.bank.model.dto;

import com.fintech.bank.model.dao.CustomerEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CustomerDto extends UserDto {

    String customerNumber;

    String status;

    public static CustomerDto buildCustomer(CustomerEntity customer){
        return CustomerDto.builder()
                .customerNumber(customer.getCustomerNumber())
                .id(customer.getUser().getId())
                .username(customer.getUser().getUsername())
                .password(customer.getUser().getPassword())
                .userType(customer.getUser().getUserType().name())
                .status(customer.getStatus().name())
                .build();
    }
}
