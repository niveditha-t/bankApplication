package com.fintech.bank.service.impl;

import com.fintech.bank.exception.CustomerNotFoundException;
import com.fintech.bank.model.dto.CustomerDto;
import com.fintech.bank.repository.CustomerRepository;
import com.fintech.bank.security.CustomerDetails;
import com.fintech.bank.service.CustomerCrudService;
import com.fintech.bank.service.EntityAccessValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CustomerCrudServiceImpl implements CustomerCrudService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityAccessValidationService entityAccessValidationService;

    @Override
    public CustomerDto fetchCustomerDetails(String customerNumber) throws CustomerNotFoundException{
        entityAccessValidationService.validateCustomerNumber(customerNumber);
        return CustomerDto.buildCustomer(customerRepository.findByCustomerNumber(customerNumber));
    }

}