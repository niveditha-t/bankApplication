package com.fintech.bank.service.impl;

import com.fintech.bank.exception.*;
import com.fintech.bank.model.dao.AccountEntity;
import com.fintech.bank.model.dao.PayeeEntity;
import com.fintech.bank.model.dao.TransactionLogEntity;
import com.fintech.bank.model.dto.PayeeDto;
import com.fintech.bank.model.dto.TransactionLogDto;
import com.fintech.bank.service.EntityAccessValidationService;
import com.fintech.bank.service.PayeeCrudService;
import com.fintech.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("transactionService")
public class TransactionServiceImpl extends AccountCrudServiceImpl implements TransactionService {

    @Autowired
    TransactionLogCrudServiceImpl transactionLogCrudService;

    @Autowired
    PayeeCrudService payeeCrudService;

    @Autowired
    EntityAccessValidationService entityAccessValidationService;

    private void validateTransactionAmount(Double amount) throws InvalidTransactionException {
        if(amount <=0){
            throw new InvalidTransactionException("Invalid transaction amount");
        }
    }

    private void validateAmountAgainstBalance(Double balance, Double amount) throws InvalidTransactionException {
        if(balance<amount)
            throw new InvalidTransactionException("Insufficient Balance to perform this transaction");
    }

    private void validateAmountAgainstLimit(Double amount, Double transferLimit) throws InvalidTransactionException {
        if(transferLimit<amount)
            throw new InvalidTransactionException("Transfer amount exceeds allowed transfer limit for this payee");
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionLogDto deposit(String customerNumber, String accountNumber, Double amount) throws AccountNotFoundException, InvalidTransactionException, InvalidAccountException {
        entityAccessValidationService.validateTxnAccountNumber(accountNumber, customerNumber);
        validateTransactionAmount(amount);
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
        accountEntity.setCurrentBalance(accountEntity.getCurrentBalance()+amount);
        accountRepository.save(accountEntity);
        return transactionLogCrudService.addTransactionDetails(accountEntity, TransactionLogEntity.TransactionType.CREDIT, amount, null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionLogDto withdraw(String customerNumber, String accountNumber, Double amount) throws AccountNotFoundException, InvalidTransactionException, InvalidAccountException {
        entityAccessValidationService.validateTxnAccountNumber(accountNumber, customerNumber);
        validateTransactionAmount(amount);
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
        validateAmountAgainstBalance(accountEntity.getCurrentBalance(), amount);
        accountEntity.setCurrentBalance(accountEntity.getCurrentBalance()-amount);
        accountRepository.save(accountEntity);
        return transactionLogCrudService.addTransactionDetails(accountEntity, TransactionLogEntity.TransactionType.DEBIT, amount, null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionLogDto transfer(String customerNumber, String accountNumber, String payeeNumber, Double amount) throws AccountNotFoundException, PayeeNotFoundException, InvalidTransactionException, InvalidPayeeException, InvalidAccountException {
        entityAccessValidationService.validateTxnAccountNumber(accountNumber, customerNumber);
        validateTransactionAmount(amount);
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
        validateAmountAgainstBalance(accountEntity.getCurrentBalance(), amount);
        accountEntity.setCurrentBalance(accountEntity.getCurrentBalance()-amount);
        PayeeDto payeeDto = payeeCrudService.fetchPayeeDetails(payeeNumber, customerNumber);
        entityAccessValidationService.validatePayeeTransferAccountNumber(payeeDto.getPayeeAccountNumber(), accountNumber);
        validateAmountAgainstLimit(amount, payeeDto.getTransferLimit());
        if(PayeeEntity.PayeeType.INTERNAL.name().equalsIgnoreCase(payeeDto.getPayeeType())){
            AccountEntity payeeAccountEntity = accountRepository.findByAccountNumber(payeeDto.getPayeeAccountNumber());
            payeeAccountEntity.setCurrentBalance(payeeAccountEntity.getCurrentBalance()+amount);
            accountRepository.save(payeeAccountEntity);
            transactionLogCrudService.addTransactionDetails(payeeAccountEntity, TransactionLogEntity.TransactionType.TRANSFER, amount, " from "+ accountNumber);
        }
        accountRepository.save(accountEntity);
        return transactionLogCrudService.addTransactionDetails(accountEntity, TransactionLogEntity.TransactionType.TRANSFER, amount, " to "+ payeeDto.getPayeeAccountNumber());

    }
}
