package com.fintech.bank.service;

import com.fintech.bank.exception.*;
import com.fintech.bank.model.dto.TransactionLogDto;

public interface TransactionService extends AccountCrudService{

    TransactionLogDto deposit(String customerNumber, String accountNumber, Double amount) throws AccountNotFoundException, InvalidTransactionException, InvalidAccountException;

    TransactionLogDto withdraw(String customerNumber, String accountNumber, Double amount) throws AccountNotFoundException, InvalidTransactionException, InvalidAccountException;

    TransactionLogDto transfer(String customerNumber, String accountNumber, String payeeNumber, Double amount) throws AccountNotFoundException, InvalidTransactionException, PayeeNotFoundException, InvalidPayeeException, InvalidAccountException;
}
