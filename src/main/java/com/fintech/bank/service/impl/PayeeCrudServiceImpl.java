package com.fintech.bank.service.impl;

import com.fintech.bank.exception.*;
import com.fintech.bank.model.dao.PayeeEntity;
import com.fintech.bank.model.dto.PayeeDto;
import com.fintech.bank.repository.CustomerRepository;
import com.fintech.bank.repository.PayeeRepository;
import com.fintech.bank.service.AccountCrudService;
import com.fintech.bank.service.CustomerCrudService;
import com.fintech.bank.service.EntityAccessValidationService;
import com.fintech.bank.service.PayeeCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayeeCrudServiceImpl implements PayeeCrudService {

    @Autowired
    private PayeeRepository payeeRepository;

    @Autowired
    private AccountCrudService accountCrudService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityAccessValidationService entityAccessValidationService;

    @Override
    public List<PayeeDto> fetchPayees(String customerNumber) throws CustomerNotFoundException {
        entityAccessValidationService.validateCustomerNumber(customerNumber);
        return PayeeDto.buildPayees(payeeRepository.findAllByCustomer_CustomerNumber(customerNumber));
    }

    @Override
    public PayeeDto fetchPayeeDetails(String payeeNumber, String customerNumber) throws PayeeNotFoundException {
        entityAccessValidationService.validatePayeeNumber(payeeNumber, customerNumber);
        return PayeeDto.buildPayee(payeeRepository.findByPayeeNumber(payeeNumber));
    }

    @Override
    public PayeeDto addPayee(PayeeDto payee, String customerNumber) throws AccountNotFoundException, InvalidPayeeException {
        if(PayeeEntity.PayeeType.INTERNAL.name().equalsIgnoreCase(payee.getPayeeType()))
            entityAccessValidationService.validateInternalPayeeAccountNumber(payee.getPayeeAccountNumber());
        PayeeEntity payeeEntity = PayeeEntity.builder()
                .customer(customerRepository.findByCustomerNumber(customerNumber))
                .payeeName(payee.getPayeeName())
                .payeeNumber(PayeeEntity.genertePayeeNumber())
                .payeeType(PayeeEntity.PayeeType.valueOf(payee.getPayeeType()))
                .accountNumber(payee.getPayeeAccountNumber())
                .transferLimit(payee.getTransferLimit())
                .build();
        return PayeeDto.buildPayee(payeeRepository.save(payeeEntity));
    }

    @Override
    public PayeeDto updateLimit(Double transferLimit, String payeeNumber, String customerNumber) throws AccountNotFoundException, InvalidPayeeException, PayeeNotFoundException {
        entityAccessValidationService.validatePayeeNumber(payeeNumber, customerNumber);
        PayeeEntity payeeEntity = payeeRepository.findByPayeeNumber(payeeNumber);
        if(transferLimit == null || transferLimit <= 0){
            throw new InvalidPayeeException("");
        }
        payeeEntity.setTransferLimit(transferLimit);
        return PayeeDto.buildPayee(payeeRepository.save(payeeEntity));
    }
}
