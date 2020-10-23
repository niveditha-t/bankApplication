package com.fintech.bank.service;

import com.fintech.bank.exception.*;
import com.fintech.bank.model.Status;
import com.fintech.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityAccessValidationService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PayeeRepository payeeRepository;

    public void validateCustomerNumber(String customerNumber) throws CustomerNotFoundException {
        if(customerNumber == null)
            throw new CustomerNotFoundException("Customer number cannot be null");
        else if(!customerRepository.existsByCustomerNumber(customerNumber))
            throw new CustomerNotFoundException((new StringBuilder("Customer with number : ")).append(customerNumber).append(" does not exisit").toString());
        return;
    }

    public void validateEmployeeNumber(String employeeNumber) throws EmployeeNotFoundException {
        if(employeeNumber == null)
            throw new EmployeeNotFoundException("Customer number cannot be null");
        else if(!employeeRepository.existsByEmployeeNumber(employeeNumber))
            throw new EmployeeNotFoundException((new StringBuilder("Employee with number : ")).append(employeeNumber).append(" does not exisit").toString());
        return;
    }

    public void validateAccountNumber(String accountNumber, String customerNumber) throws AccountNotFoundException {
        if(accountNumber == null)
            throw new AccountNotFoundException("Account number cannot be null");
        else if(!accountRepository.existsByAccountNumber(accountNumber))
            throw new AccountNotFoundException((new StringBuilder("Account with number : ")).append(accountNumber).append(" does not exisit").toString());
        else if(!accountRepository.existsByAccountNumberAndCustomer_CustomerNumber(accountNumber, customerNumber))
            throw new AccountNotFoundException((new StringBuilder("Account with number : ")).append(accountNumber).append(" does not exisit for the customer : ").append(customerNumber).toString());
        return;
    }

    public void validateTxnAccountNumber(String accountNumber, String customerNumber) throws AccountNotFoundException, InvalidAccountException {
        if(accountNumber == null)
            throw new AccountNotFoundException("Account number cannot be null");
        else if(!accountRepository.existsByAccountNumber(accountNumber))
            throw new AccountNotFoundException((new StringBuilder("Account with number : ")).append(accountNumber).append(" does not exisit").toString());
        else if(!accountRepository.existsByAccountNumberAndCustomer_CustomerNumber(accountNumber, customerNumber))
            throw new AccountNotFoundException((new StringBuilder("Account with number : ")).append(accountNumber).append(" does not exisit for the customer : ").append(customerNumber).toString());
        else if(!accountRepository.existsByAccountNumberAndAccountStatus(accountNumber, Status.ACTIVE))
            throw new InvalidAccountException((new StringBuilder("Account with number : ")).append(accountNumber).append(" is inactive").append(customerNumber).toString());
        return;
    }

    public void validateNewAccountNumber(String accountNumber) throws AccountNotFoundException, InvalidAccountException {
        if(accountNumber == null)
            throw new AccountNotFoundException("Account number cannot be null");
        else if(accountRepository.existsByAccountNumber(accountNumber))
            throw new InvalidAccountException((new StringBuilder("Account with number : ")).append(accountNumber).append(" exisit already").toString());
        return;
    }

    public void validatePayeeTransferAccountNumber(String payeeAccountNumber, String accountNumber) throws InvalidTransactionException, InvalidPayeeException, InvalidAccountException {
        if(payeeAccountNumber == null)
            throw new InvalidPayeeException("Payee Account number cannot be null");
        else if(payeeAccountNumber.equalsIgnoreCase(accountNumber))
            throw new InvalidTransactionException("Payee Account number should be different from transfer account number");
        else if(!accountRepository.existsByAccountNumberAndAccountStatus(accountNumber, Status.ACTIVE))
            throw new InvalidAccountException("Payee Account number is inactive and cannot be transacted upon");
        return;
    }

    public void validateInternalPayeeAccountNumber(String payeeAccountNumber) throws AccountNotFoundException, InvalidPayeeException {
        if(payeeAccountNumber == null)
            throw new InvalidPayeeException("Payee Account number cannot be null");
        else if(!accountRepository.existsByAccountNumber(payeeAccountNumber))
            throw new AccountNotFoundException((new StringBuilder("Internal Account with number : ")).append(payeeAccountNumber).append(" does not exisits").toString());
        return;
    }

    public void validateTransactionId(String txnId, String customerNumber) throws TransactionNotFoundException {
        if(txnId == null)
            throw new TransactionNotFoundException("Transaction Id cannot be null");
        else if(!transactionRepository.existsByTxnId(txnId))
            throw new TransactionNotFoundException((new StringBuilder("Transaction with id : ")).append(txnId).append(" does not exisit").toString());
//        else if(!transactionRepository.existsByTxnIdAAndAccount_Customer_CustomerNumber(txnId, customerNumber))
//            throw new TransactionNotFoundException((new StringBuilder("Transaction with id : ")).append(txnId).append(" does not exisit for the customer : ").append(customerNumber).toString());

        return;
    }

    public void validatePayeeNumber(String payeeNumber, String customerNumber) throws PayeeNotFoundException {
        if(payeeNumber == null)
            throw new PayeeNotFoundException("Payee number cannot be null");
        else if(!payeeRepository.existsByPayeeNumber(payeeNumber))
            throw new PayeeNotFoundException((new StringBuilder("Payee with number : ")).append(payeeNumber).append(" does not exisit").toString());
        else if(!payeeRepository.existsByPayeeNumberAndCustomer_CustomerNumber(payeeNumber, customerNumber))
            throw new PayeeNotFoundException((new StringBuilder("Payee with number : ")).append(payeeNumber).append(" does not exisit for the customer : ").append(customerNumber).toString());

        return;
    }

}
