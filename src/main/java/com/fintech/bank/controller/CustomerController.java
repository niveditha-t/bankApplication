package com.fintech.bank.controller;

import com.fintech.bank.exception.*;
import com.fintech.bank.security.CustomerDetails;
import com.fintech.bank.model.dto.*;
import com.fintech.bank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    @Autowired
    CustomerCrudService customerCrudService;

    @Autowired
    AccountCrudService accountCrudService;

    @Autowired
    TransactionCrudService transactionCrudService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    PayeeCrudService payeeCrudService;

    private String getCustomerNumber() {
        return ((CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCustomerNumber();
    }

    @GetMapping("/details")
    public CustomerDto fetchCustomer() throws CustomerNotFoundException {
        return customerCrudService.fetchCustomerDetails(getCustomerNumber());
    }

    @GetMapping("/accounts")
    public List<AccountDto> fetchAccounts() throws AccountNotFoundException {
        return accountCrudService.fetchAccounts(getCustomerNumber());
    }

    @GetMapping("/account/{accountNumber}")
    public AccountDto fetchAccountDetails(@PathVariable(name = "accountNumber") String accountNumber) throws AccountNotFoundException {
        return accountCrudService.fetchAccountDetails(accountNumber,getCustomerNumber());
    }

    @GetMapping("/transactions/{accountNumber}")
    public List<TransactionLogDto> fetchTransactions(@PathVariable(name = "accountNumber") String accountNumber) throws  AccountNotFoundException{
        return transactionCrudService.fetchTransactions(accountNumber, getCustomerNumber());
    }

    @GetMapping("/transaction/{txnId}")
    public TransactionLogDto fetchTransactionDetails(@PathVariable(name = "txnId") String txnId) throws TransactionNotFoundException {
        return transactionCrudService.fetchTransactionDetails(txnId, getCustomerNumber());
    }

    @GetMapping("/payees")
    public List<PayeeDto> fetchPayees() throws CustomerNotFoundException {
        return payeeCrudService.fetchPayees(getCustomerNumber());
    }

    @GetMapping("/payee/{payeeNumber}")
    public PayeeDto fetchPayeeDetails(@PathVariable(name = "payeeNumber") String payeeNumber) throws PayeeNotFoundException {
        return payeeCrudService.fetchPayeeDetails(payeeNumber, getCustomerNumber());
    }

    @PostMapping("/payee")
    public PayeeDto addPayee(@RequestBody PayeeDto payee) throws AccountNotFoundException, InvalidPayeeException {
        return payeeCrudService.addPayee(payee, getCustomerNumber());
    }

    @PostMapping("/payee/{payeeNumber}/updateLimit")
    public PayeeDto addPayee(@PathVariable(name = "payeeNumber") String payeeNumber, @RequestBody Double transferLimit) throws AccountNotFoundException, InvalidPayeeException, PayeeNotFoundException {
        return payeeCrudService.updateLimit(transferLimit, payeeNumber, getCustomerNumber());
    }

    @PostMapping("/account/{accountNumber}/deposit")
    public TransactionLogDto depositAmount(@PathVariable(name = "accountNumber") String accountNumber, @RequestBody Double amount) throws InvalidTransactionException, AccountNotFoundException, InvalidAccountException {
        return transactionService.deposit(getCustomerNumber(), accountNumber, amount);
    }

    @PostMapping("/account/{accountNumber}/withdraw")
    public TransactionLogDto withdrawAmount(@PathVariable(name = "accountNumber") String accountNumber,@RequestBody Double amount) throws InvalidTransactionException, AccountNotFoundException, InvalidAccountException {
        return transactionService.withdraw(getCustomerNumber(), accountNumber, amount);
    }

    @PostMapping("/account/{accountNumber}/transfer")
    public TransactionLogDto transferAmount(@PathVariable(name = "accountNumber") String accountNumber, @RequestBody FundTransferDto payee) throws AccountNotFoundException, PayeeNotFoundException, InvalidTransactionException, InvalidPayeeException, InvalidAccountException {
        return transactionService.transfer(getCustomerNumber(), accountNumber, payee.getPayeeNumber(), payee.getTransferAmount());
    }
}
