package com.fintech.bank.service;

import com.fintech.bank.exception.CustomerNotFoundException;
import com.fintech.bank.model.dto.CustomerDto;

public interface CustomerCrudService {
    CustomerDto fetchCustomerDetails(String customerNumber) throws CustomerNotFoundException;
}
