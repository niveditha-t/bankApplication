package com.fintech.bank.controller;

import com.fintech.bank.exception.*;
import com.fintech.bank.model.dto.AccountDto;
import com.fintech.bank.security.EmployeeDetails;
import com.fintech.bank.service.AccountCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    AccountCrudService accountCrudService;

    private String getEmployeeNumber() {
        return ((EmployeeDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmployeeNumber();
    }

    @PostMapping("/account")
    public AccountDto addAccount(@RequestBody AccountDto accountDto) throws AccountNotFoundException, EmployeeNotFoundException, InvalidAccountException, CustomerNotFoundException {
        return accountCrudService.addAccount(accountDto.getAccountType(),accountDto.getCustomerNumber(), getEmployeeNumber());
    }

    @PostMapping("/account/{accountNumber}/deactivate")
    public AccountDto deactivateAccount(@PathVariable(name = "accountNumber") String accountNumber) throws AccountNotFoundException{
        return accountCrudService.deActivate(accountNumber);
    }

    @PostMapping("/account/{accountNumber}/activate")
    public AccountDto activateAccount(@PathVariable(name = "accountNumber") String accountNumber) throws AccountNotFoundException{
        return accountCrudService.activate(accountNumber);
    }
}
