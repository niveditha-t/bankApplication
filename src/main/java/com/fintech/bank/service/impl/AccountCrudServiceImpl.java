package com.fintech.bank.service.impl;

import com.fintech.bank.exception.AccountNotFoundException;
import com.fintech.bank.exception.CustomerNotFoundException;
import com.fintech.bank.exception.EmployeeNotFoundException;
import com.fintech.bank.exception.InvalidAccountException;
import com.fintech.bank.model.Status;
import com.fintech.bank.model.dao.AccountEntity;
import com.fintech.bank.model.dto.AccountDto;
import com.fintech.bank.repository.AccountRepository;
import com.fintech.bank.repository.CustomerRepository;
import com.fintech.bank.repository.EmployeeRepository;
import com.fintech.bank.service.AccountCrudService;
import com.fintech.bank.service.EntityAccessValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountCrudService")
public class AccountCrudServiceImpl implements AccountCrudService {

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected EntityAccessValidationService entityAccessValidationService;

    @Override
    public List<AccountDto> fetchAccounts(String customerNumber) {
        return AccountDto.buildAccounts(accountRepository.getAllByCustomer_CustomerNumber(customerNumber));
    }

    @Override
    public AccountDto fetchAccountDetails(String accountNumber, String customerNumber) throws AccountNotFoundException {
        entityAccessValidationService.validateAccountNumber(accountNumber, customerNumber);
        return AccountDto.buildAccount(accountRepository.findByAccountNumber(accountNumber));
    }

    @Override
    public AccountDto addAccount(String accountType, String customerNumber, String employeeNumber) throws InvalidAccountException, AccountNotFoundException, CustomerNotFoundException, EmployeeNotFoundException {
        entityAccessValidationService.validateCustomerNumber(customerNumber);
        entityAccessValidationService.validateEmployeeNumber(employeeNumber);
        AccountEntity accountEntity = AccountEntity.builder()
                .accountNumber(AccountEntity.generteAccountNumber())
                .accountStatus(Status.ACTIVE)
                .accountType(AccountEntity.AccountType.valueOf(accountType))
                .customer(customerRepository.findByCustomerNumber(customerNumber))
                .employee(employeeRepository.findByEmployeeNumber(employeeNumber))
                .build();
        return AccountDto.buildAccount(accountRepository.save(accountEntity));
    }

    @Override
    public AccountDto deActivate(String accountNumber) throws AccountNotFoundException {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
        if(accountEntity == null){
            throw new AccountNotFoundException("Account with number : " + accountNumber + "doesnt exist");
        }
        accountEntity.setAccountStatus(Status.INACTIVE);
        return AccountDto.buildAccount(accountRepository.save(accountEntity));
    }

    @Override
    public AccountDto activate(String accountNumber) throws AccountNotFoundException {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
        if(accountEntity == null){
            throw new AccountNotFoundException("Account with number : " + accountNumber + "doesnt exist");
        }
        accountEntity.setAccountStatus(Status.ACTIVE);
        return AccountDto.buildAccount(accountRepository.save(accountEntity));
    }

}
