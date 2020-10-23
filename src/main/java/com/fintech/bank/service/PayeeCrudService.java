package com.fintech.bank.service;

import com.fintech.bank.exception.AccountNotFoundException;
import com.fintech.bank.exception.CustomerNotFoundException;
import com.fintech.bank.exception.InvalidPayeeException;
import com.fintech.bank.exception.PayeeNotFoundException;
import com.fintech.bank.model.dto.PayeeDto;

import java.util.List;

public interface PayeeCrudService {

    List<PayeeDto> fetchPayees(String customerNumber) throws CustomerNotFoundException;

    PayeeDto fetchPayeeDetails(String payeeNumber, String customerNumber) throws PayeeNotFoundException;

    PayeeDto addPayee(PayeeDto payee, String customerNumber) throws AccountNotFoundException, InvalidPayeeException;

    PayeeDto updateLimit(Double limit, String payeeNumber, String customerNumber) throws AccountNotFoundException, InvalidPayeeException, PayeeNotFoundException;
}
