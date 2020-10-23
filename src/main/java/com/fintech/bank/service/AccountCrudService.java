package com.fintech.bank.service;

import com.fintech.bank.exception.AccountNotFoundException;
import com.fintech.bank.exception.CustomerNotFoundException;
import com.fintech.bank.exception.EmployeeNotFoundException;
import com.fintech.bank.exception.InvalidAccountException;
import com.fintech.bank.model.dto.AccountDto;

import java.util.List;

public interface AccountCrudService {
    List<AccountDto> fetchAccounts(String accountNumber) throws AccountNotFoundException;

    AccountDto fetchAccountDetails(String accountNumber, String customerNumber) throws AccountNotFoundException;

    AccountDto addAccount(String accountType, String customerNumber, String employeeNumber) throws InvalidAccountException, AccountNotFoundException, CustomerNotFoundException, EmployeeNotFoundException;

    AccountDto deActivate(String accountNumber) throws AccountNotFoundException;

    AccountDto activate(String accountNumber) throws AccountNotFoundException;
}
